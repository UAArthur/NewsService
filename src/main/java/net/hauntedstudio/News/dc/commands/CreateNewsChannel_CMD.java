package net.hauntedstudio.News.dc.commands;

import com.google.gson.JsonObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.forums.ForumTagData;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.hauntedstudio.News.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class CreateNewsChannel_CMD extends ListenerAdapter {

    private final ConfigService configService;

    @Autowired
    public CreateNewsChannel_CMD(@Lazy ConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("createnewschannel")) {
            if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_SERVER)) {
                if (event.getOption("channelname") != null) {
                    String channelName = Objects.requireNonNull(event.getOption("channelname")).getAsString();
                    // Create Forum Channel and edit permissions
                    event.getGuild().createForumChannel(channelName)
                            .queue(forumChannel -> {
                                // Add tags after channel creation
                                List<ForumTagData> tags = Arrays.asList(
                                        new ForumTagData("News"),
                                        new ForumTagData("Events"),
                                        new ForumTagData("Updates"),
                                        new ForumTagData("Shop"),
                                        new ForumTagData("Maintenance")
                                );
                                forumChannel.getManager().setAvailableTags(tags).queue();

                                // Set permissions
                                forumChannel.getManager()
                                        .putPermissionOverride(event.getGuild().getPublicRole(), 0, Permission.MESSAGE_SEND.getRawValue())
                                        .queue();

                                // Set ChannelId in config
                                JsonObject config = configService.getConfig();
                                config.addProperty("discordNewsChannelId", forumChannel.getId());
                                configService.saveConfig(config);
                            });

                    event.reply("Channel created").queue();
                } else {
                    event.reply("Please provide a channel name").queue();
                }
            } else {
                event.reply("You don't have permission to use this command").queue();
            }
        }
    }
}
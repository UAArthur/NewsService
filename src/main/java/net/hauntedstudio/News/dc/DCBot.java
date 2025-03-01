package net.hauntedstudio.News.dc;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.hauntedstudio.News.dc.commands.CreateNewsChannel_CMD;
import net.hauntedstudio.News.dc.listener.ForumChannelListener;
import net.hauntedstudio.News.service.ConfigService;

public class DCBot {
    private final ConfigService configService;
    public JDA jda;

    public DCBot(ConfigService configService) {
        this.configService = configService;
    }

    public void startBot() {
        new Thread(() -> {
            try {
                String token = configService.getConfig().getAsJsonPrimitive("discordToken").getAsString();
                jda = JDABuilder.createDefault(token)
                        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                        .addEventListeners(new ForumChannelListener())
                        .addEventListeners(new CreateNewsChannel_CMD(configService)).build();

                CommandListUpdateAction commands = jda.updateCommands();

                commands.addCommands(
                        Commands.slash("createnewschannel", "Create a news channel")
                                .addOption(OptionType.STRING, "channelname", "The name of the channel", true)
                                .setGuildOnly(true)
                                .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                );

                commands.queue();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
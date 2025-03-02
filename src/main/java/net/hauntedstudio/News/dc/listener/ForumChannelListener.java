package net.hauntedstudio.News.dc.listener;

import com.google.gson.JsonPrimitive;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.ForumChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.forums.BaseForumTag;
import net.dv8tion.jda.api.entities.channel.forums.ForumPost;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.hauntedstudio.News.dc.DCBot;
import net.hauntedstudio.News.model.NewsModel;
import net.hauntedstudio.News.service.NewsService;
import net.hauntedstudio.News.utils.Logger;

import java.util.Objects;

public class ForumChannelListener extends ListenerAdapter {

    private DCBot dcBot;

    public ForumChannelListener(DCBot dcBot) {
        this.dcBot = dcBot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromThread() && event.getChannel().asThreadChannel().getParentChannel().getType() == ChannelType.FORUM) {
            if (event.getAuthor().isBot()) return;
            if (dcBot.getConfigService().getConfig().get("discordNewsChannelId") != null) {
                if (dcBot.getConfigService().getConfig().get("discordNewsChannelId").getAsString().equals(event.getChannel().getId())) {
                    return;
                }
            }
            if (Objects.requireNonNull(event.getMember()).getRoles().stream().noneMatch(role -> dcBot.getConfigService().getConfig().get("discordRolesId").getAsJsonArray()
                    .contains(new JsonPrimitive(role.getId())))) return;

            Logger.debug("Creating news article from forum post");
            NewsModel newsModel = new NewsModel();
            ThreadChannel threadChannel = event.getChannel().asThreadChannel();

            newsModel.setTitle(threadChannel.getName());
            newsModel.setContent(threadChannel.retrieveMessageById(threadChannel.getLatestMessageId()).complete().getContentRaw());

            String platform = threadChannel.getAppliedTags().stream()
                    .filter(tag -> tag.getName().equalsIgnoreCase("Client") || tag.getName().equalsIgnoreCase("Server"))
                    .map(BaseForumTag::getName)
                    .findFirst()
                    .orElse("All Platforms");
            newsModel.setPlatform(platform);

            threadChannel.getAppliedTags().stream()
                    .filter(tag -> tag.getName().equalsIgnoreCase("News")
                            || tag.getName().equalsIgnoreCase("Events")
                            || tag.getName().equalsIgnoreCase("Updates")
                            || tag.getName().equalsIgnoreCase("Shop")
                            || tag.getName().equalsIgnoreCase("Maintenance"))
                    .findFirst()
                    .ifPresent(tag -> newsModel.setCategory(NewsModel.Category.valueOf(tag.getName().toUpperCase())));

            dcBot.getNewsService().createNews(newsModel);
        }
    }
}

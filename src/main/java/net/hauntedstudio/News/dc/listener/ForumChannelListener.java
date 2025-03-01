package net.hauntedstudio.News.dc.listener;

import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.hauntedstudio.News.utils.Logger;

public class ForumChannelListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromThread() && event.getChannel().asThreadChannel().getParentChannel().getType() == ChannelType.FORUM) {
            Logger.debug("New Post in Forum Channel");
            Logger.debug(event.getAuthor().getAsMention());
            Logger.debug(event.getMessage().getChannel().getName());
            Logger.debug(event.getMessage().getContentRaw());
        }
    }
}

package net.hauntedstudio.News.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.hooks.EventListener;
import net.hauntedstudio.News.dc.DCBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ConfigService {
    private final File configFile = new File("config.json");

    private final NewsService newsService;

    public ConfigService(NewsService newsService) {
        this.newsService = newsService;
    }


    @PostConstruct
    public void init() {
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();

                JsonObject jsonObject = new JsonObject();
                JsonArray jsonArray = new JsonArray();

                jsonObject.addProperty("useDiscordBot", true);
                jsonObject.addProperty("discordToken", "");
                jsonObject.addProperty("discordNewsChannelId", "");
                jsonObject.add("discordRolesId", jsonArray);

                Files.write(Paths.get("config.json"), jsonObject.toString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException("Failed to create config file", e);
            }
        }

        JsonObject config = getConfig();
        if (config == null) {
            throw new RuntimeException("Failed to load config");
        }

        if (config.getAsJsonPrimitive("useDiscordBot").getAsBoolean()) {
            if (config.getAsJsonPrimitive("discordToken").getAsString().isEmpty()) {
                throw new RuntimeException("Discord token is empty");
            }

            DCBot dc = new DCBot(this, newsService);
            dc.startBot();
        }
    }

    public JsonObject getConfig() {
        try {
            String content = new String(Files.readAllBytes(configFile.toPath()));
            return JsonParser.parseString(content).getAsJsonObject();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config file", e);
        }
    }

    public void saveConfig(JsonObject config) {
        try {
            Files.write(configFile.toPath(), config.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save config file", e);
        }
    }
}

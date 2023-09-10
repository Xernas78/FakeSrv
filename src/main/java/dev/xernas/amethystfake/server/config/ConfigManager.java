package dev.xernas.amethystfake.server.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.xernas.amethystfake.protocol.models.ServerInfos;
import dev.xernas.amethystfake.protocol.models.chat.Color;
import dev.xernas.amethystfake.protocol.models.chat.Component;
import dev.xernas.amethystfake.protocol.models.chat.ComponentSerializer;
import dev.xernas.amethystfake.protocol.models.chat.TextComponent;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

public class ConfigManager {

    private final File configFile;
    private final File motdFile;
    private final File reasonFile;
    private final File logoFile;
    private final Properties properties;
    private final Component description;
    private final Component reason;
    private final String imageString;

    public ConfigManager() throws Exception {
        String absolutePath = Paths.get("").toAbsolutePath().toString();
        this.configFile = Paths.get(absolutePath, "config.properties").toFile();
        this.motdFile = Paths.get(absolutePath, "motd.json").toFile();
        this.reasonFile = Paths.get(absolutePath, "kick.json").toFile();
        this.logoFile = Paths.get(absolutePath, "icon.png").toFile();
        this.properties = setupConfig();
        this.description = setupMotd();
        this.reason = setupDisconnect();
        this.imageString = "data:image/png;base64," + setupLogo();
    }

    private boolean existsAndCreate(File file) throws IOException {
        if (file != null) {
            if (!file.exists()) {
                return file.createNewFile();
            }
            else {
                return false;
            }
        }
        else {
           return file.createNewFile();
        }
    }

    private Properties setupConfig() throws Exception {
        if (existsAndCreate(configFile)) {
            setConfigDefaults();
        }
        Properties prop = new Properties();
        FileReader reader = new FileReader(configFile);

        prop.load(reader);
        return prop;
    }

    private Component setupMotd() throws Exception {
        if (existsAndCreate(motdFile)) {
            setMotdDefaults();
        }
        FileReader reader = new FileReader(motdFile);
        JsonElement element = JsonParser.parseReader(reader);
        return ComponentSerializer.deserialize(element.getAsJsonObject());
    }

    private Component setupDisconnect() throws Exception {
        if (existsAndCreate(reasonFile)) {
            setDisconnectDefaults();
        }
        FileReader reader = new FileReader(reasonFile);
        JsonElement element = JsonParser.parseReader(reader);
        return ComponentSerializer.deserialize(element.getAsJsonObject());
    }

    private String setupLogo() throws Exception {
        if (!logoFile.exists()) {
            return null;
        }
        return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(logoFile));
    }

    private void setConfigDefaults() throws IOException {
        FileWriter fileWriter = new FileWriter(configFile);
        Properties defaultProps = new Properties();
        defaultProps.setProperty("version_name", "Amethyst 1.20.1");
        defaultProps.setProperty("version_protocol", "763");
        defaultProps.setProperty("players_max", "100");
        defaultProps.setProperty("players_online", "1");
        defaultProps.store(fileWriter, "");
    }

    private void setMotdDefaults() throws IOException {
        FileWriter fileWriter = new FileWriter(motdFile);
        Component motd = new TextComponent("");
        Component a = new TextComponent("A");
        a.setColor(Color.WHITE);
        motd.addComponent(a);
        Component amethyst = new TextComponent(" Amethyst");
        amethyst.setBold(true);
        amethyst.setColor(Color.LIGHT_PURPLE);
        motd.addComponent(amethyst);
        Component server = new TextComponent(" server\n");
        server.setColor(Color.WHITE);
        motd.addComponent(server);
        Component by = new TextComponent("By");
        by.setColor(Color.WHITE);
        motd.addComponent(by);
        Component xernasDev = new TextComponent(" Xernas Dev");
        xernasDev.setBold(true);
        xernasDev.setItalic(true);
        xernasDev.setColor(Color.RED);
        motd.addComponent(xernasDev);
        String json = ComponentSerializer.serialize(motd).toString();
        fileWriter.write(json, 0, json.length());
        fileWriter.flush();
        fileWriter.close();
    }

    private void setDisconnectDefaults() throws IOException {
        FileWriter fileWriter = new FileWriter(reasonFile);
        TextComponent reason = new TextComponent("This server doesn't allow players");
        reason.setColor(Color.RED);
        String json = ComponentSerializer.serialize(reason).toString();
        fileWriter.write(json, 0, json.length());
        fileWriter.flush();
        fileWriter.close();
    }

    private ServerInfos.Version getVersion() {
        if (properties.getProperty("version_name") == null) {
            return null;
        }
        if (properties.getProperty("version_protocol") == null) {
            return null;
        }
        return new ServerInfos.Version(properties.getProperty("version_name"), Integer.valueOf(properties.getProperty("version_protocol")));
    }

    private ServerInfos.Players getPlayers() {
        if (properties.getProperty("players_max") == null) {
            return null;
        }
        if (properties.getProperty("players_online") == null) {
            return null;
        }
        return new ServerInfos.Players(Integer.valueOf(properties.getProperty("players_max")), Integer.valueOf(properties.getProperty("players_online")));
    }

    private Component getDescription() {
        return description;
    }

    private String getFavicon() {
        return imageString;
    }

    public ServerInfos getInfos() {
        return new ServerInfos(
                getVersion(),
                getPlayers(),
                getDescription(),
                getFavicon()
        );
    }

    public Component getReason() {
        return reason;
    }
}

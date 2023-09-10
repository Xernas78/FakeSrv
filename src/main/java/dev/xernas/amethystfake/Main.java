package dev.xernas.amethystfake;

import dev.xernas.amethystfake.protocol.models.ServerInfos;
import dev.xernas.amethystfake.protocol.models.chat.Color;
import dev.xernas.amethystfake.protocol.models.chat.Component;
import dev.xernas.amethystfake.protocol.models.chat.TextComponent;
import dev.xernas.amethystfake.server.Server;
import dev.xernas.amethystfake.server.config.ConfigManager;
import dev.xernas.amethystfake.server.utils.Logger;
import dev.xernas.amethystfake.server.utils.OSUtil;
import org.fusesource.jansi.AnsiConsole;

public class Main {
    public static void main(String[] args) {
        if (OSUtil.isWindows()) {
            AnsiConsole.systemInstall();
        }
        boolean debug = false;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("debug")) {
                debug = true;
            }
        }
        Logger logger = new Logger("[AMETHYST]", debug, true);
        try {
            ConfigManager manager = new ConfigManager();
            ServerInfos infos = manager.getInfos();
            Component kickReason = manager.getReason();
            if (infos.getPlayers() == null || infos.getVersion() == null || infos.getDescription() == null) {
                TextComponent component = new TextComponent("Something is missing in server.properties");
                component.setColor(Color.RED);
                infos = new ServerInfos(new ServerInfos.Version("ERROR", 4), new ServerInfos.Players(0, 0), component, "");
                logger.warn("Something is missing in server.properties");
            }
            Server server = new Server(logger, 25565, infos, kickReason);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

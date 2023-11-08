package dev.xernas.fakesrv;

import dev.xernas.fakesrv.protocol.models.ServerInfos;
import dev.xernas.fakesrv.protocol.models.chat.Color;
import dev.xernas.fakesrv.protocol.models.chat.Component;
import dev.xernas.fakesrv.protocol.models.chat.TextComponent;
import dev.xernas.fakesrv.server.Server;
import dev.xernas.fakesrv.server.config.ConfigManager;
import dev.xernas.fakesrv.server.utils.Logger;
import dev.xernas.fakesrv.server.utils.OSUtil;
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
        Logger logger = new Logger("[FakeSRV]", debug, true);
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
            Server server = new Server(logger, manager.getPort(), infos, kickReason);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

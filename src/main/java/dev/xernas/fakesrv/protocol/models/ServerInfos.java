package dev.xernas.fakesrv.protocol.models;

import dev.xernas.fakesrv.protocol.models.chat.Component;

public class ServerInfos {

    private final Version version;
    private final Players players;
    private final Component description;
    private final String favicon;

    public ServerInfos(Version version, Players players, Component description, String favicon) {
        this.version = version;
        this.players = players;
        this.description = description;
        this.favicon = favicon;
    }

    public Players getPlayers() {
        return players;
    }

    public Version getVersion() {
        return version;
    }

    public Component getDescription() {
        return description;
    }

    public String getFavicon() {
        if (favicon == null) {
            return "";
        }
        return favicon;
    }

    public static class Version {
        private final String name;
        private final int protocol;

        public Version(String name, int protocol) {
            this.name = name;
            this.protocol = protocol;
        }
        public String getName() {
            return name;
        }
        public int getProtocol() {
            return protocol;
        }
    }

    public static class Players {
        private final int max;
        private final int online;

        public Players(int max, int online) {
            this.max = max;
            this.online = online;
        }

        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }
    }
}

package dev.mruniverse.redderhub.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
import java.util.Random;

public class ServerUtils {

    private final static Random random = new Random();

    public static String getServerName(ProxiedPlayer player) {
        return player.getServer().getInfo().getName();
    }

    public static String getRandomServer(List<String> servers) {
        return servers.get(
                random.nextInt(
                        servers.size()
                )
        );
    }

}

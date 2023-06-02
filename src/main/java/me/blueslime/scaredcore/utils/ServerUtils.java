package me.blueslime.scaredcore.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ServerUtils {

    public static String getName(ProxiedPlayer player) {
        return player.getServer().getInfo().getName();
    }

    public static String getRandom(List<String> servers) {
        return servers.get(
                ThreadLocalRandom.current().nextInt(
                        servers.size()
                )
        );
    }

}

package me.blueslime.scaredcore.listener;

import me.blueslime.scaredcore.ScaredCore;
import me.blueslime.scaredcore.utils.HexConverter;
import me.blueslime.slimelib.file.configuration.ConfigurationHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class ServerSwitchListener implements Listener {

    private final ScaredCore plugin;

    public ServerSwitchListener(ScaredCore plugin) {
        this.plugin = plugin;

        plugin.getProxy().getPluginManager().registerListener(plugin, this);
    }

    @EventHandler
    public void on(ServerSwitchEvent event) {
        ConfigurationHandler settings = plugin.getSettings();
        ProxiedPlayer player = event.getPlayer();

        if (settings.getBoolean("join-motd")) {
            String lang = player.getLocale().toString();

            if (lang.contains("-")) {
                lang = lang.split("-")[0];
            } else {
                lang = lang.split("_")[0];
            }

            if (settings.getBoolean("display-locale")) {
                plugin.getLogs().debug("Player locale (" + player.getName() + "): " + lang);
            }

            String path = settings.contains("messages.hub-command." + lang) ?
                    lang :
                    "default";

            sendList(
                event.getPlayer(),
                event.getPlayer().getServer().getInfo().getName(),
                plugin.getSettings().get(
                    "messages.hub-command." + path + ".join-motd",
                    "&aWelcome %player% to the hub server!"
                )
            );
        }
    }

    private void sendList(ProxiedPlayer sender, String server, Object aO) {
        if (aO instanceof List) {
            List<?> list = (List<?>) aO;
            for (Object object : list) {
                sender.sendMessage(
                    colorize(
                       object.toString().replace("%server%", server)
                           .replace("%name%", server)
                           .replace("%lobby%", server)
                           .replace("%player%", sender.getName())
                    )
                );
            }
        } else {
            sender.sendMessage(
                colorize(
                    aO.toString().replace("%server%", server)
                        .replace("%name%", server)
                        .replace("%lobby%", server)
                        .replace("%player%", sender.getName())
                )
            );
        }
    }
    private TextComponent colorize(String message) {
        return new TextComponent(
            ChatColor.translateAlternateColorCodes(
                '&',
                HexConverter.convert(message)
            )
        );
    }
}

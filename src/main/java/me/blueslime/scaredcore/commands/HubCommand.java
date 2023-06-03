package me.blueslime.scaredcore.commands;

import me.blueslime.scaredcore.ScaredCore;
import me.blueslime.scaredcore.utils.HexConverter;
import me.blueslime.scaredcore.utils.ServerUtils;
import me.blueslime.slimelib.commands.command.Command;
import me.blueslime.slimelib.commands.command.SlimeCommand;

import me.blueslime.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.slimelib.source.SlimeSource;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

@Command(
        description = "This is the hub command",
        shortDescription = "Hub cmd",
        usage = "/hub"
)
public class HubCommand implements SlimeCommand {

    private final ScaredCore plugin;

    public HubCommand(ScaredCore plugin) {
        this.plugin = plugin;
    }

    /**
     * This will be the main command for this command-class.
     *
     * @return String - Command
     */
    @Override
    public String getCommand() {
        return plugin.getSettings().getString("hub-command.command", "hub");
    }

    @Override
    public List<String> getAliases() {
        return plugin.getSettings().getStringList("hub-command.aliases");
    }

    /**
     * Execute the command
     *
     * @param sender       the sender using the command
     * @param commandLabel the command-alias used
     * @param args         arguments of the command executed
     */
    @Override
    public void execute(SlimeSource sender, String commandLabel, String[] args) {
        if (sender.isConsoleSender()) {

            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reload();
                    sender.sendColoredMessage("&aPlugin reloaded");
                    return;
                }
            }

            sender.sendColoredMessage("&cThis command is only for users");
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender.getOriginalSource();

        ConfigurationHandler settings = plugin.getSettings();

        String lang = player.getLocale().toString();

        if (lang.contains("-")) {
            lang = lang.split("-")[0];
        } else {
            lang = lang.split("_")[0];
        }

        if (settings.getBoolean("display-locale")) {
            plugin.getLogs().debug("Player locale (" + player.getName() + "): " + lang);
        }

        if (settings.getStringList("blacklist-servers").contains(ServerUtils.getName(player))) {
            plugin.getLogs().warn(
                    "Player " + player.getName() + " attempted to use /hub command in a blacklisted server"
            );
            return;
        }

        String path = settings.contains("messages.hub-command." + lang) ?
                lang :
                "default";


        Object obj = settings.get(
                "messages.hub-command." + path + ".connecting",
                "&cScaredCore &8| &fConnecting to a hub server!"
        );

        String server = ServerUtils.getRandom(
                settings.getStringList("lobby-servers")
        );

        sendList(sender, server, obj);

        if (server.equalsIgnoreCase(ServerUtils.getName(player))) {
            Object aO = settings.get(
                    "messages.hub-command." + path + ".already",
                    "&cScaredCore &8| &fYou are already on the hub server!"
            );

            sendList(sender, server, aO);
            return;
        }

        if (!plugin.getProxy().getServers().containsKey(server)) {
            return;
        }

        ServerInfo info = plugin.getProxy().getServers().get(
                server
        );

        if (info != null) {

            player.connect(
                    info
            );

        }

    }

    private void sendList(SlimeSource<?> sender, String server, Object aO) {
        if (aO instanceof List) {
            List<?> list = (List<?>) aO;
            for (Object object : list) {
                sender.sendColoredMessage(
                    HexConverter.convert(
                        object.toString().replace("%server%", server)
                            .replace("%name%", server)
                            .replace("%lobby%", server)
                            .replace("%player%", sender.getName())
                    )
                );
            }
        } else {
            sender.sendColoredMessage(
                HexConverter.convert(
                    aO.toString().replace("%server%", server)
                        .replace("%name%", server)
                        .replace("%lobby%", server)
                        .replace("%player%", sender.getName())
                )
            );
        }
    }
}

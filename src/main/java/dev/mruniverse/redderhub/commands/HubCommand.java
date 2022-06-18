package dev.mruniverse.redderhub.commands;

import dev.mruniverse.redderhub.RedderFile;
import dev.mruniverse.redderhub.RedderHub;
import dev.mruniverse.redderhub.utils.ServerUtils;
import dev.mruniverse.slimelib.commands.command.Command;
import dev.mruniverse.slimelib.commands.command.SlimeCommand;
import dev.mruniverse.slimelib.commands.sender.Sender;
import dev.mruniverse.slimelib.commands.sender.player.SlimeProxiedPlayer;
import dev.mruniverse.slimelib.control.Control;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.List;

@Command(
        description = "This is the hub command",
        shortDescription = "Hub cmd",
        usage = "/hub"
)
public class HubCommand implements SlimeCommand {

    private final RedderHub plugin;

    private Control commandManager;

    public HubCommand(RedderHub plugin) {
        this.plugin = plugin;
        update();
    }

    public void update() {
        commandManager = plugin.getLoader().getFiles().getControl(RedderFile.SETTINGS);
    }

    /**
     * This will be the main command for this command-class.
     *
     * @return String - Command
     */
    @Override
    public String getCommand() {
        return commandManager.getString("hub-command.command", "hub");
    }

    @Override
    public List<String> getAliases() {
        return commandManager.getStringList("hub-command.aliases");
    }

    /**
     * Execute the command
     *
     * @param sender       the sender using the command
     * @param commandLabel the command-alias used
     * @param args         arguments of the command executed
     */
    @Override
    public void execute(Sender sender, String commandLabel, String[] args) {
        if (sender.isConsoleSender()) {

            if (args.length >= 1) {
                if (args[1].equalsIgnoreCase("reload")) {
                    plugin.reload();
                    sender.sendColoredMessage("&aPlugin reloaded");
                    return;
                }
            }

            sender.sendColoredMessage("&cThis command is only for users");
            return;
        }

        ProxiedPlayer player = ((SlimeProxiedPlayer)sender).get();

        if (commandManager.getStringList("blacklist-servers").contains(ServerUtils.getServerName(player))) {
            plugin.getLogs().warn("Player " + player.getName() + " tried to use /hub command in a blacklisted server");
            return;
        }

        sender.sendColoredMessage(
                commandManager.getString("messages.hub-command.connecting", "&cRedderHub &8| &fConnecting to a hub server!")
        );

        String server = ServerUtils.getRandomServer(
                commandManager.getStringList("lobby-servers")
        );

        if (server.equalsIgnoreCase(ServerUtils.getServerName(player))) {
            sender.sendColoredMessage(
                    commandManager.getString("messages.hub-command.already", "&cRedderHub &8| &fYou are already on the hub server!")
            );
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
}

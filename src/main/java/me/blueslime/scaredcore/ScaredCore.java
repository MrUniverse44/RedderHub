package me.blueslime.scaredcore;

import me.blueslime.scaredcore.commands.HubCommand;
import me.blueslime.scaredcore.listener.ServerSwitchListener;
import me.blueslime.scaredcore.utils.LoggerSetup;
import me.blueslime.slimelib.SlimeFiles;
import me.blueslime.slimelib.SlimePlatform;
import me.blueslime.slimelib.SlimePlugin;
import me.blueslime.slimelib.SlimePluginInformation;
import me.blueslime.slimelib.file.configuration.ConfigurationHandler;
import me.blueslime.slimelib.loader.BaseSlimeLoader;
import me.blueslime.slimelib.loader.DefaultSlimeLoader;
import me.blueslime.slimelib.logs.SlimeLogs;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

@SuppressWarnings("unused")
public final class ScaredCore extends Plugin implements SlimePlugin<Plugin> {

    private final BaseSlimeLoader<Plugin> loader = new DefaultSlimeLoader<>(this);

    private SlimePluginInformation information;

    private SlimeLogs logs;

    private Metrics metrics;

    @Override
    public void onEnable() {
        information = LoggerSetup.buildData(getServerType(), this);
        logs = LoggerSetup.build(getServerType(), this);

        loader.setFiles(Configuration.class);

        loader.init();

        getCommands().register(
                new HubCommand(this)
        );

         new ServerSwitchListener(this);

         metrics = new Metrics(this, 18656);
    }

    @Override
    public void onDisable() {
        getLoader().shutdown();

        metrics.shutdown();
    }

    public ConfigurationHandler getSettings() {
        return getConfiguration(Configuration.SETTINGS);
    }

    public ConfigurationHandler getConfiguration(SlimeFiles file) {
        return getConfigurationHandler(file);
    }

    @Override
    public SlimePluginInformation getPluginInformation() {
        return information;
    }

    @Override
    public SlimePlatform getServerType() {
        return SlimePlatform.BUNGEECORD;
    }

    @Override
    public BaseSlimeLoader<Plugin> getLoader() {
        return loader;
    }

    @Override
    public SlimeLogs getLogs() {
        return logs;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public void reload() {
        loader.reload();
    }
}

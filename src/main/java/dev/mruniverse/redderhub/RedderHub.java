package dev.mruniverse.redderhub;

import dev.mruniverse.redderhub.commands.HubCommand;
import dev.mruniverse.slimelib.SlimePlatform;
import dev.mruniverse.slimelib.SlimePlugin;
import dev.mruniverse.slimelib.SlimePluginInformation;
import dev.mruniverse.slimelib.input.InputManager;
import dev.mruniverse.slimelib.loader.BaseSlimeLoader;
import dev.mruniverse.slimelib.loader.DefaultSlimeLoader;
import dev.mruniverse.slimelib.logs.SlimeLogger;
import dev.mruniverse.slimelib.logs.SlimeLogs;
import net.md_5.bungee.api.plugin.Plugin;

@SuppressWarnings("unused")
public final class RedderHub extends Plugin implements SlimePlugin<Plugin> {

    private BaseSlimeLoader<Plugin> slimeLoader;

    private SlimePluginInformation information;

    private SlimeLogs logs;

    @Override
    public void onEnable() {
        logs = SlimeLogger.createLogs(
                getServerType(),
                this
        );

        SlimeLogger logger = new SlimeLogger();

        logger.setPluginName("RedderHub");
        logger.setHidePackage("dev.mruniverse.redderhub.");
        logger.setContainIdentifier("mruniverse");
        logger.getProperties().getPrefixes().changeMainText("&9RedderHub");

        logs.setSlimeLogger(logger);

        slimeLoader = new DefaultSlimeLoader<>(
                this,
                InputManager.create(
                        getServerType(),
                        this
                )
        );

        information = new SlimePluginInformation(
                getServerType(),
                this
        );

        slimeLoader.setFiles(RedderFile.class);

        slimeLoader.getCommands().register(
                new HubCommand(this)
        );

        slimeLoader.init();
    }

    @Override
    public void onDisable() {
        getLoader().shutdown();
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
    public SlimeLogs getLogs() {
        return logs;
    }

    @Override
    public BaseSlimeLoader<Plugin> getLoader() {
        return slimeLoader;
    }

    @Override
    public Plugin getPlugin() {
        return this;
    }

    @Override
    public void reload() {
        slimeLoader.reload();
    }
}

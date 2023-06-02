package me.blueslime.scaredcore.utils;

import me.blueslime.slimelib.SlimePlatform;
import me.blueslime.slimelib.SlimePlugin;
import me.blueslime.slimelib.SlimePluginInformation;
import me.blueslime.slimelib.logs.SlimeLogger;
import me.blueslime.slimelib.logs.SlimeLogs;

public class LoggerSetup {
    public static <T> SlimeLogs build(SlimePlatform platform, SlimePlugin<T> plugin) {
        return initialize(
                SlimeLogger.createLogs(
                        platform,
                        plugin
                )
        );
    }

    public static <T> SlimePluginInformation buildData(SlimePlatform platform, T plugin) {
        return new SlimePluginInformation(platform, plugin);
    }

    public static SlimeLogs initialize(SlimeLogs logs) {
        logs.getPrefixes().getDebug().setPrefix("&bScaredCore | &f");
        logs.getPrefixes().getInfo().setPrefix("&6ScaredCore | &f");
        logs.getPrefixes().getWarn().setPrefix("&7ScaredCore | &f");
        logs.getPrefixes().getIssue().setPrefix("&2ScaredCore | &f");

        logs.getProperties().getExceptionProperties().CODE_COLOR = "&a";
        logs.getProperties().getExceptionProperties().BASE_COLOR = "&a";

        logs.getSlimeLogger().setContainIdentifier("me.blueslime.scaredcore");
        logs.getSlimeLogger().setHidePackage("me.blueslime.scaredcore.");

        return logs;
    }
}

package me.blueslime.scaredcore.utils;

import net.md_5.bungee.api.ChatColor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexConverter {
    private static final HexConverter INSTANCE = new HexConverter();

    private Method COLORIZE_METHOD;

    public HexConverter() {
        try {
            COLORIZE_METHOD = ChatColor.class.getDeclaredMethod("of", String.class);
        } catch (Exception ignored) {
            COLORIZE_METHOD = null;
        }
    }

    public String execute(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            String code = message.substring(matcher.start(), matcher.end());
            if (COLORIZE_METHOD != null) {
                try {
                    ChatColor color = (ChatColor) COLORIZE_METHOD.invoke(
                            ChatColor.WHITE,
                            code
                    );

                    message = message.replace(
                            code,
                            String.valueOf(color)
                    );

                    matcher = pattern.matcher(
                            message
                    );
                } catch (IllegalAccessException | InvocationTargetException e ) {
                    return message.replace(
                            code,
                            ""
                    );
                }
            }
        }
        return message;
    }

    public static String convert(String text) {
        return INSTANCE.execute(text);
    }
}

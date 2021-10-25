package xyz.invisraidinq.trashchallenge.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

    /**
     * Translate a message using an {@link ChatColor}
     *
     * @param input The text to translate
     * @return The translated text
     */
    public static String colour(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Translate an {@link List} of strings
     *
     * @param input The list to translate
     * @return A list of translated strings
     */
    public static List<String> colour(List<String> input) {
        List<String> translatedStrings = new ArrayList<>();
        input.forEach(string -> translatedStrings.add(colour(string)));
        return translatedStrings;
    }
}

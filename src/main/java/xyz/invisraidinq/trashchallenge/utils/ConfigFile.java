package xyz.invisraidinq.trashchallenge.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigFile extends YamlConfiguration {

    private final File yamlFile;

    /**
     * Constructor to instantiate an {@link ConfigFile}
     *
     * @param plugin The {@link Plugin} object
     * @param name The name of the file
     * @param path The path of the file
     */
    public ConfigFile(Plugin plugin, String name, File path) {
        if (!path.exists()) {
            path.mkdirs();
        }

        this.yamlFile = new File(path, name.contains(".yml") ? name : name + ".yml");
        if (!this.yamlFile.exists()) {
            plugin.saveResource(this.yamlFile.getName(), false);
        }

        this.reload();
    }

    /**
     * Reload the file
     */
    public void reload() {
        try {
            this.load(this.yamlFile);
        } catch (InvalidConfigurationException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Save the file
     */
    public void save() {
        try {
            this.save(this.yamlFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

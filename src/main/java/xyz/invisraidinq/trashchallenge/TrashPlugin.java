package xyz.invisraidinq.trashchallenge;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.invisraidinq.trashchallenge.commands.StartTrashCommand;
import xyz.invisraidinq.trashchallenge.commands.StopTrashCommand;
import xyz.invisraidinq.trashchallenge.listeners.PlayerPickupTrashListener;
import xyz.invisraidinq.trashchallenge.manager.TrashManager;
import xyz.invisraidinq.trashchallenge.scoreboard.ScoreboardProvider;
import xyz.invisraidinq.trashchallenge.utils.ConfigFile;

import javax.swing.*;
import java.util.Arrays;

public class TrashPlugin extends JavaPlugin {

    private ConfigFile configFile;
    private ConfigFile dataFile;
    private TrashManager trashManager;
    private Assemble assemble;

    @Override
    public void onEnable() {
        this.configFile = new ConfigFile(this, "config.yml", this.getDataFolder());
        this.dataFile = new ConfigFile(this, "data.yml", this.getDataFolder());

        this.trashManager = new TrashManager(this);

        Arrays.asList(
                new PlayerPickupTrashListener(this)
        ).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));

        this.getCommand("starttrash").setExecutor(new StartTrashCommand(this));
        this.getCommand("stoptrash").setExecutor(new StopTrashCommand(this));

        this.assemble = new Assemble(this, new ScoreboardProvider(this));
        this.assemble.setTicks(20L);
        this.assemble.setAssembleStyle(AssembleStyle.MODERN);
    }

    @Override
    public void onDisable() {
        this.assemble.cleanup();
    }

    public ConfigFile getConfigFile() {
        return this.configFile;
    }

    public ConfigFile getDataFile() {
        return this.dataFile;
    }

    public TrashManager getTrashManager() {
        return this.trashManager;
    }
}
package xyz.invisraidinq.trashchallenge;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.invisraidinq.trashchallenge.commands.StartTrashCommand;
import xyz.invisraidinq.trashchallenge.commands.StopTrashCommand;
import xyz.invisraidinq.trashchallenge.listeners.PlayerPickupTrashListener;
import xyz.invisraidinq.trashchallenge.manager.TrashManager;
import xyz.invisraidinq.trashchallenge.scoreboard.ScoreboardProvider;
import xyz.invisraidinq.trashchallenge.tasks.TrashSpawnTask;
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

        //We'll only run the absolute essentials on the main thread, or this will get heavy
        new TrashSpawnTask(this).runTaskTimerAsynchronously(this, 0L, 20 * this.configFile.getLong("time-between-trash-spawns"));
    }

    @Override
    public void onDisable() {
        this.assemble.cleanup();

        //Persist data
        this.dataFile.set("trash-collected", this.trashManager.getTrashPickedUp());
        this.dataFile.set("money-donated", this.trashManager.getMoneyDonated());
        this.dataFile.save();

        Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
            if (entity instanceof Item) {
                entity.remove(); //Clear entities on shutdown
            }
        }));

        Bukkit.getScheduler().cancelTasks(this);
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
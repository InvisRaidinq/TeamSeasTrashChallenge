package xyz.invisraidinq.trashchallenge.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.invisraidinq.trashchallenge.TrashPlugin;
import xyz.invisraidinq.trashchallenge.utils.CC;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements AssembleAdapter {

    private final TrashPlugin plugin;
    private final NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();

    public ScoreboardProvider(TrashPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getTitle(Player player) {
        return CC.colour(this.plugin.getConfig().getString("scoreboard.title")
                .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size())));
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> lines = new ArrayList<>();

        for (String line : this.plugin.getConfig().getStringList("scoreboard.lines")) {
            lines.add(line
                    .replace("%trash%", String.valueOf(this.plugin.getTrashManager().getTrashPickedUp()))
                    .replace("%money%", this.numberFormatter.format(this.plugin.getTrashManager().getMoneyDonated()))
                    .replace("%players%", String.valueOf(Bukkit.getOnlinePlayers().size())));
        }

        return lines;
    }
}

package xyz.invisraidinq.trashchallenge.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.invisraidinq.trashchallenge.TrashPlugin;
import xyz.invisraidinq.trashchallenge.utils.CC;

public class StopTrashCommand implements CommandExecutor {

    private final TrashPlugin plugin;

    public StopTrashCommand(TrashPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("trash.admin")) {
            sender.sendMessage(CC.colour("&cNo Permission"));
            return false;
        }

        if (!this.plugin.getTrashManager().isActive()) {
            sender.sendMessage(CC.colour("&cThe trash dropper is not active!"));
            return false;
        }

        this.plugin.getTrashManager().stopTrash();
        sender.sendMessage(CC.colour("&cYou have successfully stopped the trash dropper!"));
        return true;
    }
}

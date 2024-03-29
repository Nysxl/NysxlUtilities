package com.internal.nysxl.NysxlUtilities.CommandManager;

import com.internal.nysxl.NysxlUtilities.Utility.Commands.CommandInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final Map<String, CommandInterface> commands = new HashMap<>();

    private final JavaPlugin plugin;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(String name, CommandInterface command) {
        commands.put(name.toLowerCase(), command);
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandInterface cmd = commands.get(command.getName().toLowerCase());

        if(!cmd.hasPermission(sender)){
            sender.sendMessage("Incorrect Permissions.");
            return true;
        }

        if (cmd != null) {
            return cmd.onCommand(sender, args);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        CommandInterface cmd = commands.get(command.getName().toLowerCase());
        if (cmd != null) {
            return cmd.onTabComplete(sender, args);
        }
        return null;
    }
}
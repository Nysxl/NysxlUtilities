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

    public CommandManager(JavaPlugin plugin) {
        // Example of registering a command
        // registerCommand("example", new ExampleCommand());
    }

    public void registerCommand(String name, CommandInterface command) {
        commands.put(name.toLowerCase(), command);
        JavaPlugin.getProvidingPlugin(getClass()).getCommand(name).setExecutor(this);
        JavaPlugin.getProvidingPlugin(getClass()).getCommand(name).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandInterface cmd = commands.get(command.getName().toLowerCase());
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
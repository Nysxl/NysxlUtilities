package com.internal.nysxl.NysxlUtilities.Utility.Commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandInterface {
    boolean onCommand(CommandSender sender, String[] args);
    List<String> onTabComplete(CommandSender sender, String[] args);
    boolean hasPermission(CommandSender sender);
}

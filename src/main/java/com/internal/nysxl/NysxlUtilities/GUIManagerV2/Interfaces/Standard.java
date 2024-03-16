package com.internal.nysxl.NysxlUtilities.GUIManagerV2.Interfaces;

import com.internal.nysxl.NysxlUtilities.GUIManagerV2.Buttons.Button;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class Standard implements Executor {
    Function<Player, String> action;

    public Standard(Function<Player, String> action){
        this.action = action;
    }

    @Override
    public String execute(Player player) {
        return action.apply(player);
    }

    public void setAction(Function<Player, String> action){
        this.action = action;
    }
}
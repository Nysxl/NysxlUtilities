package com.internal.nysxl.NysxlUtilities.GUIManagerV2.Interfaces;

import com.internal.nysxl.NysxlUtilities.GUIManagerV2.Buttons.Button;
import org.bukkit.entity.Player;

import java.util.function.Function;

public class Toggleable implements Executor {

    private Function<Player, String> action;
    private Function<Player, String> toggledAction;
    boolean state = false;

    public Toggleable(Function<Player, String> action, Function<Player, String> toggledAction){
        this.action = action;
        this.toggledAction = toggledAction;
    }

    @Override
    public String execute(Player player) {
        state = !state;
        return state ? action.apply(player) : toggledAction.apply(player);
    }

    public boolean getState(){
        return state;
    }

    public void setAction(Function<Player, String> action){
        this.action = action;
    }

    public void setToggledAction(Function<Player, String> toggledAction){
        this.toggledAction = toggledAction;
    }
}
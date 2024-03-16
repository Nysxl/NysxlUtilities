package com.internal.nysxl.NysxlUtilities.GUIManagerV2.Buttons;

import com.internal.nysxl.NysxlUtilities.GUIManagerV2.GUIs.GUI;
import com.internal.nysxl.NysxlUtilities.GUIManagerV2.Interfaces.Executor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Button {

    private final UUID uuid;
    private ItemStack item;
    private int slot;
    private final HashMap<MouseClick, Executor> action = new HashMap<>();

    /**
     * Mouse Click Types.
     */
    public enum MouseClick{
        LEFT_CLICK,
        SHIFT_LEFT_CLICK,
        RIGHT_CLICK,
        SHIFT_RIGHT_CLICK,
        MIDDLE_CLICK,
        DROP,
        CONTROL_DROP;

        public static MouseClick getClickType(InventoryClickEvent e){
            switch(e.getClick()){
                case RIGHT: return RIGHT_CLICK;
                case SHIFT_RIGHT: return SHIFT_RIGHT_CLICK;
                case SHIFT_LEFT: return SHIFT_LEFT_CLICK;
                case MIDDLE: return MIDDLE_CLICK;
                case DROP: return DROP;
                case CONTROL_DROP: return CONTROL_DROP;
                default: return LEFT_CLICK;
            }
        }

    }

    public Button(ItemStack item){
        this.uuid = UUID.randomUUID();
        this.item = item;
    }

    //region factoryBuilder

    public Button setItem(ItemStack item){
        this.item = item;
        return this;
    }

    public Button withSlot(int slot){
        this.slot = slot;
        return this;
    }

    public Button withAction(MouseClick clickType, Executor executor){
        this.action.put(clickType, executor);
        return this;
    }

    //endregion

    public void placeInGUI(GUI gui){
        gui.getInventory().setItem(slot, item);
    }

    public String executeAction(InventoryClickEvent e, Player player){
        MouseClick click = MouseClick.getClickType(e);

        if(!action.containsKey(click)) return "";
        String getAction = action.get(click).execute(player);
        return getAction;
    }

    //region Getters
    public UUID getUuid() {
        return uuid;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public HashMap<MouseClick, Executor> getAction() {
        return action;
    }
    //endregion

}

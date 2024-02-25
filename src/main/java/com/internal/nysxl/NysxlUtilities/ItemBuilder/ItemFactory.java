package com.internal.nysxl.NysxlUtilities.ItemBuilder;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemFactory {

    private ItemStack itemStack;
    private ItemMeta meta;

    public ItemFactory(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public ItemFactory withMeta(ItemMeta meta){
        this.meta = meta;
        return this;
    }

    public void setLore(String... lore){
        if(meta == null) meta = itemStack.getItemMeta();
        if(meta == null) return;
        meta.setLore(List.of(lore));
    }









}

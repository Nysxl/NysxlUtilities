package com.internal.nysxl.NysxlUtilities.Utility;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ConditionEvaluator {

    private static final Map<String, BiFunction<Object, Object, Boolean>> conditions = new HashMap<>();

    static {
        // Initialize conditions with lambda expressions or method references
        conditions.put("hasItem", (player, item) -> player instanceof Player && item instanceof ItemStack && ((Player)player).getInventory().contains((ItemStack)item));
        conditions.put("hasMaterial", (player, material) -> player instanceof Player && material instanceof Material && ((Player)player).getInventory().contains((Material)material));
        conditions.put("isSneaking", (player, ignored) -> player instanceof Player && ((Player)player).isSneaking());
        conditions.put("isFlying", (player, ignored) -> player instanceof Player && ((Player)player).isFlying());
        conditions.put("hasPermission", (player, permission) -> player instanceof Permissible && permission instanceof String && ((Permissible)player).hasPermission((String)permission));
        conditions.put("playerIsCreator", (player, ignored) -> player instanceof Player && ((Player) player).getGameMode().equals(GameMode.CREATIVE));
        conditions.put("playerIsAdventure", (player, ignored) -> player instanceof Player && ((Player) player).getGameMode().equals(GameMode.ADVENTURE));
        conditions.put("playerIsSurvival", (player, ignored) -> player instanceof Player && ((Player) player).getGameMode().equals(GameMode.SURVIVAL));
        conditions.put("playerIsSleeping", (player, ignored) -> player instanceof Player && ((Player) player).isSleeping());
    }

    /**
     * evaluates the condition based on a string and objects passed in.
     * @param conditionKey the type of check being done.
     * @param player the player the check is being done on.
     * @param parameter an extra parameter the condition might be after.
     * @return returns true or false.
     */
    public static boolean evaluate(String conditionKey, Object player, Object parameter) {
        return conditions.getOrDefault(conditionKey, (p, u) -> false).apply(player, parameter);
    }

    /**
     * only returns true if all boolean conditions match the "match" condition.
     * @param match the condition all chain conditions will be checked against.
     * @param chain a list of all the conditions being checked.
     * @return true or false.
     */
    public static boolean chainAndConditions(Boolean match, Boolean... chain){
        return Arrays.stream(chain).allMatch(s->s.equals(match));
    }

    /**
     * returns true if either condition is true.
     * @param boolA condition A.
     * @param boolB condition B.
     * @return true or false.
     */
    public static boolean chainOrConditions(Boolean boolA, Boolean boolB){
        return boolA || boolB;
    }

}

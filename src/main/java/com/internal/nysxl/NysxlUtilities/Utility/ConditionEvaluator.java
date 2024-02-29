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

    private static final Map<ConditionKeys, BiFunction<Object, Object, Boolean>> conditions = new HashMap<>();

    public enum ConditionKeys{
        HAS_ITEM,
        HAS_MATERIAL,
        IS_SNEAKING,
        IS_FLYING,
        HAS_PERMISSION,
        IS_CREATIVE,
        IS_ADVENTURE,
        IS_SURVIVAL,
        IS_SLEEPING,
    }

    static {
        conditions.put(ConditionKeys.HAS_ITEM, (player, item) -> player instanceof Player && item instanceof ItemStack && ((Player)player).getInventory().contains((ItemStack)item));
        conditions.put(ConditionKeys.HAS_MATERIAL, (player, material) -> player instanceof Player && material instanceof Material && ((Player)player).getInventory().contains((Material)material));
        conditions.put(ConditionKeys.IS_SNEAKING, (player, ignored) -> player instanceof Player && ((Player)player).isSneaking());
        conditions.put(ConditionKeys.IS_FLYING, (player, ignored) -> player instanceof Player && ((Player)player).isFlying());
        conditions.put(ConditionKeys.HAS_PERMISSION, (player, permission) -> player instanceof Permissible && permission instanceof String && ((Permissible)player).hasPermission((String)permission));
        conditions.put(ConditionKeys.IS_CREATIVE, (player, ignored) -> player instanceof Player && ((Player) player).getGameMode().equals(GameMode.CREATIVE));
        conditions.put(ConditionKeys.IS_ADVENTURE, (player, ignored) -> player instanceof Player && ((Player) player).getGameMode().equals(GameMode.ADVENTURE));
        conditions.put(ConditionKeys.IS_SURVIVAL, (player, ignored) -> player instanceof Player && ((Player) player).getGameMode().equals(GameMode.SURVIVAL));
        conditions.put(ConditionKeys.IS_SLEEPING, (player, ignored) -> player instanceof Player && ((Player) player).isSleeping());
    }

    /**
     * evaluates the condition based on a string and objects passed in.
     * @param conditionKey the type of check being done.
     * @param player the player the check is being done on.
     * @param parameter an extra parameter the condition might be after.
     * @return returns true or false.
     */
    public static boolean evaluate(ConditionKeys conditionKey, Player player, Object parameter) {
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

package com.internal.nysxl.NysxlUtilities.Utility.StringFactory;

import com.internal.nysxl.NysxlUtilities.Utility.ConditionEvaluator;
import org.bukkit.entity.Player;

public class StringFactory {

    StringBuilder string;

    /**
     * Constructs the StringFactory
     * @param string the string to start building with
     */
    public StringFactory(String string){
        this.string = new StringBuilder(string);
    }

    /**
     * constructs the string factory with an empty string.
     */
    public StringFactory(){
        this.string = new StringBuilder();
    }

    /**
     * adds a string to the stringBuilder.
     * @param string the string to be appended.
     * @return returns this instance of the string builder.
     */
    public StringBuilder addToString(String string){
        return this.string.append(string);
    }

    /**
     * appends a given string based on an evaluated condition.
     * @param conditionKey the condition to be evaluated.
     * @param player the player entity for the condition to be run on.
     * @param parameter the parameters for the evaluator.
     * @param str1 the string to append if the condition returns true.
     * @param str2 the string to append if the condition returns false.
     * @return the instance of the StringBuilder.
     */
    public StringBuilder appendConditional(String conditionKey, Player player, Object parameter, String str1, String str2){
        if(ConditionEvaluator.evaluate(conditionKey,player,parameter)){
            string.append(str1);
        } else {
            string.append(str2);
        }
        return string;
    }
}

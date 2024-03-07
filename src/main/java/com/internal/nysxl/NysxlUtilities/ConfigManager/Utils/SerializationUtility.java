package com.internal.nysxl.NysxlUtilities.ConfigManager.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SerializationUtility {
    // Improved serialize method with generic type support and error handling
    public <T> Map<String, Object> serialize(String path, Function<T, Map<String, Object>> serializer, T... object) {
        Map<String, Object> serializedData = new HashMap<>();
        List<Map<String, Object>> serializedItems = new ArrayList<>();
        for (T item : object) {
            try {
                serializedItems.add(serializer.apply(item));
            } catch (Exception e) {
                // Handle serialization error, log it or throw a custom exception
            }
        }
        serializedData.put(path, serializedItems);
        return serializedData;
    }

    // Improved deserialize method with generic type support and error handling
    public <T> List<T> deserialize(Map<String, Object> serializedData, String path, Function<Map<String, Object>, T> deserializer) {
        List<T> items = new ArrayList<>();
        List<Map<String, Object>> serializedItems = (List<Map<String, Object>>) serializedData.get(path);
        if (serializedItems != null) {
            for (Map<String, Object> serializedItem : serializedItems) {
                try {
                    items.add(deserializer.apply(serializedItem));
                } catch (Exception e) {
                    // Handle deserialization error, log it or throw a custom exception
                }
            }
        }
        return items;
    }

    /**
     *  HOW TO USE.
     *     SerializationUtility utility = new SerializationUtility();
     *     Map<String, Object> serialized = utility.serialize(#PATH, #TYPE::serialize, ItemStack[]);
     *     List<#TYPE> items = utility.deserialize(serialized, #PATH, #TYPE::deserialize);
     */

}

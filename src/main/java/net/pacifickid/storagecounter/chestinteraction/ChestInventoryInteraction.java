package net.pacifickid.storagecounter.chestinteraction;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class ChestInventoryInteraction {
    public static Map<String, Integer> getMap(Inventory inventory, Map<String, Integer> res) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!(stack.getItem() instanceof AirBlockItem))
                if (res.containsKey(stack.getItem().toString())) {
                    res.put(stack.getItem().toString(), stack.getCount() + res.get(stack.getItem().toString()));
                } else {
                    res.put(stack.getItem().toString(), stack.getCount());
                }
        }
        return res;
    }

    public static void toExel(Map<String, Integer> res) {

    }
}

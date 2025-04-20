package net.pacifickid.storagecounter.storage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public abstract class StorageInteraction {
    public static Map<String, Long> inventoryToMap (Inventory inventory, Map<String, Long> res) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!(stack.getItem() instanceof AirBlockItem))
                if (res.containsKey(stack.getItem().toString())) {
                    res.put(stack.getItem().toString(), stack.getCount() + res.get(stack.getItem().toString()));
                } else {
                    res.put(stack.getItem().toString(), (long) stack.getCount());
                }
        }
        return res;
    }

    public static Map<String, Long> countAround (PlayerEntity player, int r, int h) {
        Map<String, Long> res = new HashMap<String, Long>();
        BlockPos center = player.getBlockPos();  // позиция игрока
        World world = player.getWorld();
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -h; dy <= h; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos pos = center.add(dx, dy, dz);
                    if (world.getBlockEntity(pos) instanceof Inventory) {
                        res = inventoryToMap((Inventory) world.getBlockEntity(pos), res);
                    }
                }
            }
        }
        return res;
    }

    /*public static Map<String, Integer> countForCord (BlockPos blockPos1, BlockPos blockPos2) {
        Map<String, Integer> res = new HashMap<String, Integer>();
        for (int dx )
    }*/
}

package net.pacifickid.storagecounter.storage;

import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.AirBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StorageInteraction {
    private static Map<Item, Long> includeStackToMap (ItemStack stack, Map<Item, Long> res) {
        if (stack.getItem() == Items.SHULKER_BOX) {
            ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);
            if (container == null) {
                return res;
            }
            Iterable<ItemStack> shulkerInventory = container.iterateNonEmpty();
            for (ItemStack shulkerStack : shulkerInventory) {
                includeStackToMap(shulkerStack, res);
            }
        } else if (stack.getItem() == Items.BUNDLE) {
            BundleContentsComponent contents = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (contents == null) {
                return res;
            }
            Iterable<ItemStack> items = contents.iterate();
            for (ItemStack item : items) {
                includeStackToMap(item, res);
            }
        } else if (!(stack.getItem() instanceof AirBlockItem)) {
            res.merge(stack.getItem(), (long) stack.getCount(), Long::sum);
        }
        return res;
    }

    public static Map<Item, Long> inventoryToMap (Inventory inventory, Map<Item, Long> res) {
        for (int i = 0; i < inventory.size(); i++) {
            includeStackToMap(inventory.getStack(i), res);
        }
        return res;
    }

    public static Map<Item, Long> inventoryToMap (List<ItemStack> stacks, Map<Item, Long> res) {
        for (ItemStack stack : stacks) {
            includeStackToMap(stack, res);
        }
        return res;
    }

    public static Map<Item, Long> countAround (PlayerEntity player, int r, int h) {
        Map<Item, Long> res = new HashMap<Item, Long>();
        BlockPos center = player.getBlockPos();  // позиция игрока
        World world = player.getWorld();
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -h; dy <= h; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos pos = center.add(dx, dy, dz);
                    if (world.getBlockEntity(pos) instanceof Inventory) {
                        inventoryToMap((Inventory) world.getBlockEntity(pos), res);
                    }
                }
            }
        }
        return res;
    }

    public static Map<Item, Long> countForCord (World world, BlockPos blockPos1, BlockPos blockPos2) {
        Map<Item, Long> res = new HashMap<Item, Long>();
        for (int dx = 0; dx <= blockPos2.getX() - blockPos1.getX(); dx++) {
            for (int dy = 0; dy <= blockPos2.getY() - blockPos1.getY(); dy++) {
                for (int dz = 0; dz <= blockPos2.getZ() - blockPos1.getZ(); dz++) {
                    BlockPos pos = blockPos1.add(dx, dy, dz);
                    if (world.getBlockEntity(pos) instanceof Inventory) {
                        inventoryToMap((Inventory) world.getBlockEntity(pos), res);
                    }
                }
            }
        }
        return res;
    }
}

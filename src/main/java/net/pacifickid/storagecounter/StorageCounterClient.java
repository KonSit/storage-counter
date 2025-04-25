package net.pacifickid.storagecounter;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.pacifickid.storagecounter.excel.ExcelInteraction;

import static net.pacifickid.storagecounter.storage.StorageInteraction.countAround;
import static net.pacifickid.storagecounter.storage.StorageInteraction.countForCord;

public class StorageCounterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("countaround")
                    .then(CommandManager.argument("r", IntegerArgumentType.integer())
                            .then(CommandManager.argument("h", IntegerArgumentType.integer())
                                    .executes(context -> {
                                        try {
                                            if (ExcelInteraction.toExcel(countAround(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "r"), IntegerArgumentType.getInteger(context, "h")))) {
                                                context.getSource().sendFeedback(() -> Text.literal("Exel table created successfully!"), false);
                                            } else {
                                                context.getSource().sendFeedback(() -> Text.literal("There are no storages in the given area, or all of them are empty"), false);
                                            }
                                        } catch (Exception e) {
                                            context.getSource().sendFeedback(() -> Text.literal(e.getMessage()), false);
                                        }
                                        return 1;
                                    })
                            )
                    )
            );
        });


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("count")
                    .then(CommandManager.argument("x1", IntegerArgumentType.integer())
                            .then(CommandManager.argument("y1", IntegerArgumentType.integer())
                                    .then(CommandManager.argument("z1", IntegerArgumentType.integer())
                                            .then(CommandManager.argument("x2", IntegerArgumentType.integer())
                                                    .then(CommandManager.argument("y2", IntegerArgumentType.integer())
                                                            .then(CommandManager.argument("z2", IntegerArgumentType.integer())
                                                                .executes(context -> {
                                                                    try {
                                                                        if (ExcelInteraction.toExcel(countForCord(context.getSource().getPlayer().getWorld(), new BlockPos(new Vec3i(IntegerArgumentType.getInteger(context, "x1"), IntegerArgumentType.getInteger(context, "y1"), IntegerArgumentType.getInteger(context, "z1"))),
                                                                                new BlockPos(new Vec3i(IntegerArgumentType.getInteger(context, "x2"), IntegerArgumentType.getInteger(context, "y2"), IntegerArgumentType.getInteger(context, "z2")))))) {
                                                                            context.getSource().sendFeedback(() -> Text.literal("Exel table created successfully!"), false);
                                                                        } else {
                                                                            context.getSource().sendFeedback(() -> Text.literal("There are no storages in the given area, or all of them are empty"), false);
                                                                        }
                                                                    } catch (Exception e) {
                                                                        context.getSource().sendFeedback(() -> Text.literal(e.getMessage()), false);
                                                                    }
                                                                    return 1;
                                                                }
                                                                )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
        });
    }
}

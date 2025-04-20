package net.pacifickid.storagecounter;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.pacifickid.storagecounter.exel.ExelInteraction;
import net.pacifickid.storagecounter.storage.StorageInteraction;

import static net.pacifickid.storagecounter.storage.StorageInteraction.countAround;

public class StorageCounterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("countaround")
                    .then(CommandManager.argument("r", IntegerArgumentType.integer())
                            .then(CommandManager.argument("h", IntegerArgumentType.integer())
                                    .executes(context -> {
                                        try {
                                            if (ExelInteraction.toExel(countAround(context.getSource().getPlayer(), IntegerArgumentType.getInteger(context, "r"), IntegerArgumentType.getInteger(context, "h")))) {
                                                context.getSource().sendFeedback(() -> Text.literal("Exel table created successfully!"), false);
                                            } else {
                                                context.getSource().sendFeedback(() -> Text.literal("There are no storages in the given area, or all of them are empty"), false);
                                            }
                                        } catch (Exception e) {
                                            context.getSource().sendFeedback(() -> Text.literal(e.getMessage()), false);
                                        }
                                        return 1;
                                    }))));
        });
    }
}

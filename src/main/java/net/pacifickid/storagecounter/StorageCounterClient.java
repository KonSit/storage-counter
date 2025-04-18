package net.pacifickid.storagecounter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

public class StorageCounterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            System.out.println("Открыт экран: " + screen.getClass().getName());
        });
    }
}

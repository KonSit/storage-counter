package net.pacifickid.storagecounter.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.pacifickid.storagecounter.StorageCounter;
import net.pacifickid.storagecounter.chestinteraction.ChestInventoryInteraction;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.function.Supplier;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Mixin(GenericContainerScreen.class)
public abstract class ChestScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        //System.out.println("Сундук открыт!");
        GenericContainerScreen screen = (GenericContainerScreen) (Object) this;
        Screens.getButtons(screen).add(new ButtonWidget.Builder(Text.literal("E"), (button) -> {

            //GenericContainerScreenHandler handler = screen.getScreenHandler();
            StorageCounter.LOGGER.info(ChestInventoryInteraction.getMap(screen.getScreenHandler().getInventory(), new HashMap<String, Integer>()).toString());
        }).position(5, 5).size(15, 15).build());
    }
}

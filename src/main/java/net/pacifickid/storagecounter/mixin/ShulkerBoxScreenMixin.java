package net.pacifickid.storagecounter.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.pacifickid.storagecounter.excel.ExcelInteraction;
import net.pacifickid.storagecounter.storage.StorageInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(ShulkerBoxScreen.class)
public abstract class ShulkerBoxScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        ShulkerBoxScreen screen = (ShulkerBoxScreen) (Object) this;
        Screens.getButtons(screen).add(new ButtonWidget.Builder(Text.literal("E"), (button) -> {
            ExcelInteraction.toExcel(StorageInteraction.inventoryToMap(screen.getScreenHandler().getStacks().subList(0, 27), new HashMap<Item, Long>()));
        }).position(5, 5).size(15, 15).build());
    }
}

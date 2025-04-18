package net.pacifickid.storagecounter.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(GenericContainerScreen.class)
public abstract class ChestScreenMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        System.out.println("Сундук открыт!");
        GenericContainerScreen screen = (GenericContainerScreen) (Object) this;
        Screens.getButtons(screen).add(
                Screens.getButtons(screen).add(
                        //ButtonWidget.builder(n);
                );
        )
    }
}

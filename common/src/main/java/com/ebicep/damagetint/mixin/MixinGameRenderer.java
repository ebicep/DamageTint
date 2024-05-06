package com.ebicep.damagetint.mixin;

import com.ebicep.damagetint.DamageTint;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {

    @Inject(method = "render", at = @At("HEAD"))
    private void render(float f, long l, boolean bl, CallbackInfo ci) {
        if (DamageTint.INSTANCE.getUpdateTintColor()) {
            DamageTint.INSTANCE.resetTintColor();
        }
    }

}

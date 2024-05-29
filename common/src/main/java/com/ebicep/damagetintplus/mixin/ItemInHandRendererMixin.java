package com.ebicep.damagetintplus.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    @Inject(method = "renderItem", at = @At("HEAD"))
    public void renderItemHead(
            LivingEntity livingEntity,
            ItemStack itemStack,
            ItemDisplayContext itemDisplayContext,
            boolean bl,
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            CallbackInfo ci,
            @Share("damagetintplus$redOverlay") LocalBooleanRef argRef
    ) {
        argRef.set(livingEntity.hurtTime > 0 && itemDisplayContext == ItemDisplayContext.HEAD);
    }

    @ModifyArg(
            method = "renderItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"
            ),
            index = 8
    )
    public int renderStatic(int i, @Share("damagetintplus$redOverlay") LocalBooleanRef argRef) {
        if (argRef.get()) {
            return OverlayTexture.RED_OVERLAY_V;
        } else {
            return i;
        }
    }

}

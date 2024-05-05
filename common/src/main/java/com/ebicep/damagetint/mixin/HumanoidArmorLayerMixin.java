package com.ebicep.damagetint.mixin;

import com.ebicep.damagetint.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {

    @Unique
    boolean damagetint$hurt = false;

    @Inject(
            method = "renderArmorPiece",
            at = @At(value = "HEAD")
    )
    public void renderArmorPiece(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            LivingEntity livingEntity,
            EquipmentSlot equipmentSlot,
            int i,
            HumanoidModel<?> humanoidModel,
            CallbackInfo ci
    ) {
        damagetint$hurt = livingEntity.hurtTime > 0;
    }

    @Inject(
            method = "renderModel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V"
            ),
            cancellable = true
    )
    public void renderModel(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            ArmorItem armorItem,
            HumanoidModel<?> humanoidModel,
            boolean bl,
            float f,
            float g,
            float h,
            String string,
            CallbackInfo ci
    ) {
        if (damagetint$hurt && Config.INSTANCE.getValues().getShowOnPlayerArmor()) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(getArmorLocation(armorItem, bl, string)));
            humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.RED_OVERLAY_V, f, g, h, 1.0F);
            ci.cancel();
        }
    }

    @Shadow
    private ResourceLocation getArmorLocation(ArmorItem armorItem, boolean bl, @Nullable String string) {
        return null;
    }


}

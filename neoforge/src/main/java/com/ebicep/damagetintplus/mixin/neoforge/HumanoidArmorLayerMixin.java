package com.ebicep.damagetintplus.mixin.neoforge;

import com.ebicep.damagetintplus.DamageTintPlus;
import com.ebicep.damagetintplus.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {

    @Unique
    boolean damagetintplus$hurt = false;

    @Inject(
            method = "renderArmorPiece",
            at = @At("HEAD")
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
        damagetintplus$hurt = livingEntity.hurtTime > 0;
    }

    @Inject(
            method = "renderModel(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/item/ArmorItem;Lnet/minecraft/client/model/Model;ZFFFLnet/minecraft/resources/ResourceLocation;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    public void renderModel(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            ArmorItem arg3,
            Model humanoidModel,
            boolean bl,
            float f,
            float g,
            float h,
            ResourceLocation armorResource,
            CallbackInfo ci
    ) {
        if (damagetintplus$hurt && Config.INSTANCE.getValues().getShowOnPlayerArmor()) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(DamageTintPlus.INSTANCE.getOverrideRenderType(armorResource));
            humanoidModel.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.RED_OVERLAY_V, f, g, h, 1.0F);
            ci.cancel();
        }
    }

}

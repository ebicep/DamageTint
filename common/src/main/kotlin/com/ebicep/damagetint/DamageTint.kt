package com.ebicep.damagetint

import com.ebicep.damagetint.config.Config
import com.ebicep.damagetint.config.ConfigScreen
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import dev.architectury.event.events.client.ClientTickEvent
import net.minecraft.client.Minecraft
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.Color

const val MOD_ID = "damagetint"

object DamageTint {

    val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    var updateTintColor = false

    fun init() {
        Config.load()

        ClientTickEvent.CLIENT_POST.register {
            ConfigScreen.handleOpenScreen()
        }
    }

    private fun isEnabled(): Boolean {
        return Config.values.enabled
    }

    fun showOnArmor(): Boolean {
        return isEnabled() && Config.values.showOnArmor
    }

    fun shouldUpdateTintColor(): Boolean {
        return updateTintColor
    }

    fun resetTintColor() {
        updateTintColor = false
        val texture = Minecraft.getInstance().gameRenderer.overlayTexture().texture
        val nativeImage: NativeImage? = texture.pixels

        if (nativeImage == null) {
            LOGGER.error("Failed to allocate memory for overlay color texture")
            return
        }

        val color: Int = getTintColor()

        LOGGER.info("Setting overlay color to $color")

        for (i in 0..15) {
            for (j in 0..15) {
                if (i < 8) {
                    nativeImage.setPixelRGBA(j, i, color)
                } else {
                    val k = ((1.0f - j.toFloat() / 15.0f * 0.75f) * 255.0f).toInt()
                    nativeImage.setPixelRGBA(j, i, k shl 24 or 16777215)
                }
            }
        }

        RenderSystem.activeTexture(33985)
        texture.bind()
        nativeImage.upload(0, 0, 0, 0, 0, nativeImage.width, nativeImage.height, false, true, false, false)
        RenderSystem.activeTexture(33984)
    }

    private fun getTintColor(): Int {
        return if (isEnabled()) {
            argbToAbgr(Color(Config.values.color, true).rgb)
        } else {
            -1308622593
        }
    }

    private fun argbToAbgr(argb: Int): Int {
        val alpha = argb shr 24 and 0xFF
        val red = argb shr 16 and 0xFF
        val green = argb shr 8 and 0xFF
        val blue = argb and 0xFF
        return (alpha shl 24) or (blue shl 16) or (green shl 8) or red
    }

}
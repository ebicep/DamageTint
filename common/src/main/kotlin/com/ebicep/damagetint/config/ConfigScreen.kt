package com.ebicep.damagetint.config

import dev.architectury.injectables.annotations.ExpectPlatform
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen

object ConfigScreen {

    var open = false

    fun handleOpenScreen() {
        if (open) {
            open = false
            Minecraft.getInstance().setScreen(getConfigScreen())
        }
    }

    @JvmStatic
    @ExpectPlatform
    fun getConfigScreen(previousScreen: Screen? = null): Screen {
        throw AssertionError()
    }

}
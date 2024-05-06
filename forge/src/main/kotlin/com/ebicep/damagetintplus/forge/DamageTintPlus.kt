package com.ebicep.damagetintplus.forge

import com.ebicep.damagetintplus.DamageTintPlus
import com.ebicep.damagetintplus.MOD_ID
import com.ebicep.damagetintplus.config.ConfigScreen
import com.ebicep.damagetintplus.events.forge.ClientCommandRegistration
import dev.architectury.platform.forge.EventBuses
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.minecraftforge.client.ConfigScreenHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.ModLoadingContext
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@Mod(MOD_ID)
object DamageTintPlusForge {

    init {
        EventBuses.registerModEventBus(MOD_ID, MOD_BUS)
        DamageTintPlus.init()
        MinecraftForge.EVENT_BUS.addListener(ClientCommandRegistration::registerCommands)
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory::class.java) {
            ConfigScreenHandler.ConfigScreenFactory { _: Minecraft, parent: Screen ->
                ConfigScreen.getConfigScreen(parent)
            }
        }
    }

}

package com.ebicep.damagetintplus.neoforge

import com.ebicep.damagetintplus.DamageTintPlus
import com.ebicep.damagetintplus.MOD_ID
import com.ebicep.damagetintplus.config.ConfigScreen
import com.ebicep.damagetintplus.events.neoforge.ClientCommandRegistration
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.Screen
import net.neoforged.fml.ModLoadingContext
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.ConfigScreenHandler
import net.neoforged.neoforge.common.NeoForge


@Mod(MOD_ID)
object DamageTintPlusForge {

    init {
        DamageTintPlus.init()
        NeoForge.EVENT_BUS.addListener(ClientCommandRegistration::registerCommands)
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory::class.java) {
            ConfigScreenHandler.ConfigScreenFactory { _: Minecraft, parent: Screen ->
                ConfigScreen.getConfigScreen(parent)
            }
        }
    }

}

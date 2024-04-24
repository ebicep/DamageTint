package com.ebicep.damagetint.forge

import com.ebicep.damagetint.DamageTint
import com.ebicep.damagetint.MOD_ID
import com.ebicep.damagetint.events.forge.ClientCommandRegistration
import dev.architectury.platform.forge.EventBuses
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@Mod(MOD_ID)
object DamageTintForge {

    init {
        EventBuses.registerModEventBus(MOD_ID, MOD_BUS)
        DamageTint.init()
        MinecraftForge.EVENT_BUS.addListener(ClientCommandRegistration::registerCommands)
    }

}
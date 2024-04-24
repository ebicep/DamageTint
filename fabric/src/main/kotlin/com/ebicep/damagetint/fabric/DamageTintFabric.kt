package com.ebicep.damagetint.fabric

import com.ebicep.damagetint.DamageTint
import com.ebicep.damagetint.events.fabric.ClientCommandRegistration
import net.fabricmc.api.ModInitializer


object DamageTintFabric : ModInitializer {

    override fun onInitialize() {
        DamageTint.init()
        ClientCommandRegistration.registerCommands()
    }

}

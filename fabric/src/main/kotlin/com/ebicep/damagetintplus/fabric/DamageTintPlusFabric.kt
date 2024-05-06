package com.ebicep.damagetintplus.fabric

import com.ebicep.damagetintplus.DamageTintPlus
import com.ebicep.damagetintplus.events.fabric.ClientCommandRegistration
import net.fabricmc.api.ModInitializer


object DamageTintPlusFabric : ModInitializer {

    override fun onInitialize() {
        DamageTintPlus.init()
        ClientCommandRegistration.registerCommands()
    }

}

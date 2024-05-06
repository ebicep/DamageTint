package com.ebicep.damagetintplus.events.forge

import com.ebicep.damagetintplus.config.ConfigScreen
import com.mojang.brigadier.Command
import net.minecraft.commands.Commands
import net.minecraftforge.client.event.RegisterClientCommandsEvent


object ClientCommandRegistration {

    fun registerCommands(event: RegisterClientCommandsEvent) {
        event.dispatcher.register(
            Commands.literal("damagetintplus")
                .executes {
                    ConfigScreen.open = true
                    Command.SINGLE_SUCCESS
                }
        )
    }

}
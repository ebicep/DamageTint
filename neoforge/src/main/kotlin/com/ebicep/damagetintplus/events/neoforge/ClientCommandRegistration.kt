package com.ebicep.damagetintplus.events.neoforge

import com.ebicep.damagetintplus.config.ConfigScreen
import com.mojang.brigadier.Command
import net.minecraft.commands.Commands
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
object ClientCommandRegistration {

    @SubscribeEvent
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
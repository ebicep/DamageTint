package com.ebicep.damagetint.config.fabric

import com.ebicep.damagetint.config.Config
import me.shedaniel.clothconfig2.api.ConfigBuilder
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder
import me.shedaniel.math.Color
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import java.util.function.Consumer

object ConfigScreenImpl {

    private fun ConfigEntryBuilder.booleanToggle(translatable: String, variable: Boolean, saveConsumer: Consumer<Boolean>) =
        startBooleanToggle(Component.translatable(translatable), variable)
            .setDefaultValue(variable)
            .setTooltip(Component.translatable("$translatable.tooltip"))
            .setSaveConsumer(saveConsumer)
            .build()


    @JvmStatic
    fun getConfigScreen(previousScreen: Screen? = null): Screen {
        val builder: ConfigBuilder = ConfigBuilder.create()
            .setParentScreen(previousScreen)
            .setTitle(Component.translatable("damagetint.title"))
            .setSavingRunnable(Config::save)
            .transparentBackground()
        val entryBuilder: ConfigEntryBuilder = builder.entryBuilder()
        val general = builder.getOrCreateCategory(Component.translatable("damagetint.config.general.title"))
        general.addEntry(
            entryBuilder.booleanToggle("damagetint.config.enabled", Config.values.enabled)
            { Config.values.enabled = it }
        )
        general.addEntry(
            entryBuilder.startAlphaColorField(Component.translatable("damagetint.config.color"), Color.ofTransparent(Config.values.color))
                .setTooltip(Component.translatable("damagetint.config.color.tooltip"))
                .setAlphaMode(true)
                .setDefaultValue2 { Color.ofTransparent(Config.values.color) }
                .setSaveConsumer2 { Config.values.color = it.color }
                .build()
        )
        general.addEntry(
            entryBuilder.booleanToggle("damagetint.config.showOnArmor.enabled", Config.values.showOnArmor)
            { Config.values.showOnArmor = it }
        )
        return builder.build()
    }

}

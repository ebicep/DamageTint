package com.ebicep.damagetint.config

import com.ebicep.damagetint.DamageTint
import com.ebicep.damagetint.MOD_ID
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.awt.Color
import java.io.File

private val json = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
    prettyPrint = true
}
val configDirectoryPath: String
    get() = ConfigDirectory.getConfigDirectory().toString() + "\\damagetint"

object Config {

    var values = ConfigVariables()

    fun save() {
        val configDirectory = File(configDirectoryPath)
        if (!configDirectory.exists()) {
            configDirectory.mkdir()
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        configFile.writeText(json.encodeToString(ConfigVariables.serializer(), values))
    }

    fun load() {
        DamageTint.LOGGER.info("Config Directory: ${ConfigDirectory.getConfigDirectory().toAbsolutePath().normalize()}")
        val configDirectory = File(configDirectoryPath)
        if (!configDirectory.exists()) {
            return
        }
        val configFile = File(configDirectory, "$MOD_ID.json")
        if (configFile.exists()) {
            val json = Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                encodeDefaults = true
            }
            values = json.decodeFromString(ConfigVariables.serializer(), configFile.readText())
            loadValues()
        }
    }

    private fun loadValues() {
        if (values.overrideVanillaColor) {
            DamageTint.updateTintColor = true
        }
    }

}

@Serializable
data class ConfigVariables(
    var showOnPlayerArmor: Boolean = true,
    var showOnHorseArmor: Boolean = true,
) {
    var overrideVanillaColor: Boolean = true
        set(value) {
            if (value != field) {
                field = value
                DamageTint.updateTintColor = true
            }
        }
    var overrideColor: Int = Color(255, 0, 0, 178).rgb // -1291911168
        set(value) {
            if (value != field) {
                field = value
                DamageTint.updateTintColor = true
            }
        }
}

package com.ebicep.damagetint.config

import dev.architectury.injectables.annotations.ExpectPlatform
import java.nio.file.Path

object ConfigDirectory {

    @JvmStatic
    @ExpectPlatform
    fun getConfigDirectory(): Path {
        throw AssertionError()
    }
}
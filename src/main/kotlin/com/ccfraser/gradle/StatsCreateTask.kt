package com.ccfraser.gradle

import org.gradle.api.tasks.OutputFile
import java.io.ByteArrayOutputStream
import java.io.File

open class StatsCreateTask : BaseWebpackTask() {
    init {
        description = "Does a webpack build with statistics output ready for StatsDisplayTask to analyse"
    }

    @OutputFile
    val statsFile = settings.statsDirTemplate.map { File(settings.convert(it) + "/stats.json") }

    override fun exec() {
        // We do use a -p for production, though supposedly the setting in the config overrides it anyway.
        standardOutput = ByteArrayOutputStream()
        commandLine("${webpackBinDirectory.get()}/webpack-cli", "-p",
                "--config", webpackProdConfigFile.get().toString(), "--profile", "--json")
//        println("commandLine = $commandLine")

        super.exec()
        statsFile.get().writeText(standardOutput.toString())
    }
}
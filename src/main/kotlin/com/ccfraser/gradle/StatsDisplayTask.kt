package com.ccfraser.gradle

import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import java.io.File

open class StatsDisplayTask : BaseWebpackTask() {
    init {
        description = "Uses webpack-bundle-analyzer to show a view of the bundle sizes from output of the StatsCreateTask task. " +
                "Webpack-bundle-analyser should be added to your development dependencies before this will work (e.g. yarn add -D webpack-bundle-analyzer)"
    }

    @InputFile
    val statsFile = settings.statsDirTemplate.map { File(settings.convert(it) + "${File.separator}stats.json") }

    @InputDirectory
    val distributionDirectory = settings.distributionDirTemplate.map { File(settings.convert(it)) }

    @OutputFile
    val reportFile = settings.statsDirTemplate.map { File(settings.convert(it) + "${File.separator}bundle-stats-report.html") }

    override fun exec() {
        commandLine("${webpackBinDirectory.get()}${File.separator}webpack-bundle-analyzer", statsFile.get().toString(),
                distributionDirectory.get().toString(), "--mode", "static", "--report", reportFile.get().toString())
        super.exec()
    }
}
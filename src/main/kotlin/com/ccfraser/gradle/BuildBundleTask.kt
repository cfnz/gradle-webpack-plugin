package com.ccfraser.gradle

import java.io.File

open class BuildBundleTask : BaseWebpackTask() {
    init {
        description = "Run webpack build for a development or production build depending on settings.production"
    }

    override fun exec() {
        if (settings.production) {
            // We do use a -p for production, though supposedly the setting in the config overrides it anyway.
            commandLine("${webpackBinDirectory.get()}${File.separator}webpack-cli", "-p", "--config", webpackProdConfigFile.get().toString())
        } else {
            commandLine("${webpackBinDirectory.get()}${File.separator}webpack-cli", "--config", webpackDevConfigFile.get().toString())
        }
        super.exec()
    }
}

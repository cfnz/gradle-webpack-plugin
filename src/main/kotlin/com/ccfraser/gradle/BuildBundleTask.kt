package com.ccfraser.gradle

open class BuildBundleTask : BaseWebpackTask() {
    init {
        description = "Run webpack build for a development or production build depending on settings.production"
    }

    override fun exec() {
        if (settings.production) {
            // We do use a -p for production, though supposedly the setting in the config overrides it anyway.
            commandLine("${webpackBinDirectory.get()}/webpack-cli", "-p", "--config", webpackProdConfigFile.get().toString())
        } else {
            commandLine("${webpackBinDirectory.get()}/webpack-cli", "--config", webpackDevConfigFile.get().toString())
        }
        super.exec()
    }
}
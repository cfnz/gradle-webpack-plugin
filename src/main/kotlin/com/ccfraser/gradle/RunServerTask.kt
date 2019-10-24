package com.ccfraser.gradle

import java.io.File

open class RunServerTask : BaseWebpackTask() {
    init {
        description = "Run webpack server with a production or development build (possibly configured for hot module " +
                "replacement) depending on settings.production"
    }

    override fun exec() {
        if (settings.production) {
            commandLine(getWebpackExecutablePath("webpack-dev-server"), "-p", "--config", webpackProdConfigFile.get().toString())
        } else {
            if (settings.webpackDevServerHotReload) {
                commandLine(getWebpackExecutablePath("webpack-dev-server"), "--hot", "--config", webpackDevConfigFile.get().toString())
            } else {
                commandLine(getWebpackExecutablePath("webpack-dev-server"), "--config", webpackDevConfigFile.get().toString())
            }
        }
        super.exec()
    }
}
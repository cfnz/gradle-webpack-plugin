package com.ccfraser.gradle

import java.io.File

open class RunServerOpenBrowserTask : BaseWebpackTask() {
    init {
        description = "Same as RunServerTask but also opens up a browser to the deployed site"
    }

    override fun exec() {
        if (settings.production) {
            commandLine(getWebpackExecutablePath("webpack-dev-server"), "--open",
                "-p", "--config", webpackProdConfigFile.get().toString())
        } else {
            if (settings.webpackDevServerHotReload) {
                commandLine(getWebpackExecutablePath("webpack-dev-server"), "--hot", "--open",
                    "--config", webpackDevConfigFile.get().toString())
            } else {
                commandLine(getWebpackExecutablePath("webpack-dev-server"), "--open",
                    "--config", webpackDevConfigFile.get().toString())
            }
        }
        super.exec()
    }
}
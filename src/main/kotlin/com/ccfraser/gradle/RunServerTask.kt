package com.ccfraser.gradle

open class RunServerTask : BaseWebpackTask() {
    init {
        description = "Run webpack server with a production or development build (possibly configured for hot module " +
                "replacement) depending on settings.production"
    }

    override fun exec() {
        if (settings.production) {
            commandLine("${webpackBinDirectory.get()}/webpack-dev-server", "-p", "--config", webpackProdConfigFile.get().toString())
        } else {
            if (settings.webpackDevServerHotReload) {
                commandLine("${webpackBinDirectory.get()}/webpack-dev-server", "--hot", "--config", webpackDevConfigFile.get().toString())
            } else {
                commandLine("${webpackBinDirectory.get()}/webpack-dev-server", "--config", webpackDevConfigFile.get().toString())
            }
        }
        super.exec()
    }
}
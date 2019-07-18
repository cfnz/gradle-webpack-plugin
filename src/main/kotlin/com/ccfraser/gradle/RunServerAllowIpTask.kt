package com.ccfraser.gradle

open class RunServerAllowIpTask : BaseWebpackTask() {
    init {
        description = "Same as RunServerTask but allows other PCs to navigate to this PC and view the webapp " +
                "(i.e. browser does not have to be localhost, just use this PCs ip address and probably port 8080 " +
                "(e.g. something like 192.168.0.123:8080))"
    }

    override fun exec() {
        if (settings.production) {
            commandLine("${webpackBinDirectory.get()}/webpack-dev-server", "--open", "--host", "0.0.0.0",
                        "-p", "--config", webpackProdConfigFile.get().toString())
        } else {
            if (settings.webpackDevServerHotReload) {
                commandLine("${webpackBinDirectory.get()}/webpack-dev-server", "--hot", "--open", "--host", "0.0.0.0",
                        "--config", webpackDevConfigFile.get().toString())
            } else {
                commandLine("${webpackBinDirectory.get()}/webpack-dev-server", "--open", "--host", "0.0.0.0",
                        "--config", webpackDevConfigFile.get().toString())
            }
        }
        super.exec()
    }
}
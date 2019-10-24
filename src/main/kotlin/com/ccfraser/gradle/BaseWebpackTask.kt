package com.ccfraser.gradle

import org.gradle.api.tasks.*
import java.io.File

/**
 * This is the base class for the webpack tasks. It sets up various input and output dependencies using
 * the gradle lazy settings method.
 */
abstract class BaseWebpackTask : AbstractExecTask<BaseWebpackTask>(BaseWebpackTask::class.java) {
    @Internal
    val settings = project.extensions.findByName(EXTENSION_NAME) as GradleWebpackPluginSettings

    // Sort of input... guess if this directory changes, it might change the output...
    @InputDirectory
    val webpackBinDirectory = settings.webpackBinDirTemplate.map { File(settings.convert(it)) }

    // The input might be the Dev or the Prod config file depending on setting, but we shall tag both and avoid this complexity
    @InputFile
    val webpackDevConfigFile = settings.webpackDevConfigTemplate.map { File(settings.convert(it)) }

    // The input might be the Dev or the Prod config file depending on setting, but we shall tag both and avoid this complexity
    @InputFile
    val webpackProdConfigFile = settings.webpackProdConfigTemplate.map { File(settings.convert(it)) }

    // Change to this might mean a full rebuild
    @InputFile
    val nodePackageLockFile = settings.nodePackageLockFileTemplate.map { File(settings.convert(it)) }

    // This might be handled by a task dependency, but we will leave it here for now.
    @InputDirectory
    val jsBuildDirectory = settings.jsBuildDirTemplate.map { File(settings.convert(it)) }

    @OutputDirectory
    val jsBundleDirectory = settings.jsBundleDirTemplate.map { File(settings.convert(it)) }

    /**
     * Windows and Linux/Mac has different executables for webpack, so this just centralises getting the name
     * of the executable depending on platform. We are just making an assumption that if the path separator is '\' then
     * we are on a windows OS, otherwise a Unix based OS (based on https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html).
     */
    fun getWebpackExecutablePath(executableName: String) =
            "${webpackBinDirectory.get()}${File.separator}$executableName" + if (File.separatorChar == '\\') ".cmd" else ""

    init {
        group = "webpack"
    }
}



package com.ccfraser.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

open class CopyWebResourcesTask : DefaultTask() {
    @Internal
    val settings = project.extensions.findByName(EXTENSION_NAME) as GradleWebpackPluginSettings

    init {
        group = "webpack"
        description = "Copy web resources source to distribution"
    }

    @InputDirectory
    val srcWebPubicDirectory = settings.srcWebPublicDirTemplate.map { File(settings.convert(it))}

    @OutputDirectory
    val distributionDirectory = settings.distributionDirTemplate.map { File(settings.convert(it)) }

    @TaskAction
    fun copyFiles() {
        project.copy {
            from(srcWebPubicDirectory.get())
            into(distributionDirectory.get())
        }
    }
}
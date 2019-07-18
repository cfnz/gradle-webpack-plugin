package com.ccfraser.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.property
import java.io.Serializable

internal const val EXTENSION_NAME = "GradleWebpackPluginSettings"

open class GradleWebpackPluginSettings(var project: Project) : Serializable {
    var production = false

    /*
     * Because these properties are used for inputs and/or outputs, and may be configured late, we need to use the
     * gradle lazy configuration features (which at time of writing is marked as unstable).
     *
     * The directory templates can include %projectDir%, %buildDir% and %rootDir% which will be replaced with the gradle
     * project properties of the same name
     */
    val webpackBinDirTemplate = project.objects.property<String>()
    val webpackDevConfigTemplate = project.objects.property<String>()
    val webpackProdConfigTemplate = project.objects.property<String>()
    val jsBuildDirTemplate = project.objects.property<String>()
    val jsBundleDirTemplate = project.objects.property<String>()
    val srcWebPublicDirTemplate = project.objects.property<String>()
    val nodePackageLockFileTemplate = project.objects.property<String>()
    //    var otherInputDirs project.objects.property<String>()
    val distributionDirTemplate = project.objects.property<String>()
    val statsDirTemplate = project.objects.property<String>()

    var webpackDevServerHotReload = true

    internal fun convert(value: String): String {
        return value.replace("%projectDir%", project.projectDir.toString(), false)
                .replace("%buildDir%", project.buildDir.toString(), false)
                .replace("%rootDir%", project.rootDir.toString(), false)
    }

    internal fun addKotlinJsDceTaskDependencies(dceTask: Task) {
        project.tasks["buildBundle"].dependsOn(dceTask)
        project.tasks["runServer"].dependsOn(dceTask)
        project.tasks["runServerAllowIp"].dependsOn(dceTask)
        project.tasks["runServerOpenBrowser"].dependsOn(dceTask)
        project.tasks["statsCreate"].dependsOn(dceTask)
    }

    init {
        webpackBinDirTemplate.set("%projectDir%/node_modules/.bin")
        webpackDevConfigTemplate.set("%projectDir%/webpack.config.js")
        webpackProdConfigTemplate.set("%projectDir%/webpack.config.prod.js")
        jsBuildDirTemplate.set("%buildDir%/js")
        jsBundleDirTemplate.set("%buildDir%/js-for-bundle")
        srcWebPublicDirTemplate.set("%projectDir%/src/main/resources/public")
        nodePackageLockFileTemplate.set("%projectDir%/yarn.lock")
        distributionDirTemplate.set("%buildDir%/dist")
        statsDirTemplate.set("%buildDir%/reports")
    }

    fun configureForMultiPlatformProject(dceTask: Task) {
        webpackBinDirTemplate.set("%projectDir%/src/jsMain/node_modules/.bin")
        webpackDevConfigTemplate.set("%projectDir%/src/jsMain/webpack.config.js")
        webpackProdConfigTemplate.set("%projectDir%/src/jsMain/webpack.config.prod.js")
        srcWebPublicDirTemplate.set("%projectDir%/src/jsMain/resources/public")
        nodePackageLockFileTemplate.set("%projectDir%/src/jsMain/yarn.lock")

        addKotlinJsDceTaskDependencies(dceTask)
    }
}

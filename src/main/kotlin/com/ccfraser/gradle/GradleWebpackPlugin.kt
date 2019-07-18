package com.ccfraser.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get



class GradleWebpackPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val settings = project.extensions.create(EXTENSION_NAME, GradleWebpackPluginSettings::class.java, project)

        // We need to apply these plugins so we can reference in the dependencies and also in some of the tasks.
        project.pluginManager.apply("kotlin-dce-js")

//        // Try kotlin2js first, and if it fails... try for multiplatform
//        try {
//            project.pluginManager.apply("kotlin2js")
//            println("Applied kotlin2js")
//        } catch (e: Exception) {
//            project.pluginManager.apply("kotlin-multiplatform")
//            println("Applied kotlin-multiplatform")
//        }

        // Couldn't figure out if we are a multiplatform build or not, so we are defaulting to not, and
        // if it is a multi-platform build, the user will need to call settings.configureForMultiPlatformProject

        project.tasks.create("buildBundle", BuildBundleTask::class.java)
        project.tasks.create("runServer", RunServerTask::class.java)
        project.tasks.create("runServerOpenBrowser", RunServerOpenBrowserTask::class.java)
        project.tasks.create("runServerAllowIp", RunServerAllowIpTask::class.java)

        val copyWebResources = project.tasks.create("copyWebResources", CopyWebResourcesTask::class.java)
        val statsCreateTask = project.tasks.create("statsCreate", StatsCreateTask::class.java)
        val statsDisplayTask = project.tasks.create("statsDisplay", StatsDisplayTask::class.java)
        statsDisplayTask.dependsOn(statsCreateTask)

        // runDceKotlinJs may or may not actually do Dead Code Elimination, but it does copy the Js modules to
        // kotlin-js-min regardless which is what we want.
        // Unfortunately with a MultiPlatform build, I have not figured out how to find the Dce dependency at this
        // stage in the process as it has a different name and seems to get loaded at a different time... it is not available here anyway.
        val kotlinJsDce = try {
            project.tasks["runDceKotlinJs"]
        } catch (e: Exception) {
            //project.tasks["runDceJsKotlin"] This seems to be the task name, but it is not available here...
            null
        }

        project.tasks["buildBundle"].dependsOn(copyWebResources)
        project.tasks["runServer"].dependsOn(copyWebResources)
        project.tasks["runServerAllowIp"].dependsOn(copyWebResources)
        project.tasks["runServerOpenBrowser"].dependsOn(copyWebResources)
        project.tasks["statsCreate"].dependsOn(copyWebResources)
        project.tasks["assemble"].dependsOn(copyWebResources)

        if (kotlinJsDce != null) {
            settings.addKotlinJsDceTaskDependencies(kotlinJsDce)
        } else {
            // We will rely on the "Consuming" project, which is probably a multi-platform project, to make a call to
            // settings.configureForMultiPlatformProject with a runDceTask.
        }
    }
}

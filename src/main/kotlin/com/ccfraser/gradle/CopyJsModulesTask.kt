package com.ccfraser.gradle

/**
 * Turns out we don't need this class now... kotlin DCE, when Dev mode is set to true, does more or less the same
 * thing... still puts it in js-min (we have changed this with a setting now!) which is a little confusing...
 * but makes this redundant. (In the past we only ran DCE in Production mode, but found the above behaviour in Dev
 * mode which is useful.)
 */

//open class CopyJsModulesTask : DefaultTask() {
//    @Internal
//    val settings = project.extensions.findByName(EXTENSION_NAME) as GradleWebpackPluginSettings
//
//    init {
//        group = "webpack"
//        description = "Copy js resources from within dependencies to bundle folder"
//    }
//
//    @OutputDirectory
//    val jsBundleDirDirectory = settings.jsBundleDirTemplate.map { File(settings.convert(it))}
//
//    @TaskAction
//    fun copyFiles() {
//        // For some reason we can't get to the Kotlin2JsCompile class, but we can get to one of its base classes, and
//        // that is all we need.
//        val compileKotlin2Js = project.tasks["compileKotlin2Js"] as AbstractCompile
//        project.copy {
//            compileKotlin2Js.classpath.forEach {
//                if (it.exists() && !it.isDirectory) {
//                    from(project.zipTree(it.absolutePath).matching {include("*.js")} )
//                }
//            }
//            into(jsBundleDirDirectory.get())
//        }
//    }
//}
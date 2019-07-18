# gradle-webpack-plugin
A gradle plugin to help with builds related to webpack to try and reduce boilerplate code in build files.

# Background
I found I was repeating lots of webpack build logic in different applications, and when I would update the way 
of doing things in one project, I ended up trying to update it in the others too... I thought I needed a better way.

Note the [kotlin-frontend-plugin](https://github.com/Kotlin/kotlin-frontend-plugin) is an alternative way of 
handling this, however this version gives you a bit more control of webpack and the npm package file.

Also note, I have not looked into the new experimental support for NPM and Webpack in kotlin 1.3.40... that might 
change this way of doing things quite a bit.

Finally, mainly I have shared this as I use it in the latest version of [Muirwik](https://github.com/cfnz/muirwik)'s 
test app, so without sharing it, the app would not be able to built :-)

# Compiler Warnings
Upgrading to Gradle 5.5.1 shows compiler warnings for arguments used by gradle in the build.

This is expected behaviour and is explained in the [gradle docs](https://docs.gradle.org/current/userguide/kotlin_dsl.html).

This code is only used to help with building an app, so we don't need to worry.
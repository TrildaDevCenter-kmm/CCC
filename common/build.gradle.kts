/*
 * Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    with(Plugins) {
        kotlin(multiplatform)
        id(kotlinXSerialization)
        id(androidLibrary)
        id(sqldelight)
    }
}

kotlin {

    jvm()

    android()

    ios {
        binaries {
            framework {
                baseName = "common"
            }
        }
    }

    js {
        browser {
            binaries.executable()
            testTask {
                enabled = false
            }
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {

        with(Dependencies.Common) {
            val commonMain by getting {
                dependencies {
                    implementation(project(Modules.logmob))

                    implementation(koinCore)
                    implementation(kermit)

                    implementation(ktorLogging)
                    implementation(ktorSerialization)

                    implementation(sqldelightRuntime)
                    implementation(sqldelightCoroutineExtensions)
                }
            }
        }

        with(Dependencies.Android) {
            val androidMain by getting {
                dependencies {
                    implementation(sqlliteDriver)
                    implementation(ktor)
                }
            }
        }

        with(Dependencies.IOS) {
            val iosMain by getting {
                dependencies {
                    implementation(ktor)
                    implementation(sqlliteDriver)
                }
            }
        }

        with(Dependencies.JVM) {
            val jvmMain by getting {
                dependencies {
                    implementation(ktor)
                    implementation(sqlliteDriver)
                }
            }
        }

        with(Dependencies.JS) {
            val jsMain by getting {
                dependencies {
                    implementation(ktor)
                }
            }
        }
    }
}

android {
    with(ProjectSettings) {
        compileSdkVersion(projectCompileSdkVersion)

        defaultConfig {
            minSdkVersion(projectMinSdkVersion)
            targetSdkVersion(projectTargetSdkVersion)
            versionCode = getVersionCode(project)
            versionName = getVersionName(project)
        }

        sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)

sqldelight {
    database(Database.name) {
        packageName = Database.packageName
        sourceFolders = listOf(Database.sourceFolders)
    }
}

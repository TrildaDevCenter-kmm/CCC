import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.INT
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension

plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    libs.plugins.apply {
        id(androidLib.get().pluginId)
        id(multiplatform.get().pluginId)
        id(buildKonfig.get().pluginId)
        alias(ksp)
    }
}

kotlin {
    android()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    @Suppress("UNUSED_VARIABLE")
    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(libs.common.koinCore)

                Modules.Client.ConfigService.apply {
                    implementation(project(update))
                    implementation(project(review))
                }
                implementation(project(Modules.Client.Storage.app))
                implementation(project(Modules.Client.Core.shared))
                implementation(Submodules.scopemob)
            }
        }
        val commonTest by getting {
            dependencies {
                libs.common.apply {
                    implementation(test)
                    implementation(mockative)
                }
            }
        }

        val androidMain by getting
        val androidUnitTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

dependencies {
    configurations
        .filter { it.name.startsWith("ksp") && it.name.contains("Test") }
        .forEach {
            add(it.name, libs.processors.mockative)
        }
}

android {
    ProjectSettings.apply {
        namespace = Modules.Client.Repository.appConfig.packageName
        compileSdk = COMPILE_SDK_VERSION
        defaultConfig.minSdk = MIN_SDK_VERSION

        compileOptions {
            sourceCompatibility = JAVA_VERSION
            targetCompatibility = JAVA_VERSION
        }
    }
}

configure<BuildKonfigExtension> {
    packageName = Modules.Client.Repository.appConfig.packageName

    defaultConfigs {
        buildConfigField(INT, "versionCode", ProjectSettings.getVersionCode(project).toString(), const = true)
        buildConfigField(STRING, "versionName", ProjectSettings.getVersionName(project), const = true)
    }
}

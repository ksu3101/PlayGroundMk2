package com.swkang.playground2.buildsrc

object Version {
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val minSdk = 21
    const val targetSdk = 32
    const val compileSdk = 32

    const val ktlint = "0.45.2"
}

object Libs {
    const val gradle = "com.android.tools.build:gradle:7.2.0"

    object Kotlin {
        private const val ver = "1.6.21"
        const val stdLibs = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$ver"
        const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$ver"
        const val androidExts = "org.jetbrains.kotlin:kotlin-android-extensions:$ver"
    }

    object Coroutines {
        private const val ver = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$ver"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$ver"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$ver"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"

        object Lifecycle {
            private const val ver = "2.4.1"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$ver"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$ver"
        }

        object Compose {
            const val ver = "1.2.0-beta03"
            const val layout = "androidx.compose.foundation:foundation-layout:$ver"
            const val foundation = "androidx.compose.foundation:foundation:$ver"
            const val runtime = "androidx.compose.runtime:runtime:$ver"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:$ver"
            const val material = "androidx.compose.material:material:$ver"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$ver"
            const val tooling = "androidx.compose.ui:ui-tooling:$ver"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:$ver"
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
            const val test = "androidx.compose.test:test-core:$ver"
        }

        object Material {
            private const val ver = "1.6.0-alpha03"
            const val material = "com.google.android.material:material:$ver"
        }

        object Test {
            private const val ver = "1.4.0"
            const val core = "androidx.test:core-ktx:$ver"
            const val rules = "androidx.test:rules:$ver"

            object Ext {
                const val androidXjunit = "androidx.test.ext:junit:1.1.3"
                const val truth = "androidx.test.ext:truth:1.4.0"
            }
        }
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:2.0.0"
    }

    object UnitTesting {
        const val junit = "junit:junit:4.13.2"
    }
}

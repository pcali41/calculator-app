object Libs {

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:4.2.0-alpha16"
    }

    object Test {
        const val junit = "junit:junit:4.13"
    }

    object Material {
        const val core = "com.google.android.material:material:1.2.1"
    }

    object Kotlin {
        const val version = "1.4.10"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.3.9"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Hilt {
        private const val version = "2.30.1-alpha"
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val core = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    }

    object Debug {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"

        object Compose {
            const val version = "1.0.0-alpha07"
            const val compiler = "androidx.compose.compiler:compiler:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsCore = "androidx.compose.material:material-icons-core:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:$version"
            const val tooling = "androidx.ui:ui-tooling:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiTest = "androidx.ui:ui-test:$version"
        }

        object Hilt {
            private const val version = "1.0.0-alpha02"
            const val compiler = "androidx.hilt:hilt-compiler:$version"
            const val lifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:$version"
        }

        object Layout {
            const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.1"
            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"

            private const val nav_compose_version = "1.0.0-alpha01"
            const val navCompose = "androidx.navigation:navigation-compose:$nav_compose_version"

            object SafeArgs {
                const val gradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            }
        }

        object RecyclerView {
            private const val version = "1.1.0"
            const val core = "androidx.recyclerview:recyclerview:1.1.0"
        }
        object Room {
            private const val version = "2.2.5"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2-rc01"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        }
    }
}
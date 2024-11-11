// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.7.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.8.3")
        classpath("org.jetbrains.kotlin:kotlin-serialization:2.0.21")
    }
}

plugins {
    id("com.google.dagger.hilt.android") version "2.52" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21"
}

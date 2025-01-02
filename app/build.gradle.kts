plugins {
    id("codecheck.android.application")
    id("codecheck.android.application.compose")
    id("codecheck.detekt")
    id("codecheck.android.hilt")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "jp.co.yumemi.android.codecheck"

    defaultConfig {
        applicationId = "jp.co.yumemi.android.codecheck"
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // デバッグ用の署名を使用してリリースビルドしたい場合、以下をコメントアウトする
        // signingConfig = signingConfigs["debug"]
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
    lint {
        lintConfig = file("lint.xml")
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:search"))

    implementation(libs.compose.material3)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.browser)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.slf4j.android)

    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.compose.ui.test.junit4)
}

tasks.withType<AbstractTestTask> {
    // Disable unit tests for release build type (Robolectric limitations)
    if (name == "testReleaseUnitTest") {
        enabled = false
    }
}

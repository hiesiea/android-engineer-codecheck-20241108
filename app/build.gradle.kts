plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.navigation.safeargs.kotlin)
    alias(libs.plugins.hilt)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
}

android {
    namespace = "jp.co.yumemi.android.codecheck"
    compileSdk = 35

    defaultConfig {
        applicationId = "jp.co.yumemi.android.codecheck"
        minSdk = 23
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.browser)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.slf4j.android)
    implementation(libs.coil.compose)
    implementation(libs.timber)
    implementation(libs.hilt.android)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)

    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.compose.ui.test.junit4)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

tasks.withType<AbstractTestTask> {
    // Disable unit tests for release build type (Robolectric limitations)
    if (name == "testReleaseUnitTest") {
        enabled = false
    }
}

plugins {
    id("codecheck.android.application")
    id("codecheck.android.application.compose")
    id("codecheck.detekt")
    id("codecheck.android.hilt")
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
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:search"))

    implementation(libs.compose.material3)
    implementation(libs.browser)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.slf4j.android)
}

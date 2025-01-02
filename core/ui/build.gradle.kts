plugins {
    id("codecheck.android.library")
    id("codecheck.android.library.compose")
    id("codecheck.detekt")
}

android {
    namespace = "jp.co.yumemi.android.codecheck.ui"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.coil.compose)
}

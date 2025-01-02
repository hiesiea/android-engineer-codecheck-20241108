plugins {
    id("codecheck.android.library")
    id("codecheck.detekt")
    id("codecheck.android.hilt")
}

android {
    namespace = "jp.co.yumemi.android.codecheck.data"

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
    testImplementation(libs.junit)
}

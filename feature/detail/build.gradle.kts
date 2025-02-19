plugins {
    id("codecheck.android.library")
    id("codecheck.android.library.compose")
    id("codecheck.detekt")
    id("codecheck.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "jp.co.yumemi.android.codecheck.feature.detail"

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
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))

    implementation(libs.compose.material3)
    implementation(libs.kotlinx.serialization.json)

    debugImplementation(libs.compose.ui.test.manifest)

    testImplementation(libs.robolectric)
    testImplementation(libs.compose.ui.test.junit4)
}

tasks.withType<AbstractTestTask> {
    // Disable unit tests for release build type (Robolectric limitations)
    if (name == "testReleaseUnitTest") {
        enabled = false
    }
}

import com.android.build.gradle.LibraryExtension
import jp.co.yumemi.android.codecheck.configureKotlinAndroid
import jp.co.yumemi.android.codecheck.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            dependencies {
                "testImplementation"(libs.findLibrary("kotlin.test").get())
            }
        }
    }
}

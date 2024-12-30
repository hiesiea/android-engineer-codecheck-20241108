import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("io.gitlab.arturbosch.detekt")
            setupDetekt(extensions.getByType<DetektExtension>())
        }
    }
}

private fun Project.setupDetekt(extension: DetektExtension) {
    extension.apply {
        parallel = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
    }
}

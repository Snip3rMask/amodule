rootProject.name = "anifux-module"

// Auto-include all provider subdirectories
File(rootDir, "providers").eachDir { dir ->
    if (File(dir, "build.gradle.kts").exists()) {
        include(":providers:${dir.name}")
    }
}

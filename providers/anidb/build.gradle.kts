plugins {
    java
    id("com.anifux.msr-plugin") version "1.0" apply false
}

version = 1

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Plugin API (AnifuxProvider interface and models)
    implementation(files("../../plugin-api/src"))
    
    // Required for HTML scraping
    implementation("org.jsoup:jsoup:1.17.2")
}

tasks.register<Jar>("buildMsr") {
    archiveBaseName.set("anidb")
    archiveExtension.set("msr")
    
    from(sourceSets.main.get().output)
    from("manifest.json")
    
    manifest {
        attributes(
            "Plugin-Name" to "Anidb",
            "Plugin-Version" to project.version.toString(),
            "Plugin-Class" to "com.anifux.provider.anidb.AnidbProvider"
        )
    }
    
    doLast {
        println("Created: ${archiveFile.get().asFile.absolutePath}")
    }
}

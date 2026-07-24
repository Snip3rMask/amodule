plugins {
    java
}

version = 1

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Plugin API stubs (same packages as app — needed for compilation)
    implementation(files("../../plugin-api/src"))
    
    // Jsoup for HTML scraping — NOT packaged in .msr, app provides it
    compileOnly("org.jsoup:jsoup:1.17.2")
}

tasks.register<Jar>("buildMsr") {
    archiveBaseName.set("anidb")
    archiveExtension.set("msr")
    
    from(sourceSets.main.get().output)
    from("manifest.json")
    
    doLast {
        println("Created: ${archiveFile.get().asFile.absolutePath}")
    }
}

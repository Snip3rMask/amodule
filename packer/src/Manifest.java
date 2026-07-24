package com.anifux.packer;

/**
 * Structure of manifest.json inside a .msr file.
 * This defines how the app reads plugin metadata.
 */
public class Manifest {
    public String name;         // Provider name (e.g. "Anidb")
    public int version;         // Version number for update checks
    public String mainClass;    // Full class path (e.g. "com.anifux.provider.anidb.AnidbProvider")
    public String language;     // Language code (e.g. "en")
    public String description;  // Short description
    public String icon;         // Optional icon URL

    public Manifest() {}

    public Manifest(String name, int version, String mainClass, String language) {
        this.name = name;
        this.version = version;
        this.mainClass = mainClass;
        this.language = language;
    }
}

# AStream Modules

.msr plugin modules for AStream app. Each module is an independent scraping provider packaged as a .msr file.

## Structure

```
plugin-api/src/       → Provider interface + data models
provider-{name}/      → Individual provider implementations
  ├── manifest.json    → Module metadata
  ├── build.gradle     → Build configuration
  └── src/             → Provider source code
packer/src/            → .msr packaging CLI tool
repo/repo.json         → Module repository manifest
```

## Module Format (.msr)

.msr files are zip archives containing:
- `manifest.json` — name, version, mainClass
- `classes.dex` — compiled provider bytecode

Loaded at runtime via PathClassLoader.

## Building a Module

```bash
cd provider-anidb
./gradlew buildMsr
```

Output: `build/provider-anidb.msr`

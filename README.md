# Anifux Modules — .msr Plugin Providers

This repository contains the official .msr plugin providers for the Anifux app.

## Structure

```
anifux-module/
├── plugin-api/src/        → AnifuxProvider interface + data models
├── providers/             → Individual providers
│   ├── anidb/             → Anidb.app provider
│   ├── anineko/           → Anineko.to provider
│   └── ...                → More providers
├── packer/src/            → .msr packaging CLI tool
└── repo/                  → Repository metadata
    └── repo.json          → Plugin list for app
```

## Building a Provider

Each provider must:
1. Implement `com.anifux.plugin.AnifuxProvider`
2. Include `manifest.json` with metadata
3. Include `build.gradle.kts` for building

## .msr Format

`.msr` files are zip archives containing:
- `manifest.json` — Plugin metadata (name, version, mainClass)
- `classes.dex` — Compiled Dalvik bytecode

## Adding a New Provider

1. Create folder under `providers/`
2. Copy `anidb/` structure as reference
3. Write your AnifuxProvider implementation
4. Run the packer to build .msr
5. Add entry to `repo/repo.json`

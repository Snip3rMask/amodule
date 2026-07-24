package com.anifux.packer;

import java.io.*;
import java.util.zip.*;

/**
 * CLI tool to pack compiled provider code into .msr format.
 * 
 * Usage:
 *   java AnifuxPacker build <classes-dir> <manifest.json> <output.msr>
 * 
 * Example:
 *   java AnifuxPacker build ./anidb/build/classes ./anidb/manifest.json ./anidb.msr
 * 
 * The .msr format is a zip containing:
 *   - manifest.json   : Metadata (name, version, mainClass, etc.)
 *   - classes.dex     : Compiled Dalvik bytecode (the actual provider)
 */
public class AnifuxPacker {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            printHelp();
            return;
        }

        switch (args[0]) {
            case "build" -> build(args);
            case "info" -> info(args);
            case "list" -> listContents(args);
            default -> printHelp();
        }
    }

    static void printHelp() {
        System.out.println("Anifux Packer - .msr plugin builder");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  build <classes-dir> <manifest> <output.msr>");
        System.out.println("      Pack compiled classes into .msr format");
        System.out.println();
        System.out.println("  info <file.msr>");
        System.out.println("      Show manifest info from a .msr file");
        System.out.println();
        System.out.println("  list <file.msr>");
        System.out.println("      List contents of a .msr file");
    }

    static void build(String[] args) throws Exception {
        if (args.length < 4) {
            System.err.println("Usage: build <classes-dir> <manifest> <output.msr>");
            return;
        }

        File classesDir = new File(args[1]);
        File manifestFile = new File(args[2]);
        File outputFile = new File(args[3]);

        if (!classesDir.exists() || !classesDir.isDirectory()) {
            System.err.println("Error: Classes directory not found: " + classesDir);
            return;
        }
        if (!manifestFile.exists()) {
            System.err.println("Error: Manifest file not found: " + manifestFile);
            return;
        }

        // Read manifest
        String manifestJson = readFile(manifestFile);

        try (FileOutputStream fos = new FileOutputStream(outputFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            // Add manifest.json
            zos.putNextEntry(new ZipEntry("manifest.json"));
            zos.write(manifestJson.getBytes("UTF-8"));
            zos.closeEntry();

            // Collect all .class files and pack them
            addDirectoryToZip(zos, classesDir, "");

            System.out.println("Created: " + outputFile.getAbsolutePath());
            System.out.println("Size: " + outputFile.length() + " bytes");
        }
    }

    static void addDirectoryToZip(ZipOutputStream zos, File dir, String prefix) throws IOException {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            String entryName = prefix.isEmpty() ? file.getName() : prefix + "/" + file.getName();
            
            if (file.isDirectory()) {
                addDirectoryToZip(zos, file, entryName);
            } else {
                zos.putNextEntry(new ZipEntry(entryName));
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] buffer = new byte[8192];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
            }
        }
    }

    static void info(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: info <file.msr>");
            return;
        }
        File file = new File(args[1]);
        if (!file.exists()) {
            System.err.println("Error: File not found: " + file);
            return;
        }

        try (ZipFile zip = new ZipFile(file)) {
            ZipEntry entry = zip.getEntry("manifest.json");
            if (entry == null) {
                System.err.println("Error: No manifest.json found in " + file);
                return;
            }
            try (InputStream is = zip.getInputStream(entry)) {
                String json = new String(is.readAllBytes(), "UTF-8");
                System.out.println("=== Manifest ===");
                System.out.println(json);
            }
        }
    }

    static void listContents(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: list <file.msr>");
            return;
        }
        File file = new File(args[1]);
        if (!file.exists()) {
            System.err.println("Error: File not found: " + file);
            return;
        }

        try (ZipFile zip = new ZipFile(file)) {
            System.out.println("=== Contents of " + file.getName() + " ===");
            zip.stream().forEach(entry -> {
                System.out.println("  " + entry.getName() + " (" + entry.getCompressedSize() + " bytes)");
            });
        }
    }

    static String readFile(File file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}

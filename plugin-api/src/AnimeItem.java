package com.anifux.plugin;

/** Minimal anime item for search results — used in .msr plugins */
public class AnimeItem {
    public String title;
    public String url;
    public String thumbnail;
    public String quality;
    public String year;
    public String rating;

    public AnimeItem() {}

    public AnimeItem(String title, String url, String thumbnail) {
        this.title = title;
        this.url = url;
        this.thumbnail = thumbnail;
    }
}

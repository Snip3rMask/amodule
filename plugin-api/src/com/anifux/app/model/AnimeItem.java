package com.anifux.app.model;

/** Stub matching app's AnimeItem — only fields used by modules */
public class AnimeItem {
    public String title;
    public String url;
    public String thumbnail;
    public String playbackId;
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

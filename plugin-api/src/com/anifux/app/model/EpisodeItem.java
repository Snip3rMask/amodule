package com.anifux.app.model;

/** Stub matching app's EpisodeItem — only fields used by modules */
public class EpisodeItem {
    public String title;
    public String url;
    public String thumbnail;
    public int episodeNumber;
    public String airDate;

    public EpisodeItem() {}
    public EpisodeItem(String title, String url, String thumbnail, int episodeNumber) {
        this.title = title;
        this.url = url;
        this.thumbnail = thumbnail;
        this.episodeNumber = episodeNumber;
    }
}

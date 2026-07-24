package com.anifux.plugin;

/** An episode in a series */
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

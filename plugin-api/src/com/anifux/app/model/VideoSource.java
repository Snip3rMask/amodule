package com.anifux.app.model;

/** Stub matching app's VideoSource — only fields used by modules */
public class VideoSource {
    public String quality;
    public String url;
    public String language;
    public String server;
    public String subtitleUrl;
    public String referer;

    public VideoSource() {}
    public VideoSource(String quality, String url, String language, String server) {
        this.quality = quality;
        this.url = url;
        this.language = language;
        this.server = server;
    }
}

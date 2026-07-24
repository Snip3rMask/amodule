package com.anifux.plugin;

/** A video stream source returned by a provider */
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

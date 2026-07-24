package com.anifux.provider.anidb;

import com.anifux.app.model.AnimeItem;
import com.anifux.app.model.EpisodeItem;
import com.anifux.app.model.VideoSource;
import com.anifux.app.plugin.AnifuxProvider;
import com.anifux.app.plugin.AnimeFullDetail;
import com.anifux.app.plugin.HomeRow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Anidb.app provider — scrapes anime info, episodes and video sources.
 * Reference implementation of AnifuxProvider for .msr module system.
 */
public class AnidbProvider implements AnifuxProvider {

    private static final String BASE_URL = "https://anidb.app";
    private static final String UA = "Mozilla/5.0 (Android 14; Mobile) AppleWebKit/537.36";

    @Override
    public String getName() { return "Anidb"; }

    @Override
    public String getMainUrl() { return BASE_URL; }

    @Override
    public String getLanguage() { return "en"; }

    // ==================== SEARCH ====================

    @Override
    public List<AnimeItem> search(String query, int page) {
        List<AnimeItem> results = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(BASE_URL + "/browse?q="
                    + java.net.URLEncoder.encode(query, "UTF-8") + "&page=" + page)
                    .userAgent(UA).get();

            for (Element link : doc.select("a[href*=/anime/]")) {
                String title = link.attr("title");
                if (title.isEmpty()) title = link.select("img").attr("alt");
                if (title.isEmpty()) continue;

                String href = link.attr("href");
                String thumb = link.select("img").attr("src");
                if (!thumb.startsWith("http")) thumb = "https:" + thumb;

                AnimeItem item = new AnimeItem();
                item.title = title.trim();
                item.url = href.startsWith("http") ? href : BASE_URL + href;
                item.thumbnail = thumb;
                results.add(item);
                if (results.size() >= 20) break;
            }
        } catch (Exception ignored) {}
        return results;
    }

    // ==================== HOME PAGE ====================

    @Override
    public boolean hasMainPage() { return true; }

    @Override
    public List<HomeRow> getMainPage() {
        List<HomeRow> rows = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(BASE_URL + "/browse").userAgent(UA).get();
            List<AnimeItem> latest = new ArrayList<>();

            for (Element link : doc.select("a[href*=/anime/]")) {
                String title = link.attr("title");
                if (title.isEmpty()) continue;
                String href = link.attr("href");
                String thumb = link.select("img").attr("src");
                if (!thumb.startsWith("http")) thumb = "https:" + thumb;

                AnimeItem item = new AnimeItem();
                item.title = title.trim();
                item.url = href.startsWith("http") ? href : BASE_URL + href;
                item.thumbnail = thumb;
                latest.add(item);
                if (latest.size() >= 20) break;
            }
            if (!latest.isEmpty()) rows.add(new HomeRow("Latest Anime", latest));
        } catch (Exception ignored) {}
        return rows;
    }

    // ==================== DETAIL ====================

    @Override
    public AnimeFullDetail loadDetail(String url) {
        AnimeFullDetail detail = new AnimeFullDetail();
        try {
            Document doc = Jsoup.connect(url).userAgent(UA).get();
            detail.title = doc.select("meta[property=og:title]").attr("content");
            detail.thumbnail = doc.select("meta[property=og:image]").attr("content");
            detail.description = doc.select("meta[property=og:description]").attr("content");
            detail.url = url;

            detail.episodes = new ArrayList<>();
            int epNum = 1;
            for (Element epLink : doc.select("a[href*=/episode/], a[href*=/watch/]")) {
                String epTitle = epLink.text().trim();
                String epUrl = epLink.attr("href");
                if (epUrl.startsWith("/")) epUrl = BASE_URL + epUrl;

                EpisodeItem ep = new EpisodeItem();
                ep.title = epTitle.isEmpty() ? "Episode " + epNum : epTitle;
                ep.url = epUrl;
                ep.episodeNumber = epNum++;
                detail.episodes.add(ep);
            }
        } catch (Exception ignored) {}
        return detail;
    }

    // ==================== SOURCES ====================

    @Override
    public List<VideoSource> getSources(String episodeUrl, String animeTitle, int episodeNum) {
        List<VideoSource> sources = new ArrayList<>();
        try {
            String url = episodeUrl.startsWith("http") ? episodeUrl : BASE_URL + episodeUrl;
            Document doc = Jsoup.connect(url).userAgent(UA).referrer(BASE_URL + "/").get();

            // HTML5 video sources
            for (Element source : doc.select("video source, source")) {
                String src = source.attr("src");
                if (src.isEmpty()) continue;
                if (!src.startsWith("http")) {
                    if (src.startsWith("//")) src = "https:" + src;
                    else src = BASE_URL + src;
                }
                VideoSource vs = new VideoSource();
                vs.url = src;
                vs.quality = source.attr("label") != null ? source.attr("label") : "HD";
                vs.language = "Sub";
                vs.server = "Daki";
                vs.referer = BASE_URL + "/";
                sources.add(vs);
            }

            // Iframes with embedded players
            for (Element iframe : doc.select("iframe[src]")) {
                String src = iframe.attr("src");
                if (src.isEmpty()) continue;
                if (!src.startsWith("http")) src = BASE_URL + src;

                try {
                    Document iframeDoc = Jsoup.connect(src).userAgent(UA).referrer(url).get();
                    java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                            "(https?://[^\"'\\s]*?\\.m3u8[^\"'\\s]*)"
                    ).matcher(iframeDoc.html());

                    while (m.find()) {
                        VideoSource vs = new VideoSource();
                        vs.url = m.group(1);
                        vs.quality = "HD";
                        vs.language = "Sub";
                        vs.server = "Daki";
                        vs.referer = src;
                        sources.add(vs);
                    }
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}
        return sources;
    }
}

package com.anifux.plugin;

import java.util.List;

/**
 * Main interface that all .msr plugin providers must implement.
 * This is the contract between Anifux app and external scraping modules.
 */
public interface AnifuxProvider {

    /** Name shown in the app UI */
    String getName();

    /** Main website URL this provider scrapes */
    String getMainUrl();

    /** Supported language code (e.g. "en", "ja") */
    String getLanguage();

    // ==================== SEARCH ====================

    /** Search for anime by query (page start from 1) */
    List<AnimeItem> search(String query, int page);

    // ==================== HOME PAGE ====================

    /** Does this provider have a homepage with rows? */
    boolean hasMainPage();

    /** Get homepage rows (trending, popular, etc.) */
    List<HomeRow> getMainPage();

    // ==================== DETAIL ====================

    /** Load full anime details + episodes from a detail URL */
    AnimeDetail loadDetail(String url);

    // ==================== SOURCES ====================

    /**
     * Extract video sources for an episode.
     * @param episodeUrl The episode page URL/path
     * @param animeTitle The anime title for context
     * @param episodeNum Episode number
     * @return List of video sources (qualities/servers)
     */
    List<VideoSource> getSources(String episodeUrl, String animeTitle, int episodeNum);
}

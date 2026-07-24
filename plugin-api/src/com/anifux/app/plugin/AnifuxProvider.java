package com.anifux.app.plugin;

import com.anifux.app.model.AnimeItem;
import com.anifux.app.model.VideoSource;

import java.util.List;

/**
 * Interface that all .msr module providers must implement.
 * Package MUST match the app's interface exactly — PathClassLoader uses parent first.
 */
public interface AnifuxProvider {
    String getName();
    String getMainUrl();
    String getLanguage();
    List<AnimeItem> search(String query, int page);
    boolean hasMainPage();
    List<HomeRow> getMainPage();
    AnimeFullDetail loadDetail(String url);
    List<VideoSource> getSources(String episodeUrl, String animeTitle, int episodeNum);
}

package com.anifux.app.plugin;

import com.anifux.app.model.EpisodeItem;
import java.util.List;

/** Full anime detail returned by loadDetail() */
public class AnimeFullDetail {
    public String title;
    public String url;
    public String thumbnail;
    public String banner;
    public String description;
    public String genres;
    public String year;
    public String rating;
    public String status;
    public List<EpisodeItem> episodes;
}

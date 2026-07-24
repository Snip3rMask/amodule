package com.anifux.plugin;

import java.util.List;

/** Full anime detail including episode list — returned by loadDetail() */
public class AnimeDetail {
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

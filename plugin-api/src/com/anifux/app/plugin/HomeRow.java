package com.anifux.app.plugin;

import com.anifux.app.model.AnimeItem;
import java.util.List;

/** A horizontal row on the home screen */
public class HomeRow {
    public String title;
    public List<AnimeItem> items;
    public HomeRow() {}
    public HomeRow(String title, List<AnimeItem> items) {
        this.title = title;
        this.items = items;
    }
}

package com.example.stasenkopavel.searchtest;

import io.realm.RealmObject;

/**
 * Created by stasenkopavel on 1/24/17.
 */

public class SearchItem extends RealmObject {

    private String title;
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }
}

package com.example.stasenkopavel.searchtest;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by stasenkopavel on 1/24/17.
 */

public class SearchResponse extends RealmObject{

    public String query;
    public String kind;
    private RealmList<SearchItem> items;



    public RealmList<SearchItem> getItems() {
        return items;
    }
}

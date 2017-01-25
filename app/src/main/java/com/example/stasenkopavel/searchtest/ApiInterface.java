package com.example.stasenkopavel.searchtest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by stasenkopavel on 1/24/17.
 */

public interface ApiInterface {
    String SERVICE_ENDPOINT = "https://www.googleapis.com/";
    String apiKey = "AIzaSyDSwpXiTMgiCaHawQzLza0MBIHMQMWpFhw";
    String cx = "000524798721361898671:4mnkozz8vma";

    @GET("/customsearch/v1/")
    Observable<SearchResponse> search(@Query("key") String key, @Query("cx") String cx, @Query("start") int start, @Query("q") String query);
}

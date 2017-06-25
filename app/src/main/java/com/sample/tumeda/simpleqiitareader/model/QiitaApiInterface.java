package com.sample.tumeda.simpleqiitareader.model;

import com.sample.tumeda.simpleqiitareader.data.rss.Feed;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface QiitaApiInterface {

    @GET("popular-items/feed")
    Observable<Feed> getPopularFeed();

    @GET("tags/{tag}/feed")
    Observable<Feed> getFeedWithTag(@Path(value = "tag") String tag);
}


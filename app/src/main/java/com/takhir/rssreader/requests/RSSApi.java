package com.takhir.rssreader.requests;

import com.takhir.rssreader.models.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RSSApi {

    @GET(".")
    Call<Rss> getRSSFeed();
}

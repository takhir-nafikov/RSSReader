package com.takhir.rssreader.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.takhir.rssreader.models.RSS;

public class RSSResponse {

    @SerializedName("rss")
    @Expose
    private RSS rss;

    public RSS getRSS() {
        return rss;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

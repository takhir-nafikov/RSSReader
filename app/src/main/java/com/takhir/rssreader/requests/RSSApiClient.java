package com.takhir.rssreader.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.takhir.rssreader.AppExecutors;
import com.takhir.rssreader.models.database.ChannelInfo;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.models.xml.Channel;
import com.takhir.rssreader.models.xml.Item;
import com.takhir.rssreader.models.xml.RSS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RSSApiClient {

    private static final String TAG = "RSSApiClient";

    private static RSSApiClient instance;
    private MutableLiveData<ChannelInfo> infoChannel;
    private MutableLiveData<List<Post>> posts;
    private RetrieveRSSRunnable retrieveRSSRunnable;

    public static RSSApiClient getInstance() {
        if (instance == null) {
            instance = new RSSApiClient();
        }
        return instance;
    }

    private RSSApiClient() {
        infoChannel = new MutableLiveData<>();
        posts = new MutableLiveData<>();
    }

    public LiveData<ChannelInfo> getInfoChannel() {
        return infoChannel;
    }

    public LiveData<List<Post>> getPosts() {
        return posts;
    }

    public void searchRSSFeeds(String url) {
        if (retrieveRSSRunnable != null) {
            retrieveRSSRunnable = null;
        }
        retrieveRSSRunnable = new RetrieveRSSRunnable(url);
        final Future handler = AppExecutors.getInstance().networkIo().submit(retrieveRSSRunnable);

        AppExecutors.getInstance().networkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRSSRunnable implements Runnable {

        private String url;

        public RetrieveRSSRunnable(String url) {
            this.url = url;
        }

        @Override
        public void run() {
            try {
                getRSS(url).enqueue(new Callback<RSS>() {
                    @Override
                    public void onResponse(Call<RSS> call, Response<RSS> response) {

                        Channel channel = response.body().getChannel();

                        ChannelInfo channelInfo = new ChannelInfo();
                        channelInfo.setUuid(url);
                        channelInfo.setLink(channel.getLink());
                        channelInfo.setTitle(channel.getTitle());
                        infoChannel.postValue(channelInfo);

                        List<Item> items = channel.getItems();
                        List<Post> postList = new ArrayList<>();
                        for(Item item : items) {
                            Post post = new Post();
                            post.setUuid(url);
                            post.setDescription(item.getDescription());
                            post.setDate(item.getPubDate());
                            post.setTitle(item.getTitle());
                            if (item.getEnclosure_url() != null) {
                                post.setImage(item.getEnclosure_url().getUrl());
                            }

                            postList.add(post);
                        }
                        posts.postValue(postList);
                    }

                    @Override
                    public void onFailure(Call<RSS> call, Throwable t) {
                        Log.e(TAG, t.getLocalizedMessage());
                        infoChannel.postValue(null);
                        posts.postValue(null);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                infoChannel.postValue(null);
                posts.postValue(null);
            }
        }

        private Call<RSS> getRSS(String url) {
            ServiceGenerator serviceGenerator = ServiceGenerator.newBuilder()
                    .setUrl(url)
                    .buildRetrofit()
                    .setRSSApi().build();

            return serviceGenerator.getRssApi().getRSSFeed();
        }
    }

}

package com.takhir.rssreader.requests;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.takhir.rssreader.AppExecutors;
import com.takhir.rssreader.models.RSS;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RSSApiClient {

    private static final String TAG = "RSSApiClient";

    private static RSSApiClient instance;
    private MutableLiveData<RSS> Rss;
    private RetrieveRSSRunnable retrieveRSSRunnable;

    public static RSSApiClient getInstance() {
        if (instance == null) {
            instance = new RSSApiClient();
        }
        return instance;
    }

    private RSSApiClient() {
        Rss = new MutableLiveData<>();
    }

    public LiveData<RSS> getRss() {
        return Rss;
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
                        Rss.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<RSS> call, Throwable t) {
                        Log.e(TAG, t.getLocalizedMessage());
                        Rss.postValue(null);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Rss.postValue(null);
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

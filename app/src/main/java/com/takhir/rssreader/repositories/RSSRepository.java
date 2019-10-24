package com.takhir.rssreader.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.takhir.rssreader.AppExecutors;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.models.xml.RSS;
import com.takhir.rssreader.persistence.ChannelInfoDao;
import com.takhir.rssreader.persistence.PostDao;
import com.takhir.rssreader.persistence.RSSDatabase;
import com.takhir.rssreader.requests.ServiceGenerator;
import com.takhir.rssreader.requests.responses.ApiResponse;
import com.takhir.rssreader.requests.responses.RSSResponse;
import com.takhir.rssreader.utils.NetworkBoundResource;
import com.takhir.rssreader.utils.Resource;

import java.util.List;

public class RSSRepository {

    private static final String TAG = "RSSRepository";

    private static RSSRepository instance;
    private PostDao postDao;
    private ChannelInfoDao channelInfoDao;

    public static RSSRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RSSRepository(context);
        }
        return instance;
    }

    private RSSRepository(Context context) {
        postDao = RSSDatabase.getInstance(context).getPostDao();
        channelInfoDao = RSSDatabase.getInstance(context).getChannelInfoDao();
    }

    public LiveData<Resource<List<Post>>> searchPosts(final String url, final String uuid) {
        return new NetworkBoundResource<List<Post>, RSS>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull RSS item) {
                if (item.getChannel() != null) {
                    Log.d(TAG, "ok: " + item.getChannel().getTitle());
                } else {
                    Log.e(TAG, "failed");
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Post> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Post>> loadFromDb() {
                return postDao.searchPosts(uuid);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<RSS>> createCall() {
                ServiceGenerator serviceGenerator = ServiceGenerator.newBuilder()
                        .setUrl(url)
                        .buildRetrofit()
                        .setRSSApi().build();

                return serviceGenerator.getRssApi().getRSSFeed();
            }
        }.getAsLiveData();
    }
}

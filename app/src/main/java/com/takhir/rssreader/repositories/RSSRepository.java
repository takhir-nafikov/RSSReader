package com.takhir.rssreader.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.takhir.rssreader.AppExecutors;
import com.takhir.rssreader.models.database.ChannelInfo;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.persistence.ChannelInfoDao;
import com.takhir.rssreader.persistence.PostDao;
import com.takhir.rssreader.persistence.RSSDatabase;
import com.takhir.rssreader.requests.RSSApiClient;

import java.util.List;

public class RSSRepository {

    private static final String TAG = "RSSRepository";

    private MutableLiveData<Post> intentPost = new MutableLiveData<>();

    private static RSSRepository instance;
    private static RSSApiClient rssApiClient;
    private PostDao postDao;
    private ChannelInfoDao channelInfoDao;

    public static RSSRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RSSRepository(context);
        }
        return instance;
    }

    private RSSRepository(Context context) {
        rssApiClient = RSSApiClient.getInstance();
        postDao = RSSDatabase.getInstance(context).getPostDao();
        channelInfoDao = RSSDatabase.getInstance(context).getChannelInfoDao();
    }

    public void searchRSSFeeds(String url) {
        rssApiClient.searchRSSFeeds(url);
    }

    public LiveData<List<Post>> getPosts() {
        return rssApiClient.getPosts();
    }

    public LiveData<ChannelInfo> getInfoChannel() {
        return rssApiClient.getInfoChannel();
    }

    public void savePostsInDb(String uuid) {
        List<Post> postList = rssApiClient.getPosts().getValue();
        Post[] recipes = new Post[postList.size()];
        postDao.deletePostsByUUID(uuid);
        postDao.insertRecipes((Post[]) (postList.toArray(recipes)));
    }

    public LiveData<List<Post>> getPostsFromDbByUUID(String uuid) {
        return postDao.searchPosts(uuid);
    }

    public void saveChannelInfoInDb(final ChannelInfo channelInfo) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                channelInfoDao.clearTable();
                channelInfoDao.insertInfo(channelInfo);
            }
        });
    }

    public LiveData<ChannelInfo> getChannelInfoFromDBByUUID(String uuid){
        return channelInfoDao.getChannelInfo(uuid);
    }

    public LiveData<Post> getIntentPost() {
        return intentPost;
    }

    public void setIntentPost(Post post) {
        intentPost.postValue(post);
    }
}

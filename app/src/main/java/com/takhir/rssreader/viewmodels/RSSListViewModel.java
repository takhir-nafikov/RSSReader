package com.takhir.rssreader.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.takhir.rssreader.models.database.ChannelInfo;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.repositories.RSSRepository;

import java.util.List;

public class RSSListViewModel extends AndroidViewModel {

    private RSSRepository rssRepository;

    public RSSListViewModel(Application application) {
        super(application);
        rssRepository = RSSRepository.getInstance(application);
    }

    public void searchRSSFeeds(String url) {
        rssRepository.searchRSSFeeds(url);
    }

    public LiveData<List<Post>> getPosts() {
        return rssRepository.getPosts();
    }

    public LiveData<ChannelInfo> getInfoChannel() {
        return rssRepository.getInfoChannel();
    }

    public void savePostsInDb(String uuid) {
        rssRepository.savePostsInDb(uuid);
    }

    public LiveData<List<Post>> getPostsFromDbByUUID(String uuid) {
        return rssRepository.getPostsFromDbByUUID(uuid);
    }

    public void saveChannelInfoInDb(final ChannelInfo channelInfo) {
        rssRepository.saveChannelInfoInDb(channelInfo);
    }

    public LiveData<ChannelInfo> getChannelInfoFromDBByUUID(String uuid){
        return rssRepository.getChannelInfoFromDBByUUID(uuid);
    }

    public LiveData<Post> getIntentPost() {
        return rssRepository.getIntentPost();
    }

    public void setIntentPost(Post post) {
        rssRepository.setIntentPost(post);
    }
}

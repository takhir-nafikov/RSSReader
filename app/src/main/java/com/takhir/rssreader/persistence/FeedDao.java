package com.takhir.rssreader.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.takhir.rssreader.models.database.Feed;

import java.util.List;

@Dao
public interface FeedDao {

    @Query("SELECT * FROM feeds")
    List<Feed> loadAllFeeds();

    @Insert
    void insertFeed(Feed feed);

    @Delete
    void deleteFeed(Feed feed);

    @Query("DELETE FROM feeds")
    void clearTable();
}

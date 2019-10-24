package com.takhir.rssreader.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.takhir.rssreader.models.database.ChannelInfo;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ChannelInfoDao {

    @Insert(onConflict = REPLACE)
    void insertRecipe(ChannelInfo info);

    @Query("SELECT * FROM channels WHERE uuid LIKE :uuid")
    LiveData<ChannelInfo> getChannelInfo(String uuid);

    @Query("DELETE FROM channels")
    void clearTable();
}

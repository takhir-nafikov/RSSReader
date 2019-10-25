package com.takhir.rssreader.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.takhir.rssreader.models.database.ChannelInfo;
import com.takhir.rssreader.models.database.Feed;
import com.takhir.rssreader.models.database.Post;

@Database(entities = {Post.class, ChannelInfo.class, Feed.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class RSSDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "rss_db";

    private static RSSDatabase instance;

    public static RSSDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    RSSDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract PostDao getPostDao();

    public abstract ChannelInfoDao getChannelInfoDao();

    public abstract FeedDao getFeedDao();
}

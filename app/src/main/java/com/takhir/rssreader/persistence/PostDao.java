package com.takhir.rssreader.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.takhir.rssreader.models.database.Post;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PostDao {

    @Insert(onConflict = IGNORE)
    long[] insertRecipes(Post... post);

    @Query("UPDATE posts SET uuid = :uuid, title = :title, description = :description, date = :date, image = :image " +
            "WHERE id = :id")
    void updatePost(int id, String uuid, String title, String description, String date, String image);

    @Query("SELECT * FROM posts WHERE uuid LIKE :uuid LIMIT 30")
    LiveData<List<Post>> searchPosts(String uuid);

    @Query("DELETE FROM posts")
    void clearTable();
}

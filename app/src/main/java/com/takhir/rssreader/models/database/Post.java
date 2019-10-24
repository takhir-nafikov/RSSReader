package com.takhir.rssreader.models.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "posts")
public class Post implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "image")
    private String image;

    public Post(@NonNull int id, String uuid, String title, String description, String date, String image) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public Post() {

    }

    protected Post(Parcel in) {
        id = in.readInt();
        uuid = in.readString();
        title = in.readString();
        description = in.readString();
        date = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(uuid);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}

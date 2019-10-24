package com.takhir.rssreader.models.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "channels")
public class ChannelInfo implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "uuid")
    private String uuid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "link")
    private String link;

    public ChannelInfo() {

    }

    public ChannelInfo(@NonNull int id, String uuid, String title, String link) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.link = link;
    }

    protected ChannelInfo(Parcel in) {
        id = in.readInt();
        uuid = in.readString();
        title = in.readString();
        link = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(uuid);
        dest.writeString(title);
        dest.writeString(link);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChannelInfo> CREATOR = new Creator<ChannelInfo>() {
        @Override
        public ChannelInfo createFromParcel(Parcel in) {
            return new ChannelInfo(in);
        }

        @Override
        public ChannelInfo[] newArray(int size) {
            return new ChannelInfo[size];
        }
    };

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

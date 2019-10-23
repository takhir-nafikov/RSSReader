package com.takhir.rssreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.takhir.rssreader.models.Item;

public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    private AppCompatImageView rssImage;
    private TextView rssTitle, rssDesc, rssPubDate;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        rssImage = findViewById(R.id.rss_image);
        rssTitle = findViewById(R.id.rss_title);
        rssDesc = findViewById(R.id.rss_desc);
        rssPubDate = findViewById(R.id.rss_pub_date);
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("item")) {
            Item item = (Item) getIntent().getSerializableExtra("item");
            Log.d(TAG, "getIncomingIntent: " + item.getTitle());
            Log.d(TAG, "getIncomingIntent: " + item.getPubDate());
            Log.d(TAG, "getIncomingIntent: " + item.getDescription());
        }
    }
}


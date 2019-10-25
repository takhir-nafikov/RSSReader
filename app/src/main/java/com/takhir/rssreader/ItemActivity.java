package com.takhir.rssreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.takhir.rssreader.adapters.RSSViewHolder;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.models.xml.Item;

public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    private AppCompatImageView rssImage;
    private TextView rssTitle, rssDesc, rssPubDate;

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
        if (getIntent().hasExtra("post")) {
            Post post =  getIntent().getParcelableExtra("post");
            rssTitle.setText(post.getTitle());
            rssDesc.setText(post.getDescription());
            rssPubDate.setText(post.getDate());

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.totoro);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(post.getImage())
                    .into(rssImage);
        }
    }
}


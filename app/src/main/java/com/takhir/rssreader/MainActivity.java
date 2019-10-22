package com.takhir.rssreader;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.takhir.rssreader.adapters.ViewPagerAdapter;
import com.takhir.rssreader.models.Channel;
import com.takhir.rssreader.models.Item;
import com.takhir.rssreader.models.Rss;
import com.takhir.rssreader.requests.RSSApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        call();
    }

    private void call() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://habr.com/ru/rss/interesting/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        RSSApi rssApi = retrofit.create(RSSApi.class);

        Call<Rss> call = rssApi.getRSSFeed();

        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.d(TAG, "onResponse: Server Response: " + response.toString());

                Channel channel = response.body().getChannel();
                List<Item> items = channel.getItems();
                Log.d(TAG, "onResponse: items: " + items);

                Log.d(TAG, "onResponse: desc: " + items.get(0).getDescription());
                Log.d(TAG, "onResponse: updated: " + items.get(0).getPubDate());
                Log.d(TAG, "onResponse: title: " + items.get(0).getTitle());
                Log.d(TAG, "onResponse: title: " + items.get(0).getLink());
                Log.d(TAG, "onResponse: title: " + items.get(0).getEnclosure_url());
                Log.d(TAG, "onResponse: title: " + channel.getTitle());
                Log.d(TAG, "onResponse: title: " + channel.getLink());
                Log.d(TAG, "onResponse: title: " + channel.getImage().getUrl());
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.e(TAG, "onFailure: Unable to retrieve RSS: " + t.getMessage() );
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_categories) {
            adapter.addFragment(new CustomFragment());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}

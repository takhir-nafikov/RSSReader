package com.takhir.rssreader;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.takhir.rssreader.adapters.ViewPagerAdapter;
import com.takhir.rssreader.models.database.Feed;
import com.takhir.rssreader.persistence.RSSDatabase;
import com.takhir.rssreader.utils.DialogHelper;
import com.takhir.rssreader.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPagerAdapter adapter;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        toolbar = findViewById(R.id.toolbar);

//        TabLayout tabLayout = findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);

        setSupportActionBar(toolbar);
        //loadAllFeeds();
    }

    private void loadAllFeeds() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Feed> feeds =
                        RSSDatabase.getInstance(getBaseContext()).getFeedDao().loadAllFeeds();
                if (feeds != null && feeds.size() != 0){
                    for(Feed feed: feeds) {
                        CustomFragment customFragment = buildFragment(feed.getUrl());
                        adapter.addFragment(customFragment);
                    }
                }
            }
        });
    }

    private CustomFragment buildFragment(String url){
        CustomFragment fragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//switch лучше
        if (item.getItemId() == R.id.action_add) {
            DialogHelper.showUrlDialog(MainActivity.this, new DialogHelper.stringListener() {
                @Override
                public void getString(String text) {
                    final String url = Utils.checkUrl(text);

                    CustomFragment fragment = buildFragment(url);

                    adapter.addFragment(fragment);

//                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            Feed feed = new Feed();
//                            feed.setUrl(url);
//                            RSSDatabase.getInstance(getBaseContext()).getFeedDao().insertFeed(feed);
//                        }
//                    });
                }
            }).show();
        } else if (item.getItemId() == R.id.action_info) {
            int count = adapter.getCount();
            if (count != 0) {
                int position = viewPager.getCurrentItem();
                Fragment fragment = adapter.getItem(position);
                String channelInfo = ((CustomFragment)fragment).getChannelInfo();
                String[] info = channelInfo.split("\n");
                DialogHelper.showInfoChannel(MainActivity.this, info[0], info[1]).show();

            } else {
                Toast.makeText(this, "Нет лент", Toast.LENGTH_SHORT).show();
            }

        } else if (item.getItemId() == R.id.action_delete) {
            int count = adapter.getCount();
            if (count != 0) {
                int position = viewPager.getCurrentItem();
                adapter.remove(position);

            } else {
                Toast.makeText(this, "Нет лент", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

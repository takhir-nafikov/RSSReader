package com.takhir.rssreader;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.takhir.rssreader.adapters.RSSRecyclerAdapter;
import com.takhir.rssreader.models.Channel;
import com.takhir.rssreader.models.Item;
import com.takhir.rssreader.models.RSS;
import com.takhir.rssreader.requests.RSSApiClient;

import java.util.List;

public class CustomFragment extends Fragment implements RSSRecyclerAdapter.OnRecyclerListener {

    private static final String TAG = "CustomFragment";

    private RecyclerView recyclerView;
    private RSSRecyclerAdapter rssRecyclerAdapter;
    private String title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_custom, container, false);

        recyclerView = rootView.findViewById(R.id.rss_list);
        RSSApiClient.getInstance().searchRSSFeeds("https://lenta.ru/rss/news/");

        initRecyclerView();
        subscribeObserves();
        return rootView;
    }

    private void initRecyclerView() {
        rssRecyclerAdapter = new RSSRecyclerAdapter(this);
        recyclerView.setAdapter(rssRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void subscribeObserves() {
        RSSApiClient.getInstance().getRss().observe(this, new Observer<RSS>() {
            @Override
            public void onChanged(@Nullable RSS rss) {
                Channel channel = rss.getChannel();
                List<Item> items = channel.getItems();
                rssRecyclerAdapter.setItems(items);
            }
        });
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        Item item = rssRecyclerAdapter.getSelectedItem(position);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
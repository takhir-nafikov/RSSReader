package com.takhir.rssreader;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.takhir.rssreader.adapters.RSSRecyclerAdapter;
import com.takhir.rssreader.models.database.ChannelInfo;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.utils.Utils;
import com.takhir.rssreader.utils.VerticalSpacingItemDecorator;
import com.takhir.rssreader.viewmodels.RSSListViewModel;

import java.util.List;

public class CustomFragment extends Fragment implements RSSRecyclerAdapter.OnRecyclerListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CustomFragment";

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RSSRecyclerAdapter rssRecyclerAdapter;
    private RSSListViewModel rssListViewModel;
    private String title, link;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_custom, container, false);

        recyclerView = rootView.findViewById(R.id.rss_list);
        progressBar = rootView.findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            link = bundle.getString("url", "https://lenta.ru/rss/news/");
        }

        rssListViewModel = ViewModelProviders.of(this).get(RSSListViewModel.class);
        rssListViewModel.searchRSSFeeds(link);

        initRecyclerView();
        if (Utils.checkInternetConnection(getContext())) {
            subscribeObservesOnline();
        } else {
            subscribeObservesOffline();
        }
        return rootView;
    }


    private void initRecyclerView() {
        rssRecyclerAdapter = new RSSRecyclerAdapter(this);
        recyclerView.setAdapter(rssRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
    }

    private void subscribeObservesOnline() {

        rssListViewModel.getPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                if (posts != null) {
                    rssRecyclerAdapter.setPosts(posts);
                    progressBar.setVisibility(View.GONE);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            rssListViewModel.savePostsInDb(link);
                        }
                    });
                }
            }
        });

        rssListViewModel.getInfoChannel().observe(this, new Observer<ChannelInfo>() {
            @Override
            public void onChanged(@Nullable ChannelInfo info) {
                if (info != null) {
                    title = info.getTitle();
                    link = info.getLink();
                    rssListViewModel.saveChannelInfoInDb(info);
                }
            }
        });
    }

    private void subscribeObservesOffline() {

        rssListViewModel.getPostsFromDbByUUID(link).observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                if (posts != null) {
                    rssRecyclerAdapter.setPosts(posts);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getContext(), "Нет интернета и сохраненных данных", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rssListViewModel.getChannelInfoFromDBByUUID(link).observe(this, new Observer<ChannelInfo>() {
            @Override
            public void onChanged(@Nullable ChannelInfo info) {
                if (info != null) {
                    title = info.getTitle();
                    link = info.getLink();
                } else {
                    Toast.makeText(getContext(), "Нет интернета и сохраненных данных", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClickItem(int position) {
        Intent intent = new Intent(getActivity(), ItemActivity.class);
        Post post = rssRecyclerAdapter.getSelectedItem(position);
        intent.putExtra("post", post);
        startActivity(intent);
    }

    public String getChannelInfo() {
        return title + "\n" + link;
    }

    @Override
    public void onRefresh() {
        if (Utils.checkInternetConnection(getContext())) {
            subscribeObservesOnline();
        } else {
            subscribeObservesOffline();
        }
    }
}
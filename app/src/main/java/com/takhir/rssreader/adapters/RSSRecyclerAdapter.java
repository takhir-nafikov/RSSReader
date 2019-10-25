package com.takhir.rssreader.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.takhir.rssreader.R;
import com.takhir.rssreader.models.database.Post;
import com.takhir.rssreader.models.xml.Enclosure;
import com.takhir.rssreader.models.xml.Item;

import java.util.ArrayList;
import java.util.List;

public class RSSRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnRecyclerListener {
        void onClickItem(int position);
    }

    private static final int ITEM_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Post> posts;
    private OnRecyclerListener listener;

    public RSSRecyclerAdapter(OnRecyclerListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rss_list_item, parent, false);
        return new RSSViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.totoro);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(posts.get(position).getImage())
                .into(((RSSViewHolder)holder).image);

        ((RSSViewHolder)holder).title.setText(posts.get(position).getTitle());
        ((RSSViewHolder)holder).desc.setText(posts.get(position).getDescription());
        ((RSSViewHolder)holder).pubDate.setText((posts.get(position).getDate()));
    }

    @Override
    public int getItemCount() {
        if (posts != null) {
            return  posts.size();
        }
        return 0;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public Post getSelectedItem(int pos) {
        if (posts != null && posts.size() > 0) {
            return posts.get(pos);
        }
        return null;
    }
}

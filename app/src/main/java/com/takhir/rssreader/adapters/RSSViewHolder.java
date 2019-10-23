package com.takhir.rssreader.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.takhir.rssreader.R;

public class RSSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, desc, pubDate;
    AppCompatImageView image;
    RSSRecyclerAdapter.OnRecyclerListener listener;

    public RSSViewHolder(View itemView, RSSRecyclerAdapter.OnRecyclerListener listener) {
        super(itemView);

        this.listener = listener;

        title = itemView.findViewById(R.id.rss_title);
        desc = itemView.findViewById(R.id.rss_desc);
        pubDate = itemView.findViewById(R.id.rss_pub_date);
        image = itemView.findViewById(R.id.rss_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onClickItem(getAdapterPosition());
    }
}

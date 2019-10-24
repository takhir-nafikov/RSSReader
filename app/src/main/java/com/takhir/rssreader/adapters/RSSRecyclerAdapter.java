package com.takhir.rssreader.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.takhir.rssreader.R;
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

    private List<Item> items;
    private OnRecyclerListener listener;

    public RSSRecyclerAdapter(OnRecyclerListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == LOADING_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
            return new RSSViewHolder(view, listener);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rss_list_item, parent, false);
            return new RSSViewHolder(view, listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(items.get(position).getEnclosure_url().getUrl())
                .into(((RSSViewHolder)holder).image);

        ((RSSViewHolder)holder).title.setText(items.get(position).getTitle());
        ((RSSViewHolder)holder).desc.setText(items.get(position).getDescription());
        ((RSSViewHolder)holder).pubDate.setText((items.get(position).getPubDate()));
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        } else{
            return ITEM_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return  items.size();
        }
        return 0;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public Item getSelectedItem(int pos) {
        if (items != null && items.size() > 0) {
            return items.get(pos);
        }
        return null;
    }

    public void displayLoading(){
        if (!isLoading()) {
            Item item = new Item();
            item.setTitle("LOADING...");
            item.setEnclosure_url(new Enclosure());
            List<Item> loadingList = new ArrayList<>();
            loadingList.add(item);
            items = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (items != null && items.size() > 0) {
            if (items.get(items.size() - 1).getTitle().equals("LOADING...")) {
                return true;
            }
        }
        return false;
    }

    private void hideLoading(){
        if(isLoading()){
            for(Item item: items){
                if(item.getTitle().equals("LOADING...")){
                    items.remove(item);
                }
            }
            notifyDataSetChanged();
        }
    }
}

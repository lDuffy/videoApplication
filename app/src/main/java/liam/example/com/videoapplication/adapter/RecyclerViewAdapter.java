package liam.example.com.videoapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import com.squareup.picasso.Picasso;

import liam.example.com.videoapplication.R;
import liam.example.com.videoapplication.model.EntryData;
import liam.example.com.videoapplication.model.Feed;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private Feed feed;
    private Context context;
    @Inject
    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new RecyclerViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (null == feed) {
            return;
        }
        EntryData data = feed.getData().getEntries().getList()[position].getData();
        Picasso.with(context).load(data.getThumbnail_image()).into(holder.photo);
        holder.title.setText(data.getTitle());

    }

    public void setItems(Feed itemList) {
        feed = itemList;
    }

    @Override
    public int getItemCount() {
        if (null == feed) {
            return 0;
        }
        return feed.getData().getEntry_count();
    }
}
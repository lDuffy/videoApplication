package liam.example.com.videoapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import liam.example.com.videoapplication.R;

/**
 * Created by lduf0001 on 08/10/2016.
 * View holder for RecyclerViewAdapter
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.photo) public ImageView photo;
    @BindView(R.id.title) public TextView title;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
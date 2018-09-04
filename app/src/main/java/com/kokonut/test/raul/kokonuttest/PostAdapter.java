package com.kokonut.test.raul.kokonuttest;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kokonut.test.raul.kokonuttest.model.PostItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private ArrayList<PostItem> items;

    public PostAdapter(Context context, ArrayList<PostItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        PostItem item = items.get(position);

        Glide.with(context)
                .load(item.getImage_url())
                .apply(new RequestOptions().error(R.drawable.broken_image))
                .transition(DrawableTransitionOptions.withCrossFade()).into(holder.post_image);

        holder.post_header.setText(item.getTitle());
        holder.post_card.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items!= null ?items.size():0;
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.post_card)
        public View post_card;

        @BindView(R.id.post_image)
        public ImageView post_image;

        @BindView(R.id.post_header)
        public TextView post_header;


        public PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

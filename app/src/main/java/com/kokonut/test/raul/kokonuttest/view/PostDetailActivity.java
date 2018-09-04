package com.kokonut.test.raul.kokonuttest.view;

import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kokonut.test.raul.kokonuttest.R;
import com.kokonut.test.raul.kokonuttest.model.PostItem;

import butterknife.BindView;

public class PostDetailActivity extends BaseActivity {
    public static final String EXTRA_POST_ITEM = "extra_post_item";

    @BindView(R.id.post_title)
    protected TextView post_title;

    @BindView(R.id.post_image)
    protected ImageView post_image;

    @BindView(R.id.post_content)
    protected TextView post_content;

    private PostItem item;

    @Override
    void createView() {
        setContentView(R.layout.activity_post_detail);
    }

    @Override
    void createController() {
        item = null;
        if(getIntent().hasExtra(EXTRA_POST_ITEM)){
            item = getGson().fromJson(getIntent().getStringExtra(EXTRA_POST_ITEM),PostItem.class);
        }
    }

    @Override
    void setupView() {
        if(item != null) {
            post_title.setText(item.getTitle());
            Spanned htmlAsSpanned = Html.fromHtml(item.getBody());
            post_content.setText(htmlAsSpanned);

            Glide.with(this)
                    .load(item.getImage_url())
                    .apply(new RequestOptions().error(R.drawable.broken_image))
                    .transition(DrawableTransitionOptions.withCrossFade()).into(post_image);
        }
    }

    @Override
    void refresh() {

    }
}

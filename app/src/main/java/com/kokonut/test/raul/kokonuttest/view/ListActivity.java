package com.kokonut.test.raul.kokonuttest.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.kokonut.test.raul.kokonuttest.PostAdapter;
import com.kokonut.test.raul.kokonuttest.R;
import com.kokonut.test.raul.kokonuttest.control.PostController;
import com.kokonut.test.raul.kokonuttest.model.LoginResponse;
import com.kokonut.test.raul.kokonuttest.model.PostItem;
import com.kokonut.test.raul.kokonuttest.model.PostResponse;
import com.kokonut.test.raul.kokonuttest.model.ProfileResponse;

import butterknife.BindView;
import rx.Subscriber;

public class ListActivity extends BaseActivity {
    public static final String EXTRA_LOGIN_RESPONSE = "extra_login_response";

    @BindView(R.id.recycler_posts)
    protected RecyclerView recycler_posts;

    @BindView(R.id.loading_layer)
    protected View loading_layer;

    private PostController controller;
    private LoginResponse response;
    private ProfileResponse profile;

    @Override
    void createView() {
        setContentView(R.layout.activity_post_list);
    }

    @Override
    void createController() {
        try {
            if (getIntent().hasExtra(EXTRA_LOGIN_RESPONSE)) {
                response = getGson().fromJson(getIntent().getStringExtra(EXTRA_LOGIN_RESPONSE),
                        LoginResponse.class);
            }
        }catch (Exception ex){
            response = null;
        }finally {
            controller = new PostController(this);
        }

    }

    @Override
    void setupView() {
        recycler_posts.setHasFixedSize(true);
        recycler_posts.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    void refresh() {


        loading_layer.setVisibility(View.VISIBLE);

        controller.showPosts(new Subscriber<PostResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ListActivity.this,
                        R.string.error_login,
                        Toast.LENGTH_SHORT).show();

                loading_layer.setVisibility(View.GONE);
            }

            @Override
            public void onNext(PostResponse postResponse) {

                if(postResponse.getSuccess() == LoginResponse.SUCCESS){
                    renderPosts(postResponse);
                }else {
                    (new AlertDialog.Builder(ListActivity.this))
                            .setMessage(postResponse.getMessage())
                            .setTitle(R.string.title_message)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }

                loading_layer.setVisibility(View.GONE);
            }
        });
    }

    private void renderPosts(PostResponse postResponse){
        recycler_posts.setAdapter(new PostAdapter(this,postResponse.getData().getData()));
    }

    private void renderProfile(ProfileResponse profileResponse){

        (new AlertDialog.Builder(ListActivity.this))
                .setMessage(String.format(getString(R.string.profile_welcome),profileResponse.getData().getName(),profileResponse.getData().getLast_name()))
                .setTitle(R.string.title_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    public void onPostClick(View pView){
        PostItem item = (PostItem)pView.getTag();
        if(item != null){
            Intent intent = new Intent(this,PostDetailActivity.class);
            intent.putExtra(PostDetailActivity.EXTRA_POST_ITEM,getGson().toJson(item));
            startActivity(intent);
        }
    }


    public void onProfileClick(View pView){

        if(response != null){
            loading_layer.setVisibility(View.VISIBLE);
            controller.getProfile(response.getData().getToken_type()+" "+ response.getData().getAccess_token(),
                    new Subscriber<ProfileResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(ListActivity.this,
                            R.string.error_login,
                            Toast.LENGTH_SHORT).show();
                    loading_layer.setVisibility(View.GONE);
                }

                @Override
                public void onNext(ProfileResponse profileResponse) {
                    loading_layer.setVisibility(View.GONE);
                    if(profileResponse.getSuccess() == LoginResponse.SUCCESS){
                        renderProfile(profileResponse);
                    }else {
                        (new AlertDialog.Builder(ListActivity.this))
                                .setMessage(profileResponse.getMessage())
                                .setTitle(R.string.title_message)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    }

                }
            });
        }else{
            Toast.makeText(ListActivity.this,
                    R.string.error_login,
                    Toast.LENGTH_SHORT).show();
        }

    }
}

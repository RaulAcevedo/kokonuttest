package com.kokonut.test.raul.kokonuttest.control;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kokonut.test.raul.kokonuttest.util.ApplicationConstants;

import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class BaseController {
    private Context context;
    private Retrofit retrofit;

    public BaseController(@NonNull Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return this.context;
    }

    protected Retrofit getRetrofit() {

        Gson gson = new GsonBuilder().create();
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApplicationConstants.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(rxAdapter)
                    .build();
        }

        return retrofit;

    }

    protected Boolean isNetworkingOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeConnection = cm.getActiveNetworkInfo();
        return (activeConnection != null) && activeConnection.isConnected();
    }

}

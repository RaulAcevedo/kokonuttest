package com.kokonut.test.raul.kokonuttest.control;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.os.ConfigurationCompat;

import com.kokonut.test.raul.kokonuttest.model.PostResponse;
import com.kokonut.test.raul.kokonuttest.model.ProfileResponse;
import com.kokonut.test.raul.kokonuttest.util.ApplicationConstants;

import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class PostController extends BaseController {

    public PostController(@NonNull Context context) {
        super(context);
    }

    public void showPosts (Subscriber<PostResponse> subscriber){

        String version = "";
        if(isNetworkingOnline()){
            try {
                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Locale currentLocale = ConfigurationCompat.getLocales(getContext().getResources().getConfiguration()).get(0);

            ServiceEndpoint endpoint = getRetrofit().create(ServiceEndpoint.class);
            endpoint.showPosts(ApplicationConstants.OS_NAME+version,
                    currentLocale.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }else{
            subscriber.onError(new Exception(ApplicationConstants.ERROR_NETWORK_NOT_AVAILABLE));
        }

    }

    public void getProfile(String authToken,
                           Subscriber<ProfileResponse> subscriber){

        String version = "";
        if(isNetworkingOnline()){
            try {
                PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Locale currentLocale = ConfigurationCompat.getLocales(getContext().getResources().getConfiguration()).get(0);

            ServiceEndpoint endpoint = getRetrofit().create(ServiceEndpoint.class);
            endpoint.getProfile(ApplicationConstants.OS_NAME+version,
                    currentLocale.toString(),
                    authToken)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }else{
            subscriber.onError(new Exception(ApplicationConstants.ERROR_NETWORK_NOT_AVAILABLE));
        }

    }
}

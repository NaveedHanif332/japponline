package com.japp.online;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.MobileAds;
import com.google.android.libraries.places.api.Places;


import com.japp.online.R;
import com.japp.online.helper.LocaleHelper;
import com.japp.online.utills.NoInternet.AppLifeCycleManager;

public class App extends Application {
    private static App instance;

    public static App getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        MobileAds.initialize(this, String.valueOf(R.string.Admob_app_id));
        AppLifeCycleManager.init(this);
        String apiKey = "AIzaSyCU9-DVKNM2GyoJ2_9MdMtCSU8zqD1pTLc";
        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
        MultiDex.install(this);
    }
}

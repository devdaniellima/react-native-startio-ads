package com.startioads;

import android.content.Context;
import android.widget.LinearLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.startapp.sdk.ads.banner.Banner;

public class NativeBannerAds extends LinearLayout {
    private ThemedReactContext context;
    static Integer number = 0;

    public NativeBannerAds(final ThemedReactContext context){
        super(context);

        this.init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.ad_view, this);
        //Banner b = findViewById(R.id.startAppBanner);
        //b.loadAd(100, 50);
    }
}

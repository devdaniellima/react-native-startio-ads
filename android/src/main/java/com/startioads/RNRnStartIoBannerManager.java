package com.startioads;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class RNRnStartIoBannerManager extends ViewGroupManager<FrameLayout> {
    private static final String REACT_CLASS = "RNRnStartIoBanner";

    public static ReactApplicationContext reactContext;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected FrameLayout createViewInstance(ThemedReactContext reactContext) {
        LayoutInflater inflater = (LayoutInflater) reactContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.ad_view, null);

        return frameLayout;
    }
}

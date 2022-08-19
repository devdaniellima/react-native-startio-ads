package br.com.dlotecnologia.startioads;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.image.ReactImageView;
import com.startapp.sdk.ads.banner.Banner;
import com.startapp.sdk.ads.banner.BannerListener;

import java.util.Map;

import javax.annotation.Nullable;

public class RnStartIoBannerXmlManager extends ViewGroupManager<FrameLayout> {
    public static final String REACT_CLASS = "RnStartIoBannerXml";

    public static final String EVENT_AD_RECEIVE = "onReceiveAd";
    public static final String EVENT_AD_FAILED_TO_RECEIVE = "onFailedToReceiveAd";
    public static final String EVENT_AD_IMPRESSION = "onImpression";
    public static final String EVENT_AD_CLICK = "onClick";

    public Banner appBanner;

    public ReactContext rnContext;

    public static Integer viewId;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected FrameLayout createViewInstance(ThemedReactContext reactContext) {
        this.rnContext = reactContext;

        LayoutInflater inflater = (LayoutInflater) reactContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.ad_view, null);

        this.appBanner = (Banner) frameLayout.findViewById(R.id.startAppBanner);

        this.appBanner.setBannerListener(new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                Log.d(RnStartIoBannerXmlManager.REACT_CLASS, EVENT_AD_RECEIVE + " " + view.getId());
                WritableMap params = Arguments.createMap();
                params.putString("message", EVENT_AD_RECEIVE);
                sendEvent(EVENT_AD_RECEIVE, params);
            }

            @Override
            public void onFailedToReceiveAd(View view) {
                Log.d(RnStartIoBannerXmlManager.REACT_CLASS, EVENT_AD_FAILED_TO_RECEIVE);
                WritableMap params = Arguments.createMap();
                params.putString("message", EVENT_AD_FAILED_TO_RECEIVE);
                sendEvent(EVENT_AD_FAILED_TO_RECEIVE, params);
            }

            @Override
            public void onImpression(View view) {
                Log.d(RnStartIoBannerXmlManager.REACT_CLASS, EVENT_AD_IMPRESSION);
                WritableMap params = Arguments.createMap();
                params.putString("message", EVENT_AD_IMPRESSION);
                sendEvent(EVENT_AD_IMPRESSION, params);
            }

            @Override
            public void onClick(View view) {
                Log.d(RnStartIoBannerXmlManager.REACT_CLASS, EVENT_AD_CLICK);
                WritableMap params = Arguments.createMap();
                params.putString("message", EVENT_AD_CLICK);
                sendEvent(EVENT_AD_CLICK, params);
            }
        });

        return frameLayout;
    }

    @ReactProp(name = "hideBanner")
    public void setHideBanner(FrameLayout view, Boolean hideBanner) {
        viewId = view.getId();

        if (hideBanner) {
            appBanner.hideBanner();
        } else {
            appBanner.showBanner();
        }
    }

    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        builder.put(this.EVENT_AD_RECEIVE, MapBuilder.of("registrationName", this.EVENT_AD_RECEIVE));
        builder.put(this.EVENT_AD_FAILED_TO_RECEIVE, MapBuilder.of("registrationName", this.EVENT_AD_FAILED_TO_RECEIVE));
        builder.put(this.EVENT_AD_IMPRESSION, MapBuilder.of("registrationName", this.EVENT_AD_IMPRESSION));
        builder.put(this.EVENT_AD_CLICK, MapBuilder.of("registrationName", this.EVENT_AD_CLICK));

        return builder.build();
    }

    private void sendEvent(String type, WritableMap payload) {
        WritableMap event = Arguments.createMap();
        event.putString("type", type);

        if (payload != null) {
            event.merge(payload);
        }

        rnContext.getJSModule(RCTEventEmitter.class)
                .receiveEvent(viewId, type, event);
    }
}

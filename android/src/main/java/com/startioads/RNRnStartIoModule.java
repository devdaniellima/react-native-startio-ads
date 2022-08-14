
package com.startioads;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.facebook.react.bridge.Promise;

import com.facebook.react.bridge.ReactApplicationContext;

import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;

import java.util.HashMap;
import java.util.Map;

public class RNRnStartIoModule extends ReactContextBaseJavaModule {
  private static final String REACT_CLASS = "RNStartIo";

  private final ReactApplicationContext reactContext;

  public static String INTERSTITIAL_LOAD_SUCCESS = "INTERSTITIAL_LOAD_SUCCESS";
  public static String INTERSTITIAL_LOAD_ERROR = "INTERSTITIAL_LOAD_ERROR";

  private final StartAppAd startAppAd;

  public RNRnStartIoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.startAppAd = new StartAppAd(reactContext);
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();

    constants.put("INTERSTITIAL_LOAD_SUCCESS", INTERSTITIAL_LOAD_SUCCESS);
    constants.put("INTERSTITIAL_LOAD_ERROR", INTERSTITIAL_LOAD_ERROR);

    return constants;
  }

  @ReactMethod
  public void showInterstitial(Promise promise) {
    Activity currentActivity = getCurrentActivity();

    currentActivity.runOnUiThread(() -> {
      StartAppAd.showAd(currentActivity);
      promise.resolve(null);
    });
  }

  @ReactMethod
  public void loadInterstitial(Promise promise) {
    startAppAd.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener() {
      @Override
      public void onReceiveAd(@NonNull Ad ad) {
        promise.resolve(INTERSTITIAL_LOAD_SUCCESS);
      }

      @Override
      public void onFailedToReceiveAd(@Nullable Ad ad) {
        promise.resolve(INTERSTITIAL_LOAD_ERROR);
      }
    });
  }
}
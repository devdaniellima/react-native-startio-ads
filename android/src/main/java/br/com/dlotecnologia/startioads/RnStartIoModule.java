
package br.com.dlotecnologia.startioads;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.VideoListener;

import java.util.HashMap;
import java.util.Map;

public class RnStartIoModule extends ReactContextBaseJavaModule {
  private static final String REACT_CLASS = "RnStartIoModules";

  private final ReactApplicationContext reactContext;

  public static String INTERSTITIAL_LOAD_SUCCESS = "INTERSTITIAL_LOAD_SUCCESS";
  public static String INTERSTITIAL_LOAD_ERROR = "INTERSTITIAL_LOAD_ERROR";
  public static String REWARDED_LOAD_SUCCESS = "REWARDED_LOAD_SUCCESS";
  public static String REWARDED_LOAD_ERROR = "REWARDED_LOAD_ERROR";
  public static String INTERSTITIAL_AUTOMATIC = "INTERSTITIAL_AUTOMATIC";
  public static String INTERSTITIAL_VIDEO = "INTERSTITIAL_VIDEO";
  public static String INTERSTITIAL_OFFERWALL = "INTERSTITIAL_OFFERWALL";

  private StartAppAd startAppAdInterstitial;
  private StartAppAd startAppAdRewarded;

  public RnStartIoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, params);
  }

  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();

    constants.put("INTERSTITIAL_LOAD_SUCCESS", INTERSTITIAL_LOAD_SUCCESS);
    constants.put("INTERSTITIAL_LOAD_ERROR", INTERSTITIAL_LOAD_ERROR);
    constants.put("REWARDED_LOAD_SUCCESS", REWARDED_LOAD_SUCCESS);
    constants.put("REWARDED_LOAD_ERROR", REWARDED_LOAD_ERROR);
    constants.put("INTERSTITIAL_AUTOMATIC", INTERSTITIAL_AUTOMATIC);
    constants.put("INTERSTITIAL_VIDEO", INTERSTITIAL_VIDEO);
    constants.put("INTERSTITIAL_OFFERWALL", INTERSTITIAL_OFFERWALL);

    return constants;
  }

  @ReactMethod
  public void initialize(final String appId, final boolean useReturnAds, final boolean testAds, final Promise promise) {
    try {
      Log.d(REACT_CLASS, "appId: " + appId);
      StartAppSDK.init(this.getReactApplicationContext(), appId, useReturnAds);
      StartAppSDK.setTestAdsEnabled(testAds);

      this.startAppAdInterstitial = new StartAppAd(reactContext);
      this.startAppAdRewarded = new StartAppAd(reactContext);
      this.startAppAdRewarded.setVideoListener(new VideoListener() {
        @Override
        public void onVideoCompleted() {
          WritableMap params = Arguments.createMap();
          sendEvent(
            getReactApplicationContext(),
            "rewarded",
            params
          );
          Log.d(REACT_CLASS, "rewarded complete");
        }
      });

      promise.resolve("Initialized");
      Log.d(REACT_CLASS, "Initialized");
    } catch (Exception e) {
      Log.d(REACT_CLASS, e.getMessage());
      promise.reject(e);
    }
  }

  @ReactMethod
  public void setUserConsent(final boolean value, final Promise promise) {
    try {
      StartAppSDK.setUserConsent(
        this.getReactApplicationContext(),
        "pas",
        System.currentTimeMillis(),
        value
      );
      promise.resolve(null);
      Log.d(REACT_CLASS, "Set user consent");
    } catch (Exception e) {
      Log.d(REACT_CLASS, e.getMessage());
      promise.reject(e);
    }
  }

  @ReactMethod
  public void showInterstitial(Promise promise) {
    startAppAdInterstitial.showAd();
  }

  @ReactMethod
  public void loadInterstitial(final String modeStr, Promise promise) {
    StartAppAd.AdMode mode;
    if (new String(modeStr).equals(INTERSTITIAL_OFFERWALL)) {
      mode = StartAppAd.AdMode.OFFERWALL;
    } else if (new String(modeStr).equals(INTERSTITIAL_VIDEO)) {
      mode = StartAppAd.AdMode.VIDEO;
    } else {
      mode = StartAppAd.AdMode.AUTOMATIC;
    }
    startAppAdInterstitial.loadAd(mode, new AdEventListener() {
      @Override
      public void onReceiveAd(@NonNull Ad ad) {
        Log.d(REACT_CLASS, "loadRewarded Success");
        Log.d(REACT_CLASS, "Ad rewarded type " + ad.getType());
        promise.resolve(INTERSTITIAL_LOAD_SUCCESS);
      }

      @Override
      public void onFailedToReceiveAd(@Nullable Ad ad) {
        Log.d(REACT_CLASS, "loadRewarded Error");
        Log.d(REACT_CLASS, "Ad rewarded not display reason " + ad.getNotDisplayedReason());
        promise.resolve(INTERSTITIAL_LOAD_ERROR);
      }
    });
  }

  @ReactMethod
  public void showRewarded(Promise promise) {
    startAppAdRewarded.showAd();
  }

  @ReactMethod
  public void loadRewarded(Promise promise) {
    startAppAdRewarded.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
      @Override
      public void onReceiveAd(@NonNull Ad ad) {
        Log.d(REACT_CLASS, "loadRewarded Success");
        Log.d(REACT_CLASS, "Ad rewarded type " + ad.getType());
        promise.resolve(REWARDED_LOAD_SUCCESS);
      }

      @Override
      public void onFailedToReceiveAd(@Nullable Ad ad) {
        Log.d(REACT_CLASS, "loadRewarded Error");
        Log.d(REACT_CLASS, "Ad rewarded not display reason " + ad.getNotDisplayedReason());
        promise.resolve(REWARDED_LOAD_SUCCESS);
      }
    });
  }
}
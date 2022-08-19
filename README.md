# react-native-startio-ads

This library was created to enable the use of the advertising SDK provided by Start.io.
All development was based on official Start.io [documentation](https://support.start.io/hc/en-us/articles/360014774799-Integration-via-Maven).

* **At the moment, this lib only work for Android.**

## Getting started

`$ yarn add react-native-startio-ads`

`$ npm install react-native-startio-ads --save`

### Mostly automatic installation

`$ react-native link react-native-startio-ads`

### Manual installation

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
    - Add `import com.reactlibrary.RNRnStartIoPackage;` to the imports at the top of the file
    - Add `new RNRnStartIoPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    ```
    include ':react-native-startio-ads'
    project(':react-native-startio-ads').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-startio-ads/android')
    ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
	compile project(':react-native-startio-ads')
    ```

## Pre-requisites

First of all, you must go to the Start.io [developer's portal](https://portal.start.io/#/signin) and create your app if it is not already created. Have the *APP ID* number that is displayed in the application list ready.

## Usage

First, initiate the RNStartIoAds in App.js (or other first class). Replace "startappAppId" with your own value provided in the [developers’ portal](https://portal.start.io/#/signin).


```javascript
import RNStartIoAds from 'react-native-startio-ads';

RNStartIoAds.initialize({
  appId: 'startappAppId',
  // testAds: true, # initializes the library in test mode.
});
```

### Usage with **Banner Ad**
```javascript
import React from 'react';
import {BannerAd} from 'react-native-startio-ads';

const Home = () => {
  return (
    <BannerAd
	  onReceiveAd={() => {}}
      onFailedToReceiveAd={() => {}}
      onImpression={() => {}}
      onClick={() => {}}
	/>
  )
}

export default Home;
```

### Usage with **Interstitial Ad**
```javascript
import React, {useEffect} from 'react';
import RNStartIoAds, {Types} from 'react-native-startio-ads';

const Home = () => {
  const loadAndShowInterstitial = async () => {
    await RNStartIoAds.loadInterstitial(
      // Types.INTERSTITIAL_AUTOMATIC (default) | Types.INTERSTITIAL_VIDEO | Types.INTERSTITIAL_OFFERWALL
    )
    .then(result => {
      if (result === Types.INTERSTITIAL_LOAD_SUCCESS) {
        RNStartIoAds.showInterstitial();
      }
    })
    .catch(error => {
      console.log(error);
    });
  }
	
  useEffect(() => {
    loadAndShowInterstitial();
  }, []);

  return <></>
}

export default Home;
```

### The Interstitial Ad can be loaded for viewing at a later time.
```javascript
import React, {useState, useEffect} from 'react';
import {TouchableOpacity, Text} from 'react-native';
import RNStartIoAds, {Types} from 'react-native-startio-ads';

const Home = () => {
  const [adLoaded, setAdLoaded] = useState(false);
  
  const loadInterstitial = async () => {
    setAdLoaded(false);
    await RNStartIoAds.loadInterstitial()
      .then(result => {
        if (result === Types.INTERSTITIAL_LOAD_SUCCESS) {
          //RNStartIoAds.showInterstitial();
          setAdLoaded(true);
        }
      })
      .catch(error => {
        console.log(error);
      });
  }

  const showInterstitial = async () => {
    await RNStartIoAds.showInterstitial();
    // Load can get call after show
    loadInterstitial();
  }

  useEffect(() => {
    loadInterstitial();
  }, [])

  return (
    <TouchableOpacity onPress={() => {
      if (adLoaded) {
        showInterstitial();
      }
    }}>
      <Text>Click to view the interstitial ad</Text>
    </TouchableOpacity>
  )
}

export default Home;
```

### Usage with **Rewarded Ad**
```javascript
import React, {useEffect} from 'react';
import RNStartIoAds, {Types} from 'react-native-startio-ads';

const Home = () => {
  const loadAndShowRewarded = async () => {
    await RNStartIoAds.loadRewarded()
      .then(result => {
        if (result === Types.REWARDED_LOAD_SUCCESS) {
          RNStartIoAds.showRewarded();
        }
      })
      .catch(error => {
        console.log(error);
      });
  }
	
  useEffect(() => {
    loadAndShowRewarded();

    RNStartIoAds.addListener('rewarded', () => {
      console.log('foi premiado');
    });

    return () => {
      RNStartIoAds.removeAllListeners();
    }
  }, []);

  return <></>
}

export default Home;
```

### The Rewarded Ad can be loaded for viewing at a later time.
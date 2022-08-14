import React from 'react'

import { View, requireNativeComponent } from 'react-native';

const BannerAd = () => {
    return (
        <View style={{width: '100%', height: 100}}>
            <RNRnStartIoBanner style={{width: '100%', height: '100%'}}/>
        </View>
    )
}

const RNRnStartIoBanner = requireNativeComponent(
    'RNRnStartIoBanner',
    BannerAd,
);

export default BannerAd
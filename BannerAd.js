import styles from 'components/ContainerGeneral/styles';
import React from 'react'

import { View, requireNativeComponent } from 'react-native';

const BannerAd = (props) => {
  const { 
    style,
    onReceiveAd = () => {},
    onFailedToReceiveAd = () => {},
    onImpression = () => {},
    onClick = () => {},
    hideBanner = false
  } = props
  
  return (
    <RnStartIoBannerXml 
      style={{width: '100%', height: 50, ...styles}}
      onReceiveAd={onReceiveAd}
      onFailedToReceiveAd={onFailedToReceiveAd}
      onImpression={onImpression}
      onClick={onClick}
      hideBanner={hideBanner}
    />
  )
}

const RnStartIoBannerXml = requireNativeComponent(
  'RnStartIoBannerXml',
  BannerAd,
);

export default BannerAd
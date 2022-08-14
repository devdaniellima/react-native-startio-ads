
import { NativeModules } from 'react-native';
const { RNStartIo } = NativeModules;

export { default as BannerAd } from './BannerAd';

export const Types = RNStartIo.getConstants()

const RNStartIoAds = {
    loadInterstitial: RNStartIo.loadInterstitial,
    showInterstitial: RNStartIo.showInterstitial,
}

export default RNStartIoAds;

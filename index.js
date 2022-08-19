
import { NativeModules, NativeEventEmitter } from 'react-native';
const { RnStartIoModules } = NativeModules;

export { default as BannerAd } from './BannerAd';

const eventEmitter = new NativeEventEmitter(RnStartIoModules);
const addListener = (
  type: "rewarded" ,
  handler: Function) => {
  switch (type) {
    case "rewarded":
      eventEmitter.removeAllListeners('rewarded', handler);
      eventEmitter.addListener('rewarded', handler);
      break;
    default:
      break;
  }
}
const removeAllListeners = () => {
  eventEmitter.removeAllListeners('rewarded');
};

export const Types = RnStartIoModules.getConstants()

const initialize = ({
  appId = "",
  useReturnAds = false,
  testAds = false
}) => {
  RnStartIoModules.initialize(appId, useReturnAds, testAds);
}

const setUserConsent = (value = boolean) => {
  RnStartIoModules.setUserConsent(value);
}

const loadInterstitial = (type = Types.INTERSTITIAL_AUTOMATIC) => RnStartIoModules.loadInterstitial(type)

const RnStartIo = {
  initialize,
  setUserConsent,
  loadInterstitial,
  showInterstitial: RnStartIoModules.showInterstitial,
  loadRewarded: RnStartIoModules.loadRewarded,
  showRewarded: RnStartIoModules.showRewarded,
  addListener,
  removeAllListeners,
}

export default RnStartIo;

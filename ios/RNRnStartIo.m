#import "RNRnStartIo.h"
#import <React/RCTLog.h>

@implementation RnStartIoModules

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(initialize:
                  (NSString*)appId
                  useReturnAds:(BOOL)useReturnAds
                  testAds:(BOOL)testAds)
{
  RCTLogInfo(@"initialize %@", appId);
}

RCT_EXPORT_METHOD(loadInterstitial:(NSString*)type)
{
  RCTLogInfo(@"loadInterstitial");
}

RCT_EXPORT_METHOD(showInterstitial)
{
  RCTLogInfo(@"showInterstitial");
}

RCT_EXPORT_METHOD(loadRewarded)
{
  RCTLogInfo(@"loadRewarded");
}

RCT_EXPORT_METHOD(showRewarded)
{
  RCTLogInfo(@"showInterstitial");
}

@end

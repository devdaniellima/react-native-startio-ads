
# react-native-startio-ads

## Getting started

`$ npm install react-native-startio-ads --save`

### Mostly automatic installation

`$ react-native link react-native-startio-ads`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-startio-ads` and add `RNRnStartIo.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRnStartIo.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

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


## Usage
```javascript
import RNRnStartIo from 'react-native-startio-ads';

// TODO: What to do with the module?
RNRnStartIo;
```
  
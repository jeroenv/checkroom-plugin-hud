# ConfirmPanel Plugin for Cordova/PhoneGap 3.0 (iOS and Android)


## Installation

1) Make sure that you have [Node](http://nodejs.org/) and [Cordova CLI](https://github.com/apache/cordova-cli) or [PhoneGap's CLI](https://github.com/mwbrooks/phonegap-cli) installed on your machine.

2) Add a plugin to your project using Cordova CLI:

```bash
cordova plugin add https://francescoverheye@bitbucket.org/francescoverheye/checkroom-plugin-confirmpanel.git
```
Or using PhoneGap CLI:

```bash
phonegap local plugin add https://francescoverheye@bitbucket.org/francescoverheye/checkroom-plugin-confirmpanel.git
```

## Usage

```js
function showSuccessHud() {
    var text = 'success';
    var icon = 'success.png';
    var timeOut = 3000;
            
    var options = {
        text: text,
        icon: icon,
        timeOut: timeOut
    };
    hudPlugin.show(options);
}
function hideHud() {
    hudPlugin.hide();
}
function askVisibility(){
    hudPlugin.isShowing( 
        function(result){
            alert("result " + JSON.stringify(result));  
    });
}
function showErrorHud() {
    var icon = 'error.png';
    var timeOut = 0;
            
    var options = {
        icon: icon,
        timeOut: timeOut
    };

    hudPlugin.show(options);
}
function showLoadingHud() {
    var options = {};

    hudPlugin.show(options);

    var myVar = setTimeout(hideHud, 1000);
}
function showLoadingHud2() {
    var options = {};

    hudPlugin.show(options);

    var myVar = setTimeout(askVisibility, 1000);
    var myVar2 = setTimeout(hideHud, 5000);
}
```

## Options

### text - iOS, Android
The text on the HUD.

Type: String

Default: ``

### icon - iOS, Android
The image resource name (need to include '.png')

Type: String

Default: ``

Example: `success.png`

### timeOut - iOS, Android
Time out to close the HUD (in ms). 0 keeps the hud visible until hide is called.

Type: Integer

Default: `0`

## Requirements
- PhoneGap 3.0 or newer / Cordova 3.0 or newer
- Android 2.3.1 or newer / iOS 5 or newer


## How to add image to both platforms

### Android
- add the .png files in /platforms/android/res/drawable
- must be .png
- example used images with a 74x74 dimension

### iOS
1. open the project in Xcode
2. File - New - File
3. choose iOS Resource on the left
4. choose 'Asset Catalog' on the right, press next
5. Save as: 'Images'
6. save in /PROJECT_NAME/Resources
7. add your images (png files) to 'Imagex.xcassets'
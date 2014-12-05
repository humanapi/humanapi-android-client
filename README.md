# HumanAPI Android Client Sample for Eclipse

## Eclipse instructions

 - Install Eclipse 
 - Install Gradle 1.10 from here http://www.gradle.org/downloads
 - Download and install Android stand alone SDK - https://developer.android.com/sdk/installing/index.html?pkg=tools
 - Install Google Repository in the SDK manager:
 	- Install the ADK plugin http://developer.android.com/sdk/installing/installing-adt.html - follow full instructions
 	- Install sdk/platform tools/build-tools/sdk-platform (5.x ex)/google-apis/android-support-lib/google repo/Android support repo
 - git clone our sdk repo
 - gradle build
 
 ## ADT Eclipse sample instructions 
 - Compile this repo on an Eclipse (tested on Luna)
 - Select the project on Navigator -> Run -> Run As -> Android Application
 - This should load the app in a simulator and you can open the connect popup.
 - The popup needs to be configured with valid client_id/client_secret in android.app.Activity.HomeActivity  

 

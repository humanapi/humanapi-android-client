# HumanAPI Android Client

## What's inside

Overall folders description:

- `HumanAPI SDK`: Android client classes, can be used in other projects
- `app`: simple app which demonstrate client usage


HumanAPI consists of two parts placed into respective packages:

- `co.humanapi.client`: client classes used to access Data API (requires access_token)
- `co.humanapi.sdk`: browser based UI for Authorize and Connect user flows 

## How to start demo

- Download files
- Open project using Android Studio
- Run project (Ctrl+R)

## How to use in own project

- Copy `HumanAPI SDK` into your project
- Add `HumanAPI SDK` as dependency for your application

Code example can be found in `app/src/main/java/android/humanapi/co/demo/HomeActivity.java`

## Eclipse instructions
 - Install Eclipse 
 - Install Gradle 1.10 from here http://www.gradle.org/downloads
 - Download and install Android stand alone SDK - https://developer.android.com/sdk/installing/index.html?pkg=tools
 - Install Google Repository in the SDK manager:
 	- Install the ADK plugin http://developer.android.com/sdk/installing/installing-adt.html - follow full instructions
 	- Install sdk/platform tools/build-tools/sdk-platform (5.x ex)/google-apis/android-support-lib/google repo/Android support repo
 - git clone our sdk repo
 - gradle build
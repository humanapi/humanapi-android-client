# HumanAPI Android Client

> This client should only be used for the Wellness API if your application does not have a proper backend. It is not recommended for use with larger implementations or the Clinical API.

>See the `master` branch of this repo for most use cases.

## Eclipse Users!

- Refer to the branch https://github.com/humanapi/humanapi-android-client/tree/eclipse_version_adt_sample instead. The instructions below are for Android Studio users.

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


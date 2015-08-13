# Human API Android SDK
The purpose of this SDK is to handle the Human Connect popup to allow your users to easily authenticate their data within your application. Specifically, it handles the launch of and events from Human Connect, while passing on the `sessionTokenObject` to your server for secure token exchange.

### Assumptions
* Token exchange and data sync are performed on your server
* Your `clientSecret` and user `accessTokens` are not exposed to the client device

>If you are building a standalone application for wellness API data and do not have a backend database, refer to the client in the `wellnessDirect` branch of this repository.

>For security reasons, this is not recommended for large implementations or clinical use.

###Demo Project
You can find a demo implementation in the `androidStudioDemo` branch of this repository.

---

## How To Use SDK In Your Project

### 1. Import SDK Module
* Copy `humanapi-sdk` folder somewhere within your project's `app` folder
* Import `humanapi-sdk` as a module for your application
 * Go to `File -> New -> Import Module -> Source Directory` & Browse the project path for `humanapi-sdk` folder
 * Specify the Module Name as `:humanapi-sdk`
* Let Android Studio build the project.
* Open `build.gradle (Module:app)` file and add the following line in the dependencies block:
```
compile project(':humanapi-sdk')
```
*  Press the “sync now” link to start a sync of gradle files

### 2. Launch Human Connect
* Add a function to launch Connect along with:
 - `client_user_id`: Unique ID of there user on your system
 - `client_id`: Unique Id of your Human API application
 - `auth_url`: URL on your server to post `sessionTokenObject` and complete the token exchange process
 - `public_token`: Necessary token for existing users to launch Connect in "edit mode"
 - `language`: (Optional) language code [as detailed here](http://hub.humanapi.co/docs/customizing-human-connect#localization)

```java
  public void onConnect(View view) {
      Intent intent = new Intent(this, co.humanapi.connectsdk.ConnectActivity.class);
      Bundle b = new Bundle();

      b.putString("client_id", "<YOUR-CLIENT-ID>");

      /* URL on your server to handle token swap and finalize authentication: http://hub.humanapi.co/docs/integrating-human-connect#finalize-the-user-authentication */
      b.putString("auth_url", "http://10.0.2.2:3000/sessionToken");

      /* User identifier */
      b.putString("client_user_id", "test_user@gmail.com");

      /* PublicToken (mandatory for existing users)
      If not specified, Connect popup opens in "create" mode, otherwise it opens in "edit" mode */
      //b.putString("public_token", "e56fa0350866bcf266da442cb974d84e");

      /* Locale (optional) */
      //b.putString("language", "es");

      intent.putExtras(b);
      startActivityForResult(intent, HUMANAPI_AUTH);
  }
```
### 3. [Server-side] Finish Authentication Flow
   * Receive sessionTokenObject to previously specified `auth_url`
   * Sign it with `clientSecret` from the developer portal
   * POST signed `sessionTokenObject` from your server to Human API Tokens Endpoint:
   `https://user.humanapi.co/v1/connect/tokens`
   * Retrieve and store `humanId`, `accessToken` and `publicToken` on your server for use to query user data from Human API
   * Return status `200 OK` with payload containing `publicToken` to store on device for future launches of Human Connect:

   ```
   {
     publicToken: "2767d6oea95f4c3db8e8f3d0a1238302"
   }
   ```

   For more info, see the detailed guide here: (http://hub.humanapi.co/v1.0/docs/integrating-human-connect)

   Example server code in Node.js
   ```javascript
    //Endpoint for specified 'authURL'
    app.post('/sessionToken', function(req, res, next) {

      var sessionTokenObject = req.body;
      // grab client secret from app settings page and `sign` `sessionTokenObject` with it.
      sessionTokenObject.clientSecret = '<Your-Client-Secret>';

      request({
        method: 'POST',
        uri: 'https://user.humanapi.co/v1/connect/tokens',
        json: sessionTokenObject
      }, function(err, resp, body) {
          if(err) return res.send(422);

          console.log("Success!");
          // at this point if request was successful body object
          // will have `accessToken`, `publicToken` and `humanId` associated in it.
          // You will want to store these fields in your system in association to the user's data.
          console.log("humanId = " + body.humanId);
          console.log("accessToken = "+ body.accessToken);
          console.log("publicToken = "+ body.publicToken);

          //Send back publicToken to iOS app
          var responseJSON = {publicToken: body.publicToken}
          console.log(JSON.stringify(responseJSON));

          res.setHeader('Content-Type', 'application/json');
          res.status(201).send(JSON.stringify(responseJSON));
        });
    });
   ```

### 4.Implement Callback functions
 - `RESULT_OK`: Equivalent of the finish() callback. `public_token` from token exchange will be returned here.
 - `RESULT_CANCELED`: (optional) User closed Human Connect without connecting a source

```java
  /** Called when result returned */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode != HUMANAPI_AUTH) {
          return; // incorrect code
      }
      if (resultCode == RESULT_OK) {
          Log.d("hapi-home", "Authorization workflow completed");
          Bundle b = data.getExtras();
          Log.d("hapi-home", ".. public_token=" + b.getString("public_token"));


      } else if (resultCode == RESULT_CANCELED) {
          Log.d("hapi-home", "Authorization workflow cancelled");
      }
  }
  ```

package co.humanapi.connectsdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;


public class ConnectActivity extends Activity {

    private static final String BASE_URL = "https://connect.humanapi.co/";

    // Callbacks
    private static final String FINISH_CB = "https://hapi-finish";
    private static final String CLOSE_CB = "https://hapi-close";

    private WebView webView;

    // Auth parameters
    private String clientId;
    private String authURL;
    private String userId;
    private String publicToken;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        // Find WebView instance
        webView = (WebView) findViewById(R.id.connect_webview);
        this.fetchParameters();
        this.webSettings();
        this.webViewClient();
        // Enable JS alerts
        webView.setWebChromeClient(new WebChromeClient());

        // Load URL
        Uri.Builder ub = new Uri.Builder();
        try {
            URL base = new URL(BASE_URL);
            ub.scheme(base.getProtocol());
            ub.authority(base.getHost());
            ub.path("embed");
            ub.appendQueryParameter("client_id", clientId);
            ub.appendQueryParameter("client_user_id", userId);
            if (publicToken != null) {
                ub.appendQueryParameter("public_token", publicToken);
            }
            if (language != null) {
                ub.appendQueryParameter("language", language);
            }
            ub.appendQueryParameter("finish_url", FINISH_CB);
            ub.appendQueryParameter("close_url", CLOSE_CB);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String url = ub.build().toString();
        Log.d("hapi-auth", "Opening URL: " + url);
        webView.loadUrl(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /** Fetch params from caller */
    private void fetchParameters() {
        Bundle b = getIntent().getExtras();
        clientId = b.getString("client_id");
        authURL = b.getString("auth_url");
        Log.d("hapi-auth", "Auth URL: " + authURL);


        if (b.containsKey("client_user_id")) {
            userId = b.getString("client_user_id");
        }
        if (b.containsKey("public_token")) {
            publicToken = b.getString("public_token");
        }
        if (b.containsKey("language")) {
            language = b.getString("language");
        }
    }

    /** Adjust web settings */
    private void webSettings() {
        WebSettings s = webView.getSettings();
        s.setLoadWithOverviewMode(true);
        s.setLoadsImagesAutomatically(true);
        s.setUseWideViewPort(false);
        s.setJavaScriptEnabled(true);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
    }

    /** Initialize web view client */
    private void webViewClient() {
        // Force links and redirects to open in the WebView instead of in a browser
        final ConnectActivity self = this;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("hapi-auth", "loading: " + url);
                if (url.startsWith(FINISH_CB)) {
                    Log.d("hapi-auth", "Finish Callback");
                    self.onFinish(url);
                    return true;
                } else if (url.startsWith(CLOSE_CB)) {
                    Log.d("hapi-auth", "Close Callback");
                    self.onClose();
                    return true;
                } else if (!url.contains(BASE_URL) && !url.contains(FINISH_CB) && !url.contains(CLOSE_CB)){
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                } else {
                    return false;
                }
            }
        });
    }


    /* Callbacks */
    private void onFinish(String url) {
        Log.d("hapi-auth", "Finish callback started w/ url: " + url);
        Uri uri = Uri.parse(url);
        String humanId = uri.getQueryParameter("human_id");
        String sessionToken = uri.getQueryParameter("session_token");
        Log.d("hapi-auth", ".. found humanId=" + humanId);
        Log.d("hapi-auth", ".. found clientId=" + clientId);
        Log.d("hapi-auth", ".. found sessionToken=" + sessionToken);

        // TODO run in thread or async class
        // Allow IO in main thread
        if (android.os.Build.VERSION.SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        Resty resty = new Resty();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("clientId", clientId);
        data.put("humanId", humanId);
        data.put("sessionToken", sessionToken);
        Log.d("hapi-auth", "jsonObject=" + new JSONObject(data) );


        try {
            JSONResource res = resty.json(authURL, resty.content(new JSONObject(data)));
            Log.d("hapi-auth", "result = " + res.toString());
            Intent intent = new Intent();
            intent.putExtra("public_token", res.get("publicToken").toString());
            setResult(RESULT_OK, intent);
        } catch (Exception e) {
            Log.e("hapi-auth", e.toString());
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    private void onClose() {
        Log.d("hapi-auth", "Close callback started");

        setResult(RESULT_CANCELED);
        finish();
    }

}

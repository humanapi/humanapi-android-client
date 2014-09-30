package co.humanapi.sdk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import us.monoid.json.JSONObject;
import us.monoid.web.Resty;
import us.monoid.web.TextResource;


public class AuthActivity extends Activity {

    /** Connect base URL */
    // production
    private static final String BASE_URL = "https://connect.humanapi.co";
    // staging
    //private static final String BASE_URL = "http://hapi-connect-staging-ypdaxpct4r.elasticbeanstalk.com";

    // Tokens URL
    private static final String TOKENS_URL = "https://user.humanapi.co/v1/connect/tokens";

    // Callbacks
    private static final String FINISH_CB = "https://hapi-finish";
    private static final String CLOSE_CB = "https://hapi-close";

    /** Web view for Connect rendering */
    private WebView webView;

    // Auth parameters
    private String clientId;
    private String clientSecret;
    private String userId;
    private String publicToken;
    private String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Find WebView instance
        webView = (WebView) findViewById(R.id.auth_webview);
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

    /** Fetch params from caller */
    private void fetchParameters() {
        Bundle b = getIntent().getExtras();
        clientId = b.getString("client_id");
        clientSecret = b.getString("client_secret");
        if (b.containsKey("user_id")) {
            userId = b.getString("user_id");
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
        final AuthActivity self = this;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("hapi-auth", "loading: " + url);
                if (url.startsWith(FINISH_CB)) {
                    self.onFinishCb(url);
                    return true;
                } else if (url.startsWith(CLOSE_CB)) {
                    self.onCloseCb();
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    private void onFinishCb(String url) {
        Log.d("hapi-auth", "Finish callback started");
        Uri uri = Uri.parse(url);
        String humanId = uri.getQueryParameter("human_id");
        String sessionToken = uri.getQueryParameter("session_token");
        Log.d("hapi-auth", ".. found humanId=" + humanId);
        Log.d("hapi-auth", ".. found sessionToken=" + sessionToken);

        Resty resty = new Resty();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("clientId", clientId);
        data.put("clientSecret", clientSecret);
        data.put("humanId", humanId);
        data.put("sessionToken", sessionToken);
        try {
            TextResource res = resty.text(TOKENS_URL, resty.content(new JSONObject(data)));
            Log.d("hapi-auth", "result = " + res.toString());

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
        } catch (IOException e) {
            Log.e("hapi-auth", e.toString());
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    private void onCloseCb() {
        Log.d("hapi-auth", "Close callback started");
        setResult(RESULT_CANCELED);
        finish();
    }
}

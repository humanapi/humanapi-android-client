package android.humanapi.co.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.humanapi.client.HumanAPIClient;
import co.humanapi.client.HumanAPIException;


public class HomeActivity extends Activity {

    static final int HUMANAPI_AUTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks Connect button */
    public void onConnect(View view) {
        Log.d("hapi-home", "connect clicked!");
        Intent intent = new Intent(this, co.humanapi.sdk.AuthActivity.class);
        Bundle b = new Bundle();

        /* Production app test data : pre-assigned by service */
        b.putString("client_id", "9bac0e053f486619c0795015c99477b49b229961");
        b.putString("client_secret", "b20f0c6cb300e7f6cfef2bb240d3f48481094efe");

        /* Staging app test data : pre-assigned by service */
        //b.putString("client_id", "f141d32a2d4a4221d241e2cee493cf3c34d45699");
        //b.putString("client_secret", "");

        /* User identifier */
        b.putString("user_id", "and_user_3");

        /* Public token for existing user: if not specified Connect popup
            opened in "create" mode, otherwise in "edit" mode */
        //b.putString("public_token", "...");

        intent.putExtras(b);
        startActivityForResult(intent, HUMANAPI_AUTH);
    }

    /** Called when result returned */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != HUMANAPI_AUTH) {
            return; // incorrect code
        }
        if (resultCode == RESULT_OK) {
            Log.d("hapi-home", "Authorization workflow completed");
            Bundle b = data.getExtras();
            Log.d("hapi-home", ".. human_id=" + b.getString("human_id"));
            Log.d("hapi-home", ".. access_token=" + b.getString("access_token"));
            Log.d("hapi-home", ".. public_token=" + b.getString("public_token"));

            /* Test API */
            try {
                HumanAPIClient client = new HumanAPIClient(b.getString("access_token"));
//                JSONObject h = client.humanEntity().get();
//                Log.d("hapi-home", "Got Human: " + h.toString());
            } catch (HumanAPIException e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.d("hapi-home", "Authorization workflow cancelled");
        }
    }
}

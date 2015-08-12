package co.humanapi.androidstudiodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final int HUMANAPI_AUTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Called when the user clicks Connect button */
    public void onConnect(View view) {
        Log.d("hapi-home", "connect clicked!");
        Intent intent = new Intent(this, co.humanapi.connectsdk.ConnectActivity.class);
        Bundle b = new Bundle();

        b.putString("client_id", "659e9bd58ec4ee7fa01bc6b4627cb37e5c13ec21");

        /* URL on your server to handle token swap and finalize authentication: http://hub.humanapi.co/docs/integrating-human-connect#finalize-the-user-authentication */
        b.putString("auth_url", "http://10.0.2.2:3000/sessionToken");

        /* Unique User identifier on your system (will be returned with final Human API credentials) */
        b.putString("client_user_id", "test_user@test.com");

        /* PublicToken (mandatory for existing users)
        If not specified, Connect popup opens in "create" mode, otherwise it opens in "edit" mode */
        //b.putString("public_token", "e56fa0350866bcf266da442cb974d84e");

        /* Locale (optional) */
        //b.putString("language", "es");

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
            Log.d("hapi-home", ".. public_token=" + b.getString("public_token"));
            //Save publicToken to supply to HumanConnect on subsequent launches

        } else if (resultCode == RESULT_CANCELED) {
            Log.d("hapi-home", "Authorization workflow cancelled");
        }
    }
}

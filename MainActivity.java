package fi.metropolia.virtualharri;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This needs to happen when the broadcast is received (when the new activity launches)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }
}

class PowerConnectionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent microsoftTeamsIntent = context.getPackageManager().getLaunchIntentForPackage("com" + ".microsoft.teams");

        if (action.equals(Intent.ACTION_POWER_CONNECTED)) {
            Log.e("Charger State", "power connected");

        } else if (action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            Log.e("Charger State", "power disconnected");

            if (microsoftTeamsIntent != null) {

                microsoftTeamsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(microsoftTeamsIntent);
            } else {
                Toast.makeText(context, "Microsoft Teams couldn't be launched", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
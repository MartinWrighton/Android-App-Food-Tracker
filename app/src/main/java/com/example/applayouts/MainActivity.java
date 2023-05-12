package com.example.applayouts;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.applayouts.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    /* MAKE SURE YOU DO THE USER GUIDE
A: Two screens, basically done Very Easy 4%                                                                                 DONE
B: App lifecycle, onpause(), onStart(), onCreate() etc., remember entered information when tabbing out? Easy 3%
C: Permissions, permission to send notifications Might be easy 2%                                                           DONE
D: Intents, use to move between screens, easy 2%                                                                            DONE
E: Intents 2, use to open calendar? hopefully easy 2%
F: Content Provider, use it to access my own database, hard 6%
G: Shared Preferences, simple data storage, use to store user information, moderate 2%                                      DONE
H: Database, use to store history information, hard 6%
I: Firebase, for login? moderate 4%                                                                                         NO
J: Broadcasts, receives hydration alarm broadcast easy if used 2%                                                           DONE
K: Extend View, not sure how to use but is easy 2%
L: ShareActionProvider, used to share things online?, use to share streak. moderate 2%
M: Service, does something while the app isn't open, don't know how to use, 6%
N: Alarms, set hydration alarms, easy  2%                                                                                   DONE
O: Notification, send hydration notifications, easy 2%                                                                      DONE
P: Touch gestures, scrolling or swiping between screens, optional easy 3%

     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        //ALARM MANAGER modified version of https://www.javatpoint.com/android-alarmmanager
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            int i = 5;
            Intent intent = new Intent(this, notification.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + (i * 1000),60000, pendingIntent);

        }  else {
            // You can directly ask for the permission.
            String[] perms =  new String[] { Manifest.permission.POST_NOTIFICATIONS };
            requestPermissions(
                    perms,1
                    );
        }





    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int i = 5;
                    Intent intent = new Intent(this, notification.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                            + (i * 1000),60000, pendingIntent);
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_profile){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }  else if (id == R.id.action_calendar){
        Context context = getApplicationContext();
        CharSequence text = "Calendar!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

        return super.onOptionsItemSelected(item);
    }


    public void nutritionButtonPress(View v){
        Context context = getApplicationContext();
        CharSequence text = "Nutrition!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void hydrationButtonPress(View v){
        Context context = getApplicationContext();
        CharSequence text = "Hydration!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}
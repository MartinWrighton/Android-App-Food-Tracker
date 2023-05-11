package com.example.applayouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

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
C: Permissions, permission to set alarms? Might be easy 2%
D: Intents, use to move between screens, easy 2%                                                                            DONE
E: Intents 2, use to open calendar? hopefully easy 2%
F: Content Provider, provides local data for other apps, use it to send information to timer or calendar?, hard 6%
G: Shared Preferences, simple data storage, use to store user information, moderate 2%                                      DONE
H: Database, use to store history information, hard 6%
I: Firebase, for login? moderate 4%
J: Broadcasts, ACTION_BATTERY_LOW joke about feeding your phone too? easy if used 2%
K: Extend View, not sure how to use but is easy 2%
L: ShareActionProvider, used to share things online?, use to share streak. moderate 2%
M: Service, does something while the app isn't open, don't know how to use, 6%
N: Alarms, set hydration alarms, easy  2%
O: Notification, send hydration notifications, easy 2%
P: Touch gestures, scrolling or swiping between screens, optional easy 3%

     */
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
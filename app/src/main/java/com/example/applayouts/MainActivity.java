package com.example.applayouts;

import static java.lang.Integer.max;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.content.ContextCompat;

import androidx.navigation.ui.AppBarConfiguration;




import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
;
    /* MAKE SURE YOU DO THE USER GUIDE
A: Two screens, basically done Very Easy 4%                                                                                 DONE
B: App lifecycle, onResume()   Easy 3%                                                                                      DONE
C: Permissions, permission to send notifications Might be easy 2%                                                           DONE
D: Intents, use to move between screens, easy 2%                                                                            DONE
E: Intents 2, use to open calendar? hopefully easy 2%
F: Content Provider, use it to access my own database, hard 6%                                                              DONE
G: Shared Preferences, simple data storage, use to store user information, moderate 2%                                      DONE
H: Database, use to store history information, hard 6%                                                                      DONE
I: Firebase, for login? moderate 4%                                                                                         NO
J: Broadcasts, receives hydration alarm broadcast easy if used 2%                                                           DONE
K: Extend View, not sure how to use but is easy 2%
L: ShareActionProvider, used to share things online?, use to share streak. moderate 2%
M: Service, does something while the app isn't open, don't know how to use, 6%                                              NO
N: Alarms, set hydration alarms, easy  2%                                                                                   DONE
O: Notification, send hydration notifications, easy 2%                                                                      DONE
P: Touch gestures, scrolling or swiping between screens, optional easy 3%


    add first time setup
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

    updateBars();

    }

    @Override
    protected void onResume(){
        super.onResume();
        updateBars();

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

        if (id == R.id.action_profile){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }  else if (id == R.id.action_calendar){
            Intent intent = new Intent(this, calendar.class);
            startActivity(intent);
    }

        return super.onOptionsItemSelected(item);
    }


    public void nutritionButtonPress(View v){
        Intent intent = new Intent(this, add_nutrition.class);
        startActivity(intent);



        /*DATABASE TEST
        ContentValues values = new ContentValues();
        values.put("score",1);
        getContentResolver().insert(myProvider.uri, values);
        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();

         */
    }

    @SuppressLint("Range")
    public void hydrationButtonPress(View v){
        Intent intent = new Intent(this, add_hydration.class);
        startActivity(intent);



        /*DATABASE TEST
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.applayouts.provider/data"), null, null, null, null);
        if(cursor.moveToFirst()) {
            StringBuilder strBuild=new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+ "-"+ cursor.getString(cursor.getColumnIndex("score")));
                cursor.moveToNext();
            }
            Toast.makeText(getBaseContext(), strBuild, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getBaseContext(), "No Records", Toast.LENGTH_LONG).show();
        }

         */
    }

    private void updateBars(){
        //display data correctly
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        int currentCalories = sharedPreferences.getInt("currentCalories", 0);
        int currentFat = sharedPreferences.getInt("currentFat", 0);
        int currentSaturates = sharedPreferences.getInt("currentSaturates", 0);
        int currentSalt = sharedPreferences.getInt("currentSalt", 0);
        int currentSugar = sharedPreferences.getInt("currentSugar", 0);
        int currentWater = sharedPreferences.getInt("currentWater", 0);


        int maxCalories = sharedPreferences.getInt("calories", 0);
        int maxFat = sharedPreferences.getInt("fat", 0);
        int maxSaturates = sharedPreferences.getInt("saturates", 0);
        int maxSalt = sharedPreferences.getInt("salt", 0);
        int maxSugar = sharedPreferences.getInt("sugar", 0);
        int maxWater = sharedPreferences.getInt("water", 0);

        int caloriesPercent = (currentCalories*100) / max(1,maxCalories);
        int fatPercent = (currentFat*100) / max(1,maxFat);
        int saturatesPercent = (currentSaturates*100) / max(1,maxSaturates);
        int saltPercent = (currentSalt*100) / max(1,maxSalt);
        int sugarPercent = (currentSugar*100) / max(1,maxSugar);
        int waterPercent = (currentWater*100) / max(1,maxWater);

        ProgressBar calories = findViewById(R.id.caloriesBar);
        ProgressBar fat = findViewById(R.id.fatBar);
        ProgressBar saturates = findViewById(R.id.saturatesBar);
        ProgressBar salt = findViewById(R.id.saltBar);
        ProgressBar sugar = findViewById(R.id.sugarBar);
        TextView hydration = findViewById(R.id.hydrationAmount);

        calories.setProgress(caloriesPercent);
        fat.setProgress(fatPercent);
        saturates.setProgress(saturatesPercent);
        salt.setProgress(saltPercent);
        sugar.setProgress(sugarPercent);
        hydration.setText(Integer.toString(waterPercent)+"%");
        if (caloriesPercent < 70){
            calories.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        } else if (caloriesPercent < 99){
            calories.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#aaff00")));
        } else {
            calories.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ee4b2b")));
        }
        if (fatPercent < 70){
            fat.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        } else if (fatPercent < 99){
            fat.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#aaff00")));
        } else {
            fat.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ee4b2b")));
        }
        if (saturatesPercent < 70){
            saturates.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        } else if (saturatesPercent < 99){
            saturates.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#aaff00")));
        } else {
            saturates.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ee4b2b")));
        }
        if (saltPercent < 70){
            salt.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        } else if (saltPercent < 99){
            salt.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#aaff00")));
        } else {
            salt.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ee4b2b")));
        }
        if (sugarPercent < 70){
            sugar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffa500")));
        } else if (sugarPercent < 99){
            sugar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#aaff00")));
        } else {
            sugar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ee4b2b")));
        }
    }


}
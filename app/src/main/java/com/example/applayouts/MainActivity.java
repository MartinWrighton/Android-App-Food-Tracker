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

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.content.ContextCompat;

import androidx.core.view.MenuItemCompat;
import androidx.navigation.ui.AppBarConfiguration;




import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
;
    /* MAKE SURE YOU DO THE USER GUIDE
A: Two screens, basically done Very Easy 4%                                                                                 DONE
B: App lifecycle, onResume()   Easy 3%                                                                                      DONE
C: Permissions, permission to send notifications Might be easy 2%                                                           DONE
D: Intents, use to move between screens, easy 2%                                                                            DONE
E: Intents 2, use to open calendar? hopefully easy 2%                                                                       DONE
F: Content Provider, use it to access my own database, hard 6%                                                              DONE
G: Shared Preferences, simple data storage, use to store user information, moderate 2%                                      DONE
H: Database, use to store history information, hard 6%                                                                      DONE
I: Firebase, for login? moderate 4%                                                                                         NO
J: Broadcasts, receives hydration alarm broadcast easy if used 2%                                                           DONE
K: Extend View, not sure how to use but is easy 2%                                                                          NO
L: ShareActionProvider, used to share things online?, use to share streak. moderate 2%                                      DONE
M: Service, does something while the app isn't open, don't know how to use, 6%                                              NO
N: Alarms, set hydration alarms, easy  2%                                                                                   DONE
O: Notification, send hydration notifications, easy 2%                                                                      DONE
P: Touch gestures,swipe to fill hyrdation ,easy 3%                                                                          DONE



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
        //FIRST TIME SETUP

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        int currentDay = sharedPreferences.getInt("day", -1); // returns -1 if this is the first time the app was started
        if (currentDay == -1){
            for(int i = 0; i<28;i++){
                ContentValues values = new ContentValues();
                values.put("score",-1);
                getContentResolver().insert(myProvider.uri, values);
            }
            LocalDate date = LocalDate.now();

            String longdate = Integer.toString(date.getYear())+Integer.toString(date.getMonthValue())+Integer.toString(date.getDayOfMonth());

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putInt("day", 0);
            myEdit.putString("date", longdate);
            myEdit.apply();

            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);

        }

        //check for passed days

        LocalDate date = LocalDate.now();
        String longdate = Integer.toString(date.getYear())+Integer.toString(date.getMonthValue())+Integer.toString(date.getDayOfMonth());
        int daysPast = Integer.parseInt(longdate) - Integer.parseInt(sharedPreferences.getString("date", "0"));
        if (daysPast > 0){
            //NEW DAY;
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("date", longdate);
            for (int i = 0; i <daysPast;i++){
                newDay();
            }


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
                            + (i * 1000),10800000, pendingIntent);
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

        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider myShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        int score = sharedPreferences.getInt("highScore", -0);

        Intent myShareIntent = new Intent(Intent.ACTION_SEND);
        myShareIntent.setType("text/plain");
        myShareIntent.putExtra(Intent.EXTRA_STREAM, score);
        myShareActionProvider.setShareIntent(myShareIntent);

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
        Float currentCalories = sharedPreferences.getFloat("currentCalories", Float.valueOf(0));
        Float currentFat = sharedPreferences.getFloat("currentFat", Float.valueOf(0));
        Float currentSaturates = sharedPreferences.getFloat("currentSaturates", Float.valueOf(0));
        Float currentSalt = sharedPreferences.getFloat("currentSalt", Float.valueOf(0));
        Float currentSugar = sharedPreferences.getFloat("currentSugar", Float.valueOf(0));
        int currentWater = sharedPreferences.getInt("currentWater", 0);


        int maxCalories = sharedPreferences.getInt("calories", 0);
        int maxFat = sharedPreferences.getInt("fat", 0);
        int maxSaturates = sharedPreferences.getInt("saturates", 0);
        int maxSalt = sharedPreferences.getInt("salt", 0);
        int maxSugar = sharedPreferences.getInt("sugar", 0);
        int maxWater = sharedPreferences.getInt("water", 0);

        Float caloriesPercent = (currentCalories*100) / max(1,maxCalories);
        Float fatPercent = (currentFat*100) / max(1,maxFat);
        Float saturatesPercent = (currentSaturates*100) / max(1,maxSaturates);
        Float saltPercent = (currentSalt*100) / max(1,maxSalt);
        Float sugarPercent = (currentSugar*100) / max(1,maxSugar);
        int waterPercent = (currentWater*100) / max(1,maxWater);
        ProgressBar calories = findViewById(R.id.caloriesBar);
        ProgressBar fat = findViewById(R.id.fatBar);
        ProgressBar saturates = findViewById(R.id.saturatesBar);
        ProgressBar salt = findViewById(R.id.saltBar);
        ProgressBar sugar = findViewById(R.id.sugarBar);
        TextView hydration = findViewById(R.id.hydrationAmountMain);

        calories.setProgress(Math.round(caloriesPercent));
        fat.setProgress(Math.round(fatPercent));
        saturates.setProgress(Math.round(saturatesPercent));
        salt.setProgress(Math.round(saltPercent));
        sugar.setProgress(Math.round(sugarPercent));


        if(waterPercent>100){
            waterPercent=100;
        }
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



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        View box = findViewById(R.id.hydrationBoxMain);
        int maxHeight = height/3;
        Log.d("maxheight", String.valueOf(maxHeight));
        int newHeight = Math.round((waterPercent* maxHeight)/100);
        Log.d("newHeight", String.valueOf(newHeight));
        ViewGroup.LayoutParams params = box.getLayoutParams();
        params.height = Math.round(Math.max(newHeight,1));
        box.setLayoutParams(params);
    }

    public void newDay(){
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        Float currentCalories = sharedPreferences.getFloat("currentCalories", Float.valueOf(0));
        Float currentFat = sharedPreferences.getFloat("currentFat", Float.valueOf(0));
        Float currentSaturates = sharedPreferences.getFloat("currentSaturates", Float.valueOf(0));
        Float currentSalt = sharedPreferences.getFloat("currentSalt", Float.valueOf(0));
        Float currentSugar = sharedPreferences.getFloat("currentSugar", Float.valueOf(0));
        int currentWater = sharedPreferences.getInt("currentWater", 0);
        int currentDay = sharedPreferences.getInt("day", -1);

        int maxCalories = sharedPreferences.getInt("calories", 0);
        int maxFat = sharedPreferences.getInt("fat", 0);
        int maxSaturates = sharedPreferences.getInt("saturates", 0);
        int maxSalt = sharedPreferences.getInt("salt", 0);
        int maxSugar = sharedPreferences.getInt("sugar", 0);
        int maxWater = sharedPreferences.getInt("water", 0);

        Float caloriesPercent = (currentCalories*100) / max(1,maxCalories);
        Float fatPercent = (currentFat*100) / max(1,maxFat);
        Float saturatesPercent = (currentSaturates*100) / max(1,maxSaturates);
        Float saltPercent = (currentSalt*100) / max(1,maxSalt);
        Float sugarPercent = (currentSugar*100) / max(1,maxSugar);
        int waterPercent = (currentWater*100) / max(1,maxWater);

        //calculate score
        int score = 0;

        if (caloriesPercent >=70 && caloriesPercent < 100){
            score += 1;
        }
        if (fatPercent >=70 && caloriesPercent < 100){
            score += 1;
        }
        if (saturatesPercent >=70 && caloriesPercent < 100){
            score += 1;
        }
        if (saltPercent >=70 && caloriesPercent < 100){
            score += 1;
        }
        if (sugarPercent >=70 && caloriesPercent < 100){
            score += 1;
        }
        if (waterPercent >=70){
            score += 1;
        }
        if (score == 0){
            score = -1;
        }
        //add to database
        ContentValues values = new ContentValues();
        values.put("score",score);
        getContentResolver().insert(myProvider.uri, values);
        //reset values

        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("day", currentDay+1);


        myEdit.putFloat("currentCalories", 0);
        myEdit.putFloat("currentFat", 0);
        myEdit.putFloat("currentSaturates", 0);
        myEdit.putFloat("currentSalt", 0);
        myEdit.putFloat("currentSugar", 0);
        myEdit.putInt("currentWater", 0);
        myEdit.apply();
        updateBars();




    }


    public void toUserGuide(View view) {
        Intent intent = new Intent(this, userGuide.class);
        startActivity(intent);
    }
}
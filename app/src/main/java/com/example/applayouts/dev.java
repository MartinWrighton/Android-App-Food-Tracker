package com.example.applayouts;

import static java.lang.Integer.max;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.applayouts.databinding.ActivityDevBinding;

public class dev extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

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
            finish();
        }  else if (id == R.id.action_calendar){
            Intent intent = new Intent(this, calendar.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void fastNotifications(View view) {
        Intent intent = new Intent(this, notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (5 * 1000),60000, pendingIntent);
    }

    public void newDay(View v){
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





    }
}
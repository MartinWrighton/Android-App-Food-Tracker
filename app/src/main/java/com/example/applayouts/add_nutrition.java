package com.example.applayouts;

import static java.lang.Double.max;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class add_nutrition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nutrition);

        EditText calories = findViewById(R.id.editCalories);
        EditText fat = findViewById(R.id.editFat);
        EditText saturates = findViewById(R.id.editSaturates);
        EditText salt = findViewById(R.id.editSalt);
        EditText sugar = findViewById(R.id.editSugar);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateBoxes();
            }
        };

        calories.addTextChangedListener(watcher);
        fat.addTextChangedListener(watcher);
        saturates.addTextChangedListener(watcher);
        salt.addTextChangedListener(watcher);
        sugar.addTextChangedListener(watcher);


    }

    private void updateBoxes(){

        TextView caloriesP = findViewById(R.id.caloriesP);
        TextView fatP = findViewById(R.id.fatP);
        TextView saturatesP = findViewById(R.id.saturatesP);
        TextView saltP = findViewById(R.id.saltP);
        TextView sugarP = findViewById(R.id.sugarP);

        EditText calories = findViewById(R.id.editCalories);
        EditText fat = findViewById(R.id.editFat);
        EditText saturates = findViewById(R.id.editSaturates);
        EditText salt = findViewById(R.id.editSalt);
        EditText sugar = findViewById(R.id.editSugar);

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        int maxCalories = sharedPreferences.getInt("calories", 0);
        int maxFat = sharedPreferences.getInt("fat", 0);
        int maxSaturates = sharedPreferences.getInt("saturates", 0);
        int maxSalt = sharedPreferences.getInt("salt", 0);
        int maxSugar = sharedPreferences.getInt("sugar", 0);
        int maxWater = sharedPreferences.getInt("water", 0);



        double caloriesPer = (Math.round((Double.parseDouble("0"+String.valueOf(calories.getText())) * 100) / max(1,maxCalories)*100))/100;
        double fatPer = (Math.round((Double.parseDouble("0"+String.valueOf(fat.getText())) * 100) / max(1,maxFat)*100))/100;
        double saturatesPer = (Math.round((Double.parseDouble("0"+String.valueOf(saturates.getText())) * 100) / max(1,maxSaturates)*100))/100;
        double saltPer = (Math.round((Double.parseDouble("0"+String.valueOf(salt.getText())) * 100) / max(1,maxSalt)*100))/100;
        double sugarPer = (Math.round((Double.parseDouble("0"+String.valueOf(sugar.getText())) * 100) / max(1,maxSugar)*100))/100;
        caloriesP.setText(Double.toString(caloriesPer )+"%");
        fatP.setText(Double.toString(fatPer)+ "%");
        saturatesP.setText(Double.toString(saturatesPer) + "%");
        saltP.setText(Double.toString(saltPer)+ "%");
        sugarP.setText(Double.toString(sugarPer)+ "%");


        if (Double.parseDouble("0"+String.valueOf(fat.getText())) <= 3 ){
            findViewById(R.id.fatBox).setBackgroundColor(Color.parseColor("#aaff00"));
        } else if (Double.parseDouble("0"+String.valueOf(fat.getText())) <= 17.5 ) {
            findViewById(R.id.fatBox).setBackgroundColor(Color.parseColor("#ffa500"));
        } else {
            findViewById(R.id.fatBox).setBackgroundColor(Color.parseColor("#ee4b2b"));
        }

        if (Double.parseDouble("0"+String.valueOf(saturates.getText())) <= 1.5 ){
            findViewById(R.id.saturatesBox).setBackgroundColor(Color.parseColor("#aaff00"));
        } else if (Double.parseDouble("0"+String.valueOf(saturates.getText())) <= 5 ) {
            findViewById(R.id.saturatesBox).setBackgroundColor(Color.parseColor("#ffa500"));
        } else {
            findViewById(R.id.saturatesBox).setBackgroundColor(Color.parseColor("#ee4b2b"));
        }

        if (Double.parseDouble("0"+String.valueOf(salt.getText())) <= 0.3 ){
            findViewById(R.id.saltBox).setBackgroundColor(Color.parseColor("#aaff00"));
        } else if (Double.parseDouble("0"+String.valueOf(salt.getText())) <= 1.5 ) {
            findViewById(R.id.saltBox).setBackgroundColor(Color.parseColor("#ffa500"));
        } else {
            findViewById(R.id.saltBox).setBackgroundColor(Color.parseColor("#ee4b2b"));
        }

        if (Double.parseDouble("0"+String.valueOf(sugar.getText())) <= 5 ){
            findViewById(R.id.sugarBox).setBackgroundColor(Color.parseColor("#aaff00"));
        } else if (Double.parseDouble("0"+String.valueOf(sugar.getText())) <= 22.5 ) {
            findViewById(R.id.sugarBox).setBackgroundColor(Color.parseColor("#ffa500"));
        } else {
            findViewById(R.id.sugarBox).setBackgroundColor(Color.parseColor("#ee4b2b"));
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
            finish();
        }  else if (id == R.id.action_calendar){
            Intent intent = new Intent(this, calendar.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSave(View v){
        EditText calories = findViewById(R.id.editCalories);
        EditText fat = findViewById(R.id.editFat);
        EditText saturates = findViewById(R.id.editSaturates);
        EditText salt = findViewById(R.id.editSalt);
        EditText sugar = findViewById(R.id.editSugar);

        EditText portions = findViewById(R.id.editPortions);

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        float cCalories = sharedPreferences.getInt("currentCalories", 0);
        float cFat = sharedPreferences.getInt("currentFat", 0);
        float cSaturates = sharedPreferences.getInt("currentSaturates", 0);
        float cSalt = sharedPreferences.getFloat("currentSalt", 0);
        float cSugar = sharedPreferences.getInt("currentSugar", 0);


        myEdit.putFloat("currentCalories", cCalories+Float.parseFloat("0"+calories.getText().toString())*Float.parseFloat("0"+portions.getText().toString()));
        myEdit.putFloat("currentFat", cFat+Float.parseFloat("0"+fat.getText().toString())*Float.parseFloat("0"+portions.getText().toString()));
        myEdit.putFloat("currentSaturates", cSaturates+Float.parseFloat("0"+saturates.getText().toString())*Float.parseFloat("0"+portions.getText().toString()));
        myEdit.putFloat("currentSalt", cSalt+Float.parseFloat("0"+salt.getText().toString())*Float.parseFloat("0"+portions.getText().toString()));
        myEdit.putFloat("currentSugar", cSugar+Float.parseFloat("0"+sugar.getText().toString())*Float.parseFloat("0"+portions.getText().toString()));
        myEdit.apply();
        finish();



    }



}
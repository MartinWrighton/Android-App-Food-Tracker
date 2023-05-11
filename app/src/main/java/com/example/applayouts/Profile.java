package com.example.applayouts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.applayouts.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        EditText age = (EditText) findViewById(R.id.editTextAge);
        age.setText(Integer.toString(sharedPreferences.getInt("age", 0)));
        Spinner gender = findViewById(R.id.spinnerGender);
        switch (sharedPreferences.getString("gender", "Male")) {
            case "Male":
                gender.setSelection(0);
                break;
            case "Female":
                gender.setSelection(1);
                break;
            default:
               gender.setSelection(2);
        }
        EditText weight = findViewById(R.id.editTextWeight);
        weight.setText(Integer.toString(sharedPreferences.getInt("weight", 0)));
        EditText height = findViewById(R.id.editTextHeight);
        height.setText(Integer.toString(sharedPreferences.getInt("height", 0)));
        EditText calories = findViewById(R.id.editTextCalories);
        calories.setText(Integer.toString(sharedPreferences.getInt("calories", 0)));
        EditText fat = findViewById(R.id.editTextFat);
        fat.setText(Integer.toString(sharedPreferences.getInt("fat", 0)));
        EditText saturates = findViewById(R.id.editTextSaturates);
        saturates.setText(Integer.toString(sharedPreferences.getInt("saturates", 0)));
        EditText salt = findViewById(R.id.editTextSalt);
        salt.setText(Integer.toString(sharedPreferences.getInt("salt", 0)));
        EditText sugar = findViewById(R.id.editTextSugar);
        sugar.setText(Integer.toString(sharedPreferences.getInt("sugar", 0)));
        EditText water = findViewById(R.id.editTextWater);
        water.setText(Integer.toString(sharedPreferences.getInt("water", 0)));
    }

    public void save(View v){
        EditText age = findViewById(R.id.editTextAge);
        Spinner gender = findViewById(R.id.spinnerGender);
        EditText weight = findViewById(R.id.editTextWeight);
        EditText height = findViewById(R.id.editTextHeight);
        EditText calories = findViewById(R.id.editTextCalories);
        EditText fat = findViewById(R.id.editTextFat);
        EditText saturates = findViewById(R.id.editTextSaturates);
        EditText salt = findViewById(R.id.editTextSalt);
        EditText sugar = findViewById(R.id.editTextSugar);
        EditText water = findViewById(R.id.editTextWater);
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
        myEdit.putString("gender", gender.getSelectedItem().toString());
        myEdit.putInt("weight", Integer.parseInt(weight.getText().toString()));
        myEdit.putInt("height", Integer.parseInt(height.getText().toString()));
        myEdit.putInt("calories", Integer.parseInt(calories.getText().toString()));
        myEdit.putInt("fat", Integer.parseInt(fat.getText().toString()));
        myEdit.putInt("saturates", Integer.parseInt(saturates.getText().toString()));
        myEdit.putInt("salt", Integer.parseInt(salt.getText().toString()));
        myEdit.putInt("sugar", Integer.parseInt(sugar.getText().toString()));
        myEdit.putInt("water", Integer.parseInt(water.getText().toString()));
        myEdit.apply();
    }


    public void saveButton(View v){
        save(v);
        finish();
    }


    public void calcButton(View v) {
        save(v);
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        int age = sharedPreferences.getInt("age", 0);
        String gender = sharedPreferences.getString("gender", "Other");
        int weight = sharedPreferences.getInt("weight", 0);
        int height = sharedPreferences.getInt("height", 0);
        //Calories
        int calories = 0;

        if (age <= 3){
               calories = 80*weight;
           } else if (age <= 5) {
               calories = 70*weight;
           } else if (age <= 8){
               calories = 60*weight;
           } else if (age <=13) {
               calories = 35*weight;
           } else {
            if (gender.length() == 4) {
                calories = 2500;
            } else {
                calories = 2000;
            }
           }


        //Fat
        int fat = 0;
        fat = ((calories/3)/9);

        //Saturates
        int saturates = 0;
        saturates = ((calories/10)/9);

        //Salt
        int salt = 0;
        if (age <= 3){
            salt = 2;
        } else if (age <= 6){
            salt = 3;
        } else if (age <= 10){
            salt = 5;
        } else {
            salt = 6;
        }

        //Sugar
        int sugar = 0;
        sugar = calories/28;

        //Water
        int water = 0;
        if (gender.length() == 4){
            water = 2500;
        } else {
            water = 2000;
        }

        //Set Values
        EditText caloriesB = findViewById(R.id.editTextCalories);
        caloriesB.setText(Integer.toString(calories));
        EditText fatB = findViewById(R.id.editTextFat);
        fatB.setText(Integer.toString(fat));
        EditText saturatesB = findViewById(R.id.editTextSaturates);
        saturatesB.setText(Integer.toString(saturates));
        EditText saltB = findViewById(R.id.editTextSalt);
        saltB.setText(Integer.toString(salt));
        EditText sugarB = findViewById(R.id.editTextSugar);
        sugarB.setText(Integer.toString(sugar));
        EditText waterB = findViewById(R.id.editTextWater);
        waterB.setText(Integer.toString(water));



    }
}
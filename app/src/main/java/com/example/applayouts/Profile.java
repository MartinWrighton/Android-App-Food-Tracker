package com.example.applayouts;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

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
    }



    public void saveButton(View v){
        EditText age = findViewById(R.id.editTextAge);
        Spinner gender = findViewById(R.id.spinnerGender);
        EditText weight = findViewById(R.id.editTextWeight);
        EditText height = findViewById(R.id.editTextHeight);
        // Creating a shared pref object with a file name "MySharedPref" in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));
        myEdit.putString("gender", gender.getSelectedItem().toString());
        myEdit.putInt("weight", Integer.parseInt(weight.getText().toString()));
        myEdit.putInt("height", Integer.parseInt(height.getText().toString()));
        myEdit.apply();
        finish();
    }
}
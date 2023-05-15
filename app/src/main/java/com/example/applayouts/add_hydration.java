package com.example.applayouts;

import static java.lang.Math.max;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.view.MotionEventCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class add_hydration extends AppCompatActivity {
    private float currentY = 0;
    private float newY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hydration);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                currentY = event.getY();
                break;
            case (MotionEvent.ACTION_MOVE):

                newY = event.getY();
                float change = (currentY-newY);
                TextView text = findViewById(R.id.hydrationAmount);
                String textString = "0"+ ((String) text.getText());
                float currentml = Integer.parseInt(textString.substring(1, textString.length() - 2));

                int newValue = Math.round(change+currentml);
                if (newValue <0){
                    newValue = 0;
                }



                text.setText(String.valueOf(newValue)+"ml");


                View box = findViewById(R.id.hydrationBox);
                ViewGroup.LayoutParams params = box.getLayoutParams();
                params.height = Math.round(max(newValue,1));
                box.setLayoutParams(params);



                currentY = event.getY();
                break;


        }
        return super.onTouchEvent(event);
    }

    public void saveButton(View view) {
        View water = findViewById(R.id.hydrationAmount);

        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        int cWater = sharedPreferences.getInt("currentWater", -1);
        TextView text = findViewById(R.id.hydrationAmount);
        String textString = "0"+ ((String) text.getText());
        float currentml = Integer.parseInt(textString.substring(1, textString.length() - 2));
        int newWater = (int) (cWater+currentml);
        myEdit.putInt("currentWater", newWater);
        myEdit.apply();
        finish();
    }
}
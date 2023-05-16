package com.example.applayouts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class calendar extends AppCompatActivity {



    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);





        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        int currentDay = sharedPreferences.getInt("day", -1);
        int score = 0;
        int streak = 0;
        int[] scoreList = new int[28];
        int count = 0;
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.applayouts.provider/data"), null, null, null, "id");
        if(cursor.moveToFirst()) {
            StringBuilder strBuild=new StringBuilder();
            while (!cursor.isAfterLast()) {
                if (cursor.getInt(cursor.getColumnIndex("id"))>(currentDay)) {//if the selected record is within 28 days of today

                    score += cursor.getInt(cursor.getColumnIndex("score")); //add it to score
                    if (cursor.getInt(cursor.getColumnIndex("score")) == -1){//calculate streak
                        streak = 0;
                    } else {
                        streak += 1;
                    }
                    scoreList[count] = cursor.getInt(cursor.getColumnIndex("score"));
                    count+=1;
                }
                cursor.moveToNext();
            }

            if (sharedPreferences.getInt("highScore", -0)<score){//update highscore
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putInt("highScore", score);
                myEdit.apply();
            }

            //colour Boxes and fill out text
            int[] boxID = {R.id.day1,R.id.day2,R.id.day3,R.id.day4,R.id.day5,R.id.day6,R.id.day7,R.id.day8,R.id.day9,R.id.day10,R.id.day11,R.id.day12,R.id.day13,R.id.day14,R.id.day15,R.id.day16,R.id.day17,R.id.day18,R.id.day19,R.id.day20,R.id.day21,R.id.day22,R.id.day23,R.id.day24,R.id.day25,R.id.day26,R.id.day27,R.id.day28};
            int[] labelID = {R.id.dayScore1,R.id.dayScore2,R.id.dayScore3,R.id.dayScore4,R.id.dayScore5,R.id.dayScore6,R.id.dayScore7,R.id.dayScore8,R.id.dayScore9,R.id.dayScore10,R.id.dayScore11,R.id.dayScore12,R.id.dayScore13,R.id.dayScore14,R.id.dayScore15,R.id.dayScore16,R.id.dayScore17,R.id.dayScore18,R.id.dayScore19,R.id.dayScore20,R.id.dayScore21,R.id.dayScore22,R.id.dayScore23,R.id.dayScore24,R.id.dayScore25,R.id.dayScore26,R.id.dayScore27,R.id.dayScore28};

            for (int i = 0; i<28;i++){
                TextView label = findViewById(labelID[i]);
                label.setText(Integer.toString(scoreList[i]));
                View box = findViewById(boxID[i]);
                if (scoreList[i]<0){

                } else if (scoreList[i]<4){
                    box.setBackgroundColor(Color.parseColor("#ee4b2b"));
                } else if (scoreList[i]<6){
                    box.setBackgroundColor(Color.parseColor("#ffa500"));
                } else {
                    box.setBackgroundColor(Color.parseColor("#aaff00"));
                }

            }

            TextView streakL = findViewById(R.id.streakValue);
            streakL.setText(Integer.toString(streak));
            TextView scoreL = findViewById(R.id.scoreValue);
            scoreL.setText(Integer.toString(score));
            TextView highL = findViewById(R.id.highscoreValue);
            highL.setText(Integer.toString(sharedPreferences.getInt("highScore", -0)));


        }
        else {
            Toast.makeText(getBaseContext(), "No Records", Toast.LENGTH_LONG).show();
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

    public void toCalendar(View v){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("title", "New Meal");
        intent.putExtra("description", "Plan your meal here");
        intent.putExtra("beginTime", System.currentTimeMillis());
        intent.putExtra("endTime", System.currentTimeMillis());
        startActivity(intent);
    }
}
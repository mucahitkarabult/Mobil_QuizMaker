package com.mucahitkarabult.multiple_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ExamSettings extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText scoreText,diffucultyText,timeText;
    String pretime,prediff,prescore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_settings);
        scoreText=findViewById(R.id.scoreText);
        diffucultyText=findViewById(R.id.diffucltyText);
        timeText=findViewById(R.id.timeText);
        sharedPreferences=this.getSharedPreferences("com.mucahitkarabult.examproject", Context.MODE_PRIVATE);

        pretime=sharedPreferences.getString("time","");
        prediff=sharedPreferences.getString("diff","");
        prescore=sharedPreferences.getString("score","");
        putData();

    }
    public void SaveSettings(View view){
        String time,diffulcty,score;
        time=timeText.getText().toString();
        diffulcty=diffucultyText.getText().toString();
        score=scoreText.getText().toString();
        if(time.isEmpty()||diffulcty.isEmpty()||score.isEmpty()){
            Toast.makeText(ExamSettings.this,"Field cannot be empty",Toast.LENGTH_SHORT).show();
        }
        else{
            sharedPreferences.edit().putString("time",time).apply();
            sharedPreferences.edit().putString("diff",diffulcty).apply();
            sharedPreferences.edit().putString("score",score).apply();
            Toast.makeText(ExamSettings.this,"Change Saved",Toast.LENGTH_SHORT).show();
        }

    }
    public void putData(){
        if(!(pretime.isEmpty()||prediff.isEmpty()||prescore.isEmpty())){
            timeText.setText(pretime);
            diffucultyText.setText(prediff);
            scoreText.setText(prescore);
        }
    }
}
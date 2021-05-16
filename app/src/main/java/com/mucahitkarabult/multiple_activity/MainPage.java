package com.mucahitkarabult.multiple_activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainPage extends AppCompatActivity {
    ImageView addquestion,viewquestion,makeexam,examsetting;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toast.makeText(MainPage.this,"Succeful Login",Toast.LENGTH_LONG).show();
        addquestion=findViewById(R.id.addImage);
        viewquestion=findViewById(R.id.listImage);
        makeexam=findViewById(R.id.examImage);
        examsetting=findViewById(R.id.examsettingImage);
        Intent intent=getIntent();
        mail=intent.getStringExtra("mail");

    }

    public void addQuestions(View view){
        Intent intent =new Intent(MainPage.this,Addquestion.class);
        intent.putExtra("mail",mail);
        startActivity(intent);


    }
    public void listQuestions(View view){
        Intent intent =new Intent(MainPage.this,ShowQuestions.class);
        intent.putExtra("mail",mail);
        startActivity(intent);

    }

    public void makeExam(View view){
        Intent intent =new Intent(MainPage.this,MakeExam.class);
        intent.putExtra("mail",mail);
        startActivity(intent);


    }

    public void examSettings(View view){
        Intent intent =new Intent(MainPage.this,ExamSettings.class);
        intent.putExtra("mail",mail);
        startActivity(intent);


    }
}
package com.mucahitkarabult.multiple_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MakeExam extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText scoreText,diffucultyText,timeText;
    String pretime,prediff,prescore,mail;
    ArrayList<String> userQuestion;
    ArrayList<String> userAnswerA;
    ArrayList<String> userAnswerB;
    ArrayList<String> userAnswerC;
    ArrayList<String> userAnswerD;
    ArrayList<String> userAnswerE;
    ArrayList<String> userAnswerCo;
    ArrayList<String> id;
    MakeRecylerAdapter makeRecylerAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_exam);
        sharedPreferences=this.getSharedPreferences("com.mucahitkarabult.examproject", Context.MODE_PRIVATE);
        scoreText=findViewById(R.id.MakeScore);
        diffucultyText=findViewById(R.id.MakeDiff);
        timeText=findViewById(R.id.MakeTime);
        pretime=sharedPreferences.getString("time","");
        prediff=sharedPreferences.getString("diff","");
        prescore=sharedPreferences.getString("score","");

        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");
        userQuestion = new ArrayList<>();
        userAnswerA = new ArrayList<>();
        userAnswerB = new ArrayList<>();
        userAnswerC = new ArrayList<>();
        userAnswerD = new ArrayList<>();
        userAnswerE = new ArrayList<>();
        userAnswerCo = new ArrayList<>();
        id=new ArrayList<>();

        putData();

        getData();

        RecyclerView recyclerView = findViewById(R.id.Recyc);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        makeRecylerAdapter = new MakeRecylerAdapter(userQuestion, userAnswerA, userAnswerB, userAnswerC, userAnswerD, userAnswerE, userAnswerCo,id);
        recyclerView.setAdapter(makeRecylerAdapter);
    }

    private void putData() {
        if(!(pretime.isEmpty()||prediff.isEmpty()||prescore.isEmpty())){
            timeText.setText(pretime);
            diffucultyText.setText(prediff);
            scoreText.setText(prescore);
        }
    }
    public void getData() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Question", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS questions (id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");
            String select = "SELECT * FROM  questions " + " WHERE " + "mail" + " = ?";
            Cursor cursor = database.rawQuery(select, new String[]{mail});
            int idIx=cursor.getColumnIndex("id");
            int queIx = cursor.getColumnIndex("question");
            int AIx = cursor.getColumnIndex("aoption");
            int BIx = cursor.getColumnIndex("boption");
            int CIx = cursor.getColumnIndex("coption");
            int DIx = cursor.getColumnIndex("doption");
            int EIx = cursor.getColumnIndex("eoption");
            int CoIx = cursor.getColumnIndex("answer");

            while (cursor.moveToNext()) {
                id.add(String.valueOf(cursor.getInt(idIx)));
                userQuestion.add(cursor.getString(queIx));
                userAnswerA.add(cursor.getString(AIx));
                userAnswerB.add(cursor.getString(BIx));
                userAnswerC.add(cursor.getString(CIx));
                userAnswerD.add(cursor.getString(DIx));
                userAnswerE.add(cursor.getString(EIx));
                userAnswerCo.add(cursor.getString(CoIx)+" Sikki dogru cevap");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
package com.mucahitkarabult.multiple_activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class ShowQuestions extends AppCompatActivity {
    ArrayList<String> userQuestion;
    ArrayList<String> userAnswerA;
    ArrayList<String> userAnswerB;
    ArrayList<String> userAnswerC;
    ArrayList<String> userAnswerD;
    ArrayList<String> userAnswerE;
    ArrayList<String> userAnswerCo;
    ArrayList<String> id;
    String mail;
    ReceylerAdapter receylerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);
        userQuestion = new ArrayList<>();
        userAnswerA = new ArrayList<>();
        userAnswerB = new ArrayList<>();
        userAnswerC = new ArrayList<>();
        userAnswerD = new ArrayList<>();
        userAnswerE = new ArrayList<>();
        userAnswerCo = new ArrayList<>();
        id=new ArrayList<>();
        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");
        getData();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        receylerAdapter = new ReceylerAdapter(userQuestion, userAnswerA, userAnswerB, userAnswerC, userAnswerD, userAnswerE, userAnswerCo,id);
        recyclerView.setAdapter(receylerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receylerAdapter.notifyDataSetChanged();
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
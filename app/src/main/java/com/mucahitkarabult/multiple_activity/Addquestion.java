package com.mucahitkarabult.multiple_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Addquestion extends AppCompatActivity {
    String mail;
    SQLiteDatabase database;
    EditText questText, aOptionText, bOptionText, cOptionText, dOptionText, eOptionText, correctOptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquestion);
        questText = findViewById(R.id.UquestionText);
        aOptionText = findViewById(R.id.UAText);
        bOptionText = findViewById(R.id.UBText);
        cOptionText = findViewById(R.id.CText);
        dOptionText = findViewById(R.id.DText);
        eOptionText = findViewById(R.id.EText);
        correctOptionText = findViewById(R.id.correctText);


        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");
        Toast.makeText(Addquestion.this, mail, Toast.LENGTH_SHORT).show();


    }

    public void saveQuestion(View view) {
        String ques = questText.getText().toString();
        String A = aOptionText.getText().toString(), B = bOptionText.getText().toString(), C = cOptionText.getText().toString(), D = dOptionText.getText().toString(), E = eOptionText.getText().toString();
        String Co = correctOptionText.getText().toString();
        if (ques.isEmpty() || A.isEmpty() || B.isEmpty() || C.isEmpty() || D.isEmpty() || E.isEmpty() || Co.isEmpty()) {
            Toast.makeText(Addquestion.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
        } else {
            database = this.openOrCreateDatabase("Question", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS questions (id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");
            try {
                String sqlString = "INSERT INTO questions(question,aoption,boption,coption,doption,eoption,answer,mail) VALUES (?, ?, ?, ?,?,?,?,?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                sqLiteStatement.bindString(1, ques);
                sqLiteStatement.bindString(2, A);
                sqLiteStatement.bindString(3, B);
                sqLiteStatement.bindString(4, C);
                sqLiteStatement.bindString(5, D);
                sqLiteStatement.bindString(6, E);
                sqLiteStatement.bindString(7, Co);
                sqLiteStatement.bindString(8, mail);
                sqLiteStatement.execute();
                questText.setText("");
                aOptionText.setText("");
                bOptionText.setText("");
                cOptionText.setText("");
                dOptionText.setText("");
                eOptionText.setText("");
                correctOptionText.setText("");
                Toast.makeText(Addquestion.this, "Question is added", Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
}
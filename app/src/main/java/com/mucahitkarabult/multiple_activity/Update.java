package com.mucahitkarabult.multiple_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Update extends AppCompatActivity {
    Intent intent;
    private EditText A;
    private EditText B;
    private EditText C;
    private EditText D;
    private EditText E;
    private EditText Q;
    private EditText Co;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        intent=getIntent();
        id=intent.getStringExtra("id");
        A=findViewById(R.id.UAText);
        B=findViewById(R.id.UBText);
        C=findViewById(R.id.UCText);
        D=findViewById(R.id.UDText);
        E=findViewById(R.id.UEText);
        Q=findViewById(R.id.UquestionText);
        Co=findViewById(R.id.correctText);
        getData();
    }
    public void getData() {

        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Question", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS questions (id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");
            String select = "SELECT * FROM  questions " + " WHERE " + "id" + " = ?";
            Cursor cursor = database.rawQuery(select, new String[]{id});
            int idIx=cursor.getColumnIndex("id");
            int queIx = cursor.getColumnIndex("question");
            int AIx = cursor.getColumnIndex("aoption");
            int BIx = cursor.getColumnIndex("boption");
            int CIx = cursor.getColumnIndex("coption");
            int DIx = cursor.getColumnIndex("doption");
            int EIx = cursor.getColumnIndex("eoption");
            int CoIx = cursor.getColumnIndex("answer");

            while (cursor.moveToNext()) {

                Q.setText(cursor.getString(queIx));
                A.setText(cursor.getString(AIx));
                B.setText(cursor.getString(BIx));
                C.setText(cursor.getString(CIx));
                D.setText(cursor.getString(DIx));
                E.setText(cursor.getString(EIx));
                Co.setText(cursor.getString(CoIx));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public  void Uppdate(View view){



        try {
            SQLiteDatabase database = this.openOrCreateDatabase("Question", MODE_PRIVATE, null);
            database.execSQL("CREATE TABLE IF NOT EXISTS questions (id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");

            String sqlString = " UPDATE questions set question=?,aoption=?,boption=?,coption=?,doption=?,eoption=?,answer=? where id=?";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1, Q.getText().toString());
            sqLiteStatement.bindString(2, A.getText().toString());
            sqLiteStatement.bindString(3, B.getText().toString());
            sqLiteStatement.bindString(4, C.getText().toString());
            sqLiteStatement.bindString(5, D.getText().toString());
            sqLiteStatement.bindString(6, E.getText().toString());
            sqLiteStatement.bindString(7, Co.getText().toString());
            sqLiteStatement.bindString(8, id);
            sqLiteStatement.execute();
            Toast.makeText(Update.this, "Question Changed", Toast.LENGTH_SHORT).show();


        }
        catch (Exception e){
            System.out.println(e);
        }

    }

}
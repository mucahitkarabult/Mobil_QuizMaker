package com.mucahitkarabult.multiple_activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int count = 0;
    int flag=0;
    EditText emailText;
    EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailText = findViewById(R.id.LmailText);
        passText = findViewById(R.id.LpasswordText);
        count = 0;
    }

    public void Login(View view) {

        String mail = emailText.getText().toString();
        String pA = passText.getText().toString();
        if (mail.isEmpty() || pA.isEmpty()) {
            Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            count++;
        } else if (count < 2) {
            try {
                flag=0;
                count++;
                SQLiteDatabase database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

                database.execSQL("CREATE TABLE IF NOT EXISTS userinformation (name VARCHAR,surname VARCHAR,email VARCHAR PRIMARY KEY ,password VARCHAR,phonenumber VARCHAR, image BLOB)");

                String select = "SELECT * FROM  userinformation " + " WHERE " + "email" + " = ? AND " + "password" + " = ?";
                Cursor cursor = database.rawQuery(select, new String[]{mail, pA});
                int mailIx = cursor.getColumnIndex("email");
                int passIx = cursor.getColumnIndex("password");
                while (cursor.moveToNext()) {

                    String truemail = cursor.getString(mailIx);
                    String truepass = cursor.getString(passIx);
                    if (truemail.matches(mail) && truepass.matches(pA)) {
                        flag=1;
                        Intent intent = new Intent(MainActivity.this, MainPage.class);
                        intent.putExtra("mail", mail);
                        startActivity(intent);
                        finish();
                    }

                }
                cursor.close();
                if(flag==0) {
                    Toast.makeText(MainActivity.this, "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {


            }


        } else {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
            finish();

        }
    }

    public void signup(View view) {


        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
        finish();

    }
}
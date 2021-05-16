package com.mucahitkarabult.multiple_activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {
    Bitmap selectedImage;
    ImageView imageView;
    EditText nameText, surnameText, mailText, passwordText, re_passwordText, phoneText;
    Button button;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        imageView = findViewById(R.id.imageView);
        nameText = findViewById(R.id.nameText);
        surnameText = findViewById(R.id.surnameText);
        mailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.LpasswordText);
        re_passwordText = findViewById(R.id.repasswordText);
        phoneText = findViewById(R.id.phoneText);
        button = findViewById(R.id.signUp);
        imageView.setImageResource(R.drawable.selectimage);

    }

    public void selectImage(View view) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri imageData = data.getData();


            try {

                if (Build.VERSION.SDK_INT >= 28) {

                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);

                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    imageView.setImageBitmap(selectedImage);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            selectedImage = drawable.getBitmap();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Sign_up(View view) {
        String name = nameText.getText().toString();
        String surname = surnameText.getText().toString();
        String mail = mailText.getText().toString();
        String pA = passwordText.getText().toString();
        String re_p = re_passwordText.getText().toString();
        String phone = phoneText.getText().toString();

        if (name.isEmpty() || surname.isEmpty() || mail.isEmpty() || pA.isEmpty() || re_p.isEmpty() || phone.isEmpty()) {
            Toast.makeText(Register.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();

        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            mailText.setError("Please enter valid mail");
        } else if (!pA.matches(re_p)) {
            passwordText.setError("pasword does not match");
            re_passwordText.setError("pasword does not match");
        } else {
            Bitmap smallImage = makeSmallerImage(selectedImage, 300);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            smallImage.compress(Bitmap.CompressFormat.PNG, 50, outputStream);
            byte[] byteArray = outputStream.toByteArray();

            try {
                int flag = 0;
                database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
                database.execSQL("CREATE TABLE IF NOT EXISTS userinformation (name VARCHAR,surname VARCHAR,email VARCHAR PRIMARY KEY ,password VARCHAR,phonenumber VARCHAR ,image BLOB)");


                String sqlString = "INSERT INTO userinformation (name, surname, email,password, phonenumber,image) VALUES (?, ?, ?, ?,?,?)";
                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                sqLiteStatement.bindString(1, name);
                sqLiteStatement.bindString(2, surname);
                sqLiteStatement.bindString(3, mail);
                sqLiteStatement.bindString(4, pA);
                sqLiteStatement.bindString(5, phone);
                sqLiteStatement.bindBlob(6, byteArray);
                sqLiteStatement.execute();

                Intent intent = new Intent(Register.this, MainPage.class);
                intent.putExtra("mail", mail);
                startActivity(intent);
                finish();


            } catch (Exception e) {
                Toast.makeText(Register.this, "E mail already registered the app", Toast.LENGTH_LONG).show();

            }
        }


    }


    public Bitmap makeSmallerImage(Bitmap image, int maximumSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maximumSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maximumSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
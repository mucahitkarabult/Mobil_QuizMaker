package com.mucahitkarabult.multiple_activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MakeRecylerAdapter extends RecyclerView.Adapter<MakeRecylerAdapter.PostHolder> {
    private ArrayList<String> userQuestion;
    private ArrayList<String> userAnswerA;
    private ArrayList<String> userAnswerB;
    private ArrayList<String> userAnswerC;
    private ArrayList<String> userAnswerD;
    private ArrayList<String> userAnswerE;
    private ArrayList<String> userAnswerCo;
    private ArrayList<String> questionid;

    public MakeRecylerAdapter(ArrayList<String> userQuestion, ArrayList<String> userAnswerA, ArrayList<String> userAnswerB, ArrayList<String> userAnswerC, ArrayList<String> userAnswerD, ArrayList<String> userAnswerE, ArrayList<String> userAnswerCo, ArrayList<String> questionid) {
        this.userQuestion = userQuestion;
        this.userAnswerA = userAnswerA;
        this.userAnswerB = userAnswerB;
        this.userAnswerC = userAnswerC;
        this.userAnswerD = userAnswerD;
        this.userAnswerE = userAnswerE;
        this.userAnswerCo = userAnswerCo;
        this.questionid = questionid;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.exam_recyler,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.question.setText(userQuestion.get(position));
        Integer id= Integer.valueOf(questionid.get(position));
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    SQLiteDatabase database = v.getContext().openOrCreateDatabase("Question",Context.MODE_PRIVATE,null);
                    database.execSQL("CREATE TABLE IF NOT EXISTS questions (id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");

                    String select = "SELECT * FROM  questions " + " WHERE " + "id" + " = ?";
                    Cursor cursor = database.rawQuery(select, new String[]{String.valueOf(id)});

                    int idIx=cursor.getColumnIndex("id");
                    int queIx = cursor.getColumnIndex("question");
                    int AIx = cursor.getColumnIndex("aoption");
                    int BIx = cursor.getColumnIndex("boption");
                    int CIx = cursor.getColumnIndex("coption");
                    int DIx = cursor.getColumnIndex("doption");
                    int EIx = cursor.getColumnIndex("eoption");
                    int CoIx = cursor.getColumnIndex("answer");
                    int mailIx = cursor.getColumnIndex("mail");
                    String Vid="";
                    String Vuq="";
                    String Vaa="";
                    String Vab="";
                    String Vac="";
                    String Vad="";
                    String Vae="";
                    String Vaco="";
                    String mail="";
                    while (cursor.moveToNext()) {
                         Vid=String.valueOf(cursor.getInt(idIx));
                         Vuq=cursor.getString(queIx);
                         Vaa=cursor.getString(AIx);
                         Vab=cursor.getString(BIx);
                         Vac=cursor.getString(CIx);
                         Vad=cursor.getString(DIx);
                         Vae=cursor.getString(EIx);
                         Vaco=cursor.getString(CoIx);
                         mail=cursor.getString(mailIx);
                    }

                    notifyItemRemoved(position);

                SQLiteDatabase database2 = v.getContext().openOrCreateDatabase("Quiz",Context.MODE_PRIVATE,null);
                database2.execSQL("CREATE TABLE IF NOT EXISTS questions(id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");
                try {
                    String sqlString = "INSERT INTO questions(question,aoption,boption,coption,doption,eoption,answer,mail) VALUES (?, ?, ?, ?,?,?,?,?)";
                    SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                    sqLiteStatement.bindString(1, Vuq);
                    sqLiteStatement.bindString(2, Vaa);
                    sqLiteStatement.bindString(3, Vab);
                    sqLiteStatement.bindString(4, Vac);
                    sqLiteStatement.bindString(5, Vad);
                    sqLiteStatement.bindString(6, Vae);
                    sqLiteStatement.bindString(7, Vaco);
                    sqLiteStatement.bindString(8, mail);
                    sqLiteStatement.execute();
                    Toast.makeText(v.getContext(), "Question is added", Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    System.out.println(e);
                }

            }



        });

    }

    @Override
    public int getItemCount() {
        return userQuestion.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{
        TextView question;
        Button button;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.ma_re_questionText);
            button=itemView.findViewById(R.id.ma_add);
        }
    }
}

package com.mucahitkarabult.multiple_activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ReceylerAdapter extends RecyclerView.Adapter<ReceylerAdapter.PostHolder> {

    private ArrayList<String> userQuestion;
    private ArrayList<String> userAnswerA;
    private ArrayList<String> userAnswerB;
    private ArrayList<String> userAnswerC;
    private ArrayList<String> userAnswerD;
    private ArrayList<String> userAnswerE;
    private ArrayList<String> userAnswerCo;
    private ArrayList<String> questionid;
    private Context context;

    public ReceylerAdapter(@NonNull ArrayList<String> userQuestion, ArrayList<String> userAnswerA, ArrayList<String> userAnswerB, ArrayList<String> userAnswerC, ArrayList<String> userAnswerD, ArrayList<String> userAnswerE, ArrayList<String> userAnswerCo, ArrayList<String> id) {
        this.userQuestion = userQuestion;
        this.userAnswerA = userAnswerA;
        this.userAnswerB = userAnswerB;
        this.userAnswerC = userAnswerC;
        this.userAnswerD = userAnswerD;
        this.userAnswerE = userAnswerE;
        this.userAnswerCo = userAnswerCo;
        this.questionid = id;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyler_row, parent, false);
        context = parent.getContext();
        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.A.setText(userAnswerA.get(position));
        holder.B.setText(userAnswerB.get(position));
        holder.C.setText(userAnswerC.get(position));
        holder.D.setText(userAnswerD.get(position));
        holder.E.setText(userAnswerE.get(position));
        holder.Q.setText(userQuestion.get(position));
        holder.Co.setText(userAnswerCo.get(position));
        Integer id= Integer.valueOf(questionid.get(position));
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Are U sure");
                alert.setMessage("Are u sure to delete this question");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            SQLiteDatabase database = v.getContext().openOrCreateDatabase("Question",Context.MODE_PRIVATE,null);
                            database.execSQL("CREATE TABLE IF NOT EXISTS questions (id INTEGER PRIMARY KEY,question VARCHAR,aoption VARCHAR,boption VARCHAR,coption VARCHAR,doption VARCHAR,eoption VARCHAR,answer VARCHAR,mail VARCHAR)");

                            String sqlString =  "DELETE FROM questions WHERE id= ?";
                            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                            sqLiteStatement.bindString(1, String.valueOf(id));
                            sqLiteStatement.execute();
                            Toast.makeText(v.getContext(), "Question is deleted", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);


                        }
                        catch (Exception e){
                            System.out.println(e);
                        }



                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(), "Question dont delete", Toast.LENGTH_SHORT).show();

                    }
                });
                alert.show();

            }

        });
        holder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),Update.class);
                intent.putExtra("id",questionid.get(position));
                v.getContext().startActivity(intent);
                notifyItemRemoved(position);

            }
        });

    }


    @Override
    public int getItemCount() {
        return userQuestion.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        TextView A;
        TextView B;
        TextView C;
        TextView D;
        TextView E;
        TextView Q;
        TextView Co;
        Button del;
        Button up;
        View rootView;

        public PostHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            A = itemView.findViewById(R.id.re_AText);
            B = itemView.findViewById(R.id.re_BText);
            C = itemView.findViewById(R.id.re_CText);
            D = itemView.findViewById(R.id.re_DText);
            E = itemView.findViewById(R.id.re_EText);
            Q = itemView.findViewById(R.id.ma_re_questionText);
            Co = itemView.findViewById(R.id.re_coText);
            del = itemView.findViewById(R.id.deleteButton);
            up = itemView.findViewById(R.id.uptadeButton);


        }

    }
}

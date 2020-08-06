package com.kfs.onlineexam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetExamMCQ extends Fragment implements OnOptionItemSelected {


    DatabaseReference questionsRef ;
    FirebaseDatabase database;


    TextView examName;
    RecyclerView getExamRecyclerView;
    Button finish;


    GetExamViewH ad;



    List<MCQModel> list ;

    HashMap<Integer,Integer> answers;
    HashMap<Integer,Integer> studentAnswers;
    int score;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_get_exam_mcq,container,false);
        InflatingEditTexts(v);
        Instantiating();
        ad.setOnOptionSelected(this);
        GetQuestions();
        Log.e("ffff","1");
       // Log.e("ffff","1/"+questionsList.size());
       // Log.e("ffff","1/"+choisesList.size());

        Log.e("ffff","2");

        Log.e("ffff","3");
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        return v;
    }

    private void finish() {
        Log.e("final","1/ "+answers.size());
        Log.e("finall","1/ "+studentAnswers.size());
        for (int y = 0 ; y < answers.size() ;y++)
        {
            if(answers.get(y) == studentAnswers.get(y))
            {
                score++;
            }
        }
        Log.e("finallll1","1/ "+answers.get(0)+studentAnswers.get(0));
        Log.e("finallll2","1/ "+answers.get(1)+studentAnswers.get(1));
        Log.e("finallll3","1/ "+score);
    }


    private void GetQuestions() {
        Log.e("kosom","ldonia");
        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("2","111111111111111111");
                if (dataSnapshot.exists()){
                    Log.e("3","111111111111111111");
                    int i=0;
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){

                        MCQModel mm= (MCQModel) npsnapshot.getValue(MCQModel.class);

                         Log.e("bmooooot","1/"+mm.getAns());
                        list.add(mm);

                        answers.put(i,mm.getAns());
                        i++;
                    }

                    ad.setQuestionModels(list);
                    getExamRecyclerView.setAdapter(ad);
                    //Log.e("trt",""+questionsList.get(1));
                    Log.e("wooooow","q/"+answers.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("komha",""+databaseError);
            }});

    }

    private void Instantiating() {

        database = FirebaseDatabase.getInstance();
        questionsRef = database.getReference().child("Professor").child("Dr Amr").child("Testing")
                .child("MCQ").child("Questions");


        answers = new HashMap<Integer,Integer>();
        studentAnswers = new HashMap<Integer,Integer>();
        ad = new GetExamViewH();
        list = new ArrayList<MCQModel>();
        getExamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getExamRecyclerView.setHasFixedSize(true);
    }

    private void InflatingEditTexts(View v) {
        examName = (TextView)v.findViewById(R.id.welcomeExamName);
        getExamRecyclerView = (RecyclerView)v.findViewById(R.id.getExamMCQRecycler);
        finish = (Button)v.findViewById(R.id.finishMCQExam);

    }


    public static GetExamMCQ newInstance ()
    {
        GetExamMCQ mcq=new GetExamMCQ();
        return mcq;
    }

    @Override
    public void onOptionSelected(int position, int itemSelected) {
        list.get(position).setSelectedAnswerPosition(itemSelected);
        Log.e("7","11111111111111");
        switch (itemSelected){
            case 1:
                list.get(position).setC1(true);
                Log.e("fff23",""+position+"/ "+itemSelected);
                break;

            case 2:
                list.get(position).setC2(true);
                Log.e("fff23",""+position+"/ "+itemSelected);
                break;

            case 3:
                list.get(position).setC3(true);
                Log.e("fff23",""+position+"/ "+itemSelected);
                break;

            case 4:
                list.get(position).setC4(true);
                Log.e("fff23",""+position+"/ "+itemSelected);
                break;

        }
        //trueAndFalseAdapter.setQuestionModels(list);

        studentAnswers.put(position,itemSelected);
        Log.e("8","11111111111111");
    }
}

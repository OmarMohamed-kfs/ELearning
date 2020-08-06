package com.kfs.onlineexam;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GetExamTrueAndFalse extends Fragment implements OnOptionItemSelected {

    DatabaseReference questionsRef ;
    FirebaseDatabase database;


    TextView examName;
    RecyclerView getExamRecyclerView;
    Button finish;

    GetExamTrueAndFalseAdapter ad;


    HashMap<Integer,Integer> answers;
    HashMap<Integer,Integer> studentAnswers;

    int score;

    List<TrueAndFalseModel> ans ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_get_exam_true_and_false, container, false);
        InflatingEditTexts(v);
        ad = new GetExamTrueAndFalseAdapter();
        ad.setOnOptionSelected(this);
        Instantiating();
        GetQuestions();
        //GetQuestio();

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
        Log.e("finallll","1/ "+score);
    }


    private void InflatingEditTexts(View v) {
        examName = (TextView)v.findViewById(R.id.welcomeExamNameTAF);
        getExamRecyclerView =(RecyclerView) v.findViewById(R.id.getExamTAFRecycler);
        finish = (Button) v.findViewById(R.id.finishTAFExam);

    }
    private void Instantiating() {
        database = FirebaseDatabase.getInstance();
        questionsRef = database.getReference().child("Professor").child("Dr Amr").child("Testing")
                .child("True and False").child("Questions");
        ans = new ArrayList<TrueAndFalseModel>();
        answers = new HashMap<Integer,Integer>();
        studentAnswers = new HashMap<Integer,Integer>();
        getExamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getExamRecyclerView.setHasFixedSize(true);

    }
    private void GetQuestions() {
        Log.e("timee","no timee");
        questionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("2","fafa");
                if (dataSnapshot.exists()){
                    Log.e("3","fafa");
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){

                        TrueAndFalseModel mm= (TrueAndFalseModel) npsnapshot.getValue(TrueAndFalseModel.class);

                        ans.add(mm);
                        int i=0;
                        answers.put(i,mm.getAns());
                        i++;
                    }
                    ad.setQuestionModels(ans);
                    getExamRecyclerView.setAdapter(ad);
                    //Log.e("trt",""+questionsList.get(1));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("koa",""+databaseError);
            }});
    }



    public static GetExamTrueAndFalse newInstance ()
    {
        GetExamTrueAndFalse mcq=new GetExamTrueAndFalse();
        return mcq;
    }

    @Override
    public void onOptionSelected(int position, int itemSelected) {
        int answer = 0;
        int pos = 0;
        ans.get(position).setSelectedAnswerPosition(itemSelected);
            Log.e("7","11111111111111");
            switch (itemSelected){
                case 1:
                    ans.get(position).setC1(true);
                    Log.e("fff23",""+position+"/ "+itemSelected);
                    answer = 1;
                    pos = position;
                    break;

                case 2:
                    ans.get(position).setC2(true);
                    Log.e("fff23",""+position+"/ "+itemSelected);
                    answer = 2;
                    pos = position;
                    break;

                default:
                    ans.get(position).setC2(true);
                    Log.e("fff23",""+position+"/ "+itemSelected);
                    answer = 0;
                    pos = position;
                    break;
            }
            studentAnswers.put(pos,answer);
            //trueAndFalseAdapter.setQuestionModels(list);
             ad.notifyDataSetChanged();
            Log.e("8","11111111111111");

    }
}

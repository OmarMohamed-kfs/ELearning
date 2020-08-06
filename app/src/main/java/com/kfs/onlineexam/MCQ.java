package com.kfs.onlineexam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MCQ extends Fragment {

    EditText q,a1,a2,a3,a4 ,subjectName,examMinutes;
    TextView a7a;
    Button addMCQ , submitMCQExam;
    RecyclerView recyclerView;

    RadioGroup radioGroup;
    RadioButton rb1,rb2,rb3,rb4;

    String qv , a1v , a2v , a3v , a4v;
    List<MCQModel> list ;
    String examName;
    MCQAdapter mcqAdapter;

    Toast mToast;
    SharedPreferencesConfig sharedPreferencesConfig;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference secondRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_mcq, container, false);
        inflatingEditTexts(v);
        mcqAdapter = new MCQAdapter(list);
        sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());
        addMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestion();
            }
        });
        return v;
    }

    private void inflatingEditTexts(View v) {
        subjectName =(EditText)v.findViewById(R.id.MCQSubjectName);
        examMinutes =(EditText)v.findViewById(R.id.MCQExamMinutes);
        q =(EditText)v.findViewById(R.id.enterQ);
        a1 =(EditText)v.findViewById(R.id.enterA1);
        a2 =(EditText)v.findViewById(R.id.enterA2);
        a3 =(EditText)v.findViewById(R.id.enterA3);
        a4 =(EditText)v.findViewById(R.id.enterA4);
        addMCQ =(Button)v.findViewById(R.id.btnAdd);
        list = new ArrayList<MCQModel>();
        submitMCQExam =(Button)v.findViewById(R.id.submitMcqExam);
        radioGroup = (RadioGroup)v.findViewById(R.id.rgMCQ) ;
        rb1 =(RadioButton)v.findViewById(R.id.edans1);
        rb2 =(RadioButton)v.findViewById(R.id.edans2);
        rb3 =(RadioButton)v.findViewById(R.id.edans3);
        rb4 =(RadioButton)v.findViewById(R.id.edans4);
        recyclerView = (RecyclerView)v.findViewById(R.id.mcqRecycler);
        a7a =(TextView)v.findViewById(R.id.a7o);


    }
    private boolean getValues() {
        if(subjectName.getText().toString().isEmpty())
        {
            Log.e("11","ggggggggggggggggggggggggggggggggggggggg");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter subject name  ", Toast.LENGTH_LONG);
            mToast.show();
            return false;

        }
        else if(q.getText().toString().isEmpty())
        {
            Log.e("11","ggggggggggggggggggggggggggggggggggggggg");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter question ", Toast.LENGTH_LONG);
            mToast.show();
            return false;

        }else if(a1.getText().toString().isEmpty())
        {
            Log.e("12","ggggggggggggggggggggggggggggggggggggggg");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter first answer ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(a2.getText().toString().isEmpty())
        {
            Log.e("13","ggggggggggggggggggggggggggggggggggggggg");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter second answer ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if (a3.getText().toString().isEmpty()){
            Log.e("14","ggggggggggggggggggggggggggggggggggggggg");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter third answer ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if (a4.getText().toString().isEmpty())
        {
            Log.e("15","ggggggggggggggggggggggggggggggggggggggg");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter fourth answer", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(radioGroup.getCheckedRadioButtonId()==-1)
        {
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please select answer ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }
        return true;
    }

    private void addQuestion() {
        Log.e("1","ggggggggggggggggggggggggggggggggggggggg");

        if(!getValues()){
            return;
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mcqAdapter);
        int i= radioGroup.getCheckedRadioButtonId();
        int answer=0;
        switch (i)
        {
            case R.id.edans1:
                answer = 1;
                break;
            case R.id.edans2:
                answer = 2;
                break;
             case R.id.edans3:
                answer = 3;
                break;
            case R.id.edans4:
                answer = 4;
                break;
        }
        //storeQuestion(q.getText().toString());
        //storeChoises(a1.getText().toString(),a2.getText().toString(),a3.getText().toString(),a4.getText().toString());
        MCQModel m = new MCQModel(q.getText().toString(),a1.getText().toString(),a2.getText().toString(),a3.getText().toString(),a4.getText().toString(),answer);
        list.add(m);

       /*
        StudentMCQModel mm = new StudentMCQModel();

        mm.setQ(q.getText().toString());//,,a2.getText().toString(),a3.getText().toString(),a4.getText().toString(),answer
        mm.setAns1(a1.getText().toString());
        mm.setAn2(a2.getText().toString());
        mm.setAns3(a3.getText().toString());
        mm.setAns4(a4.getText().toString());
        mm.setRealAns(answer);*/
        storeQuestions(m);
        //storeAnswer(answer);
        Log.e("3","ggggggggggggggggggggggggggggggggggggggg");
    }



    private void storeQuestions(MCQModel m) {
        String name = sharedPreferencesConfig.readName();
        examName = subjectName.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.e("333333","a7a"+name);
        databaseReference = firebaseDatabase.getReference().child("Professor").child(name).child(examName).child("MCQ").child("Questions");
        databaseReference.push().setValue(m);
    }




    public static MCQ newInstance ()
    {
        MCQ mcq=new MCQ();
        return mcq;
    }


}

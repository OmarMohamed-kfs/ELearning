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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class TaF extends Fragment implements OnOptionItemSelected{

    EditText subjectName , examMinutes , q;
    Button addQ,submit;
    int numOfQuestions;
    RadioGroup radioGroup;
    RadioButton rb1,rb2;
    String examName;
    Toast mToast;
    RecyclerView mRecyclerView;
    List<TrueAndFalseModel> list ;
    LinearLayoutManager layoutManager;
    TrueAndFalseAdapter trueAndFalseAdapter;

    SharedPreferencesConfig sharedPreferencesConfig;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ta, container, false);
        sharedPreferencesConfig = new SharedPreferencesConfig(getActivity());
        InflatingView(v);
        list = new ArrayList<TrueAndFalseModel>();
        trueAndFalseAdapter =new TrueAndFalseAdapter();
        trueAndFalseAdapter.setOnOptionSelected(this);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list = new ArrayList<TrueAndFalseModel>();
        trueAndFalseAdapter.setQuestionModels(list);
        mRecyclerView.setLayoutManager(layoutManager);
        addQ.setOnClickListener(

                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("1","t1111111111");
                AddQuestion();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
        return v;
    }

    private void Submit() {
    }

    private void InflatingView(View v) {
        subjectName = (EditText)v.findViewById(R.id.subjectName);
        examMinutes = (EditText)v.findViewById(R.id.examMinutes);
        q = (EditText)v.findViewById(R.id.edQ);
        radioGroup = (RadioGroup)v.findViewById(R.id.radioGroup);
        rb1 = (RadioButton)v.findViewById(R.id.truee);
        rb2 = (RadioButton)v.findViewById(R.id.falsee);
        addQ = (Button) v.findViewById(R.id.btn);
        submit = (Button) v.findViewById(R.id.submit);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler);

    }

    private void AddQuestion() {
        Log.e("1","t22222222");
        if(!checkForNullVlues()){
            return;
        }
        numOfQuestions++;
        TrueAndFalseModel trueAndFalseModel = new TrueAndFalseModel();
        String question = q.getText().toString();
        int answer = 0;
        int id = radioGroup.getCheckedRadioButtonId();
        Log.e("1212",""+id);
        switch (id){

            case R.id.truee:
                answer = 1;
                break;
            case R.id.falsee:
                answer = 2;
                break;
        }
        Log.e("4","11111111111111");

            trueAndFalseModel.setQ(question);
            trueAndFalseModel.setAns(answer);
            list.add(trueAndFalseModel);

            mRecyclerView.setAdapter(trueAndFalseAdapter);
            mRecyclerView.setHasFixedSize(true);

        storeQuestion(trueAndFalseModel);
        //storeAnswer(answer);
        Log.e("6","11111111111111");

    }

    private boolean checkForNullVlues() {
        if(subjectName.getText().toString().isEmpty())
        {
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter subject name ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(examMinutes.getText().toString().isEmpty())
        {
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter time of exam ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(examMinutes.getText().toString().isEmpty())
        {
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter question ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(q.getText().toString().isEmpty())
        {
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter question ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(radioGroup.getCheckedRadioButtonId()==-1)
        {
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(getActivity(), "Please enter answer ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }
        return true;
    }

    private void storeQuestion(TrueAndFalseModel trueAndFalseModel) {
        String name = sharedPreferencesConfig.readName();
        examName = subjectName.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Log.e("333333","a7a"+name);

        databaseReference = firebaseDatabase.getReference().child("Professor").child(name).child(examName).child("True and False").child("Questions");
        databaseReference.push().setValue(trueAndFalseModel);
    }



    public static TaF newInstance ()
    {
        TaF taf=new TaF();
        return taf;
    }


    @Override
    public void onOptionSelected(int position, int itemSelected) {
        list.get(position).setSelectedAnswerPosition(itemSelected);
        Log.e("7","11111111111111");
        switch (itemSelected){
            case 1:
                list.get(position).setC1(true);
                Log.e("fff",""+position+"/ "+itemSelected);
                break;

            case 2:
                list.get(position).setC2(true);
                Log.e("fff",""+position+"/ "+itemSelected);
                break;

        }
        //trueAndFalseAdapter.setQuestionModels(list);
        trueAndFalseAdapter.notifyDataSetChanged();

        Log.e("8","11111111111111");
    }
}

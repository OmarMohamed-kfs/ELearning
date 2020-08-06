package com.kfs.onlineexam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    EditText code , password;
    Button login;
    RadioGroup radioGroup;
    RadioButton student,professor;
    Context context = this;
    TextView createOne;

    String codeV ;
    String passwordV;
    int num;

    Toast mToast;
    ValueEventListener listener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SharedPreferencesConfig sharedPreferencesConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instantiatingReferenceForSharedPreferences();
        checkForUserStatus();

        inflatingEditTexts();

        //getDatabaseReferenceStudent();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        createOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
            }
        });
    }

    private void instantiatingReferenceForSharedPreferences() {
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());
    }

    private void checkForUserStatus() {
        if(sharedPreferencesConfig.readLoginStatus()==true && sharedPreferencesConfig.readStudentOrProfessor()==1 )
        {
            startActivity(new Intent(this, GetExam.class));
            finish();
        }else if(sharedPreferencesConfig.readLoginStatus()==true && sharedPreferencesConfig.readStudentOrProfessor()==2 )
        {
            startActivity(new Intent(this, Exam.class));
            finish();
        }
    }

    private void inflatingEditTexts() {
        code =(EditText)findViewById(R.id.code);
        password =(EditText)findViewById(R.id.password);
        radioGroup = (RadioGroup)findViewById(R.id.studentOrProfessor);
        student = (RadioButton)findViewById(R.id.student);
        professor = (RadioButton)findViewById(R.id.professor);
        createOne = (TextView)findViewById(R.id.createOne);
        login =(Button)findViewById(R.id.Login);
    }

    private void getDatabaseReferenceStudent() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Student");
    }
    private void getDatabaseReferenceProfessor() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Professor");

    }
    public void register()
    {
        num = getEditTextsValues();
        if (!checkForNullValues())
        {
            return;
        }
        switch (num)
        {
            case 1:
                getDatabaseReferenceStudent();
                break;
            case 2:
                getDatabaseReferenceProfessor();
                break;
        }
        checkForValues(num);
    }

    private boolean checkForNullValues() {
        Log.e("n1","hhhhhhhhhhhhhhhhhhhh");
        if(codeV.isEmpty())
        {
            Log.e("n2","hhhhhhhhhhhhhhhhhhhh");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Please enter your code ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(passwordV.isEmpty()) {
            Log.e("n5","hhhhhhhhhhhhhhhhhhhh");
            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Please enter your password ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if (radioGroup.getCheckedRadioButtonId() == -1)
        {

            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Please choose if you are student or professor", Toast.LENGTH_LONG);
            mToast.show();
            Log.e("3232","4444");
            return false;
        }
        return true;
    }
    private int getEditTextsValues() {
        Log.e("gg2","hhhhhhhhhhhhhhhhhhhh");
        codeV = code.getText().toString();
        Log.e("gg5","hhhhhhhhhhhhhhhhhhhh");
        passwordV = password.getText().toString();
        Log.e("gg6","hhhhhhhhhhhhhhhhhhhh");
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id)
        {
            case R.id.student:
                return 1;

            case R.id.professor:
                return 2;

            default:
                //Toast.makeText(context, "Please choose if you are student or professor  ", Toast.LENGTH_LONG).show();
                return 0;
        }

    }

    private void checkForValues(final int num) {
        if(num == 1)
        {
             listener = databaseReference.orderByChild("Student").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Member member = null;
                    if (dataSnapshot.exists()){

                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            Log.e("4","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            String code = childSnapshot.child("code").getValue().toString();

                            Log.e("hiiii",""+code+"/ "+codeV);
                            String password = childSnapshot.child("password").getValue().toString();
                            if(code.equals(codeV))
                            {
                                Log.e("hellooo",""+childSnapshot.getKey());
                                Log.e("helloyoo","f");
                                if(password.equals(passwordV))
                                {
                                    if(mToast != null) {
                                        mToast.cancel();
                                    }
                                    mToast = Toast.makeText(context, "Login successfully", Toast.LENGTH_LONG);
                                    mToast.show();
                                    sharedPreferencesConfig.writeLoginStatus(true);
                                    sharedPreferencesConfig.writeStudentOrProfessor(1);
                                    sharedPreferencesConfig.writeName(childSnapshot.getKey());
                                    Log.e("sharedddddddddd",""+sharedPreferencesConfig.readLoginStatus());
                                    Intent intent = new Intent(Login.this,GetExam.class);

                                    startActivity(intent);
                                    databaseReference.removeEventListener(this);
                                    finish();
                                }else {
                                    if(mToast != null) {
                                        mToast.cancel();
                                    }
                                    mToast = Toast.makeText(context, "Wrong password", Toast.LENGTH_LONG);
                                    mToast.show();
                                }

                                break;
                            }else {
                                if(mToast != null) {
                                    mToast.cancel();
                                }
                                mToast = Toast.makeText(context, "Invalid code !! ", Toast.LENGTH_LONG);
                                mToast.show();
                                Log.e("heoo","f");
                                continue;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if(mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(context, "No internet connection !!", Toast.LENGTH_LONG);
                    mToast.show();
                }
            });
        }else{

            listener = databaseReference.orderByChild("Professor").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Member member = null;
                    if (dataSnapshot.exists()){
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            Log.e("4","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            String code = childSnapshot.child("code").getValue().toString();
                            Log.e("hiiii",""+code+"/ "+codeV);
                            String password = childSnapshot.child("password").getValue().toString();
                            if(code.equals(codeV))
                            {
                                if(password.equals(passwordV))
                                {
                                    Toast.makeText(context, "Login successfully", Toast.LENGTH_LONG).show();
                                    sharedPreferencesConfig.writeLoginStatus(true);
                                    sharedPreferencesConfig.writeStudentOrProfessor(2);
                                    sharedPreferencesConfig.writeName(childSnapshot.getKey());
                                    Log.e("sharedddddddddd",""+sharedPreferencesConfig.readLoginStatus());
                                    Intent intent = new Intent(Login.this,Exam.class);

                                    databaseReference.removeEventListener(this);
                                    startActivity(intent);

                                    finish();
                                }else {
                                    if(mToast != null) {
                                        mToast.cancel();
                                    }
                                    mToast = Toast.makeText(context, "Wrong password ", Toast.LENGTH_LONG);
                                    mToast.show();
                                }

                                break;
                            }else {
                                if(mToast != null) {
                                    mToast.cancel();
                                }
                                mToast = Toast.makeText(context, "Invalid code !!", Toast.LENGTH_LONG);
                                mToast.show();
                                continue;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if(mToast != null) {
                        mToast.cancel();
                    }
                    mToast = Toast.makeText(context, "No internet connection !!", Toast.LENGTH_LONG);
                    mToast.show();
                }
            });
        }
        }

        /*
    @Override
    protected void onPause() {
        super.onPause();
        databaseReference.removeEventListener(listener);
    }
    */

}

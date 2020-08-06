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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText code , secretKey , id , password;
    Button register;
    RadioGroup radioGroup;
    RadioButton student,professor;
    Context context = this;

    String codeV , idV;
    String secretKeyV;
    String passwordV;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inflatingEditTexts();
        Log.e("0","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        //getDatabaseReferenceStudent();
        //getDatabaseReferenceProfessor();
        Log.e("1","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("2","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                register();
                Log.e("3","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            }
        });


    }

    private void inflatingEditTexts() {
        code =(EditText)findViewById(R.id.code);
        secretKey =(EditText)findViewById(R.id.secretKey);
        id =(EditText)findViewById(R.id.id);
        password =(EditText)findViewById(R.id.password);
        radioGroup = (RadioGroup)findViewById(R.id.studentOrProfessor);
        student = (RadioButton)findViewById(R.id.student);
        professor = (RadioButton)findViewById(R.id.professor);
        register =(Button)findViewById(R.id.register);
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
        int num = getEditTextsValues();
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

    private int getEditTextsValues() {
        Log.e("gg2","hhhhhhhhhhhhhhhhhhhh");
        codeV = code.getText().toString();
        Log.e("gg3","hhhhhhhhhhhhhhhhhhhh");
        idV = id.getText().toString();
        Log.e("gg4","hhhhhhhhhhhhhhhhhhhh");
        secretKeyV = secretKey.getText().toString();
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
        }else if(idV.isEmpty()){
            Log.e("n3","hhhhhhhhhhhhhhhhhhhh");

            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Please enter your ID ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(secretKeyV.isEmpty()) {
            Log.e("n4","hhhhhhhhhhhhhhhhhhhh");

            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Please enter your secret key ", Toast.LENGTH_LONG);
            mToast.show();
            return false;
        }else if(passwordV.isEmpty()) {
            Log.e("n5","hhhhhhhhhhhhhhhhhhhh");

            if(mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG);
            mToast.show();
            Log.e("3232","23232");
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


    private void checkForValues(int num) {
        if (num==1)
        {
            databaseReference.orderByChild("Student").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Member member = null;
                    if (dataSnapshot.exists()){
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            Log.e("4","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            String code = childSnapshot.child("code").getValue().toString();
                            String idd = childSnapshot.child("id").getValue().toString();
                            String secretKey = childSnapshot.child("secretKey").getValue().toString();
                            Log.e("hiiii",""+code+"/ "+codeV);
                            if(code.equals(codeV))
                            {
                                Log.e("1111111",""+idV+"/"+idd);

                                if(idd.equals(idV))
                                {
                                    if(secretKey.equals(secretKeyV))
                                    {
                                        DatabaseReference updateData = FirebaseDatabase.getInstance()
                                                .getReference("Student")
                                                .child(childSnapshot.getKey());
                                        updateData.child("password").setValue(passwordV);
                                        if(mToast != null) {
                                            mToast.cancel();
                                        }
                                        mToast = Toast.makeText(context, "Registering successfully", Toast.LENGTH_LONG);
                                        mToast.show();
                                        startActivity(new Intent(SignUp.this,Login.class));
                                        break;
                                    }else{


                                        if(mToast != null) {
                                            mToast.cancel();
                                        }
                                        mToast = Toast.makeText(context, "Invalid Secret key !!", Toast.LENGTH_LONG);
                                        mToast.show();
                                        break;
                                    }
                                }else{

                                    if(mToast != null) {
                                        mToast.cancel();
                                    }
                                    mToast = Toast.makeText(context, "Invalid ID !!", Toast.LENGTH_LONG);
                                    mToast.show();
                                    break;
                                }

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
        }else{
            num = 2;
            databaseReference.orderByChild("Professor").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Member member = null;
                    if (dataSnapshot.exists()){
                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                            Log.e("4","hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                            String code = childSnapshot.child("code").getValue().toString();
                            String idd = childSnapshot.child("id").getValue().toString();
                            String secretKey = childSnapshot.child("secretKey").getValue().toString();
                            Log.e("hiiii",""+code+"/ "+codeV);
                            if(code.equals(codeV))
                            {
                                Log.e("1111111",""+idV+"/"+idd);
                                if(idd.equals(idV))
                                {
                                    if(secretKey.equals(secretKeyV))
                                    {
                                        DatabaseReference updateData = FirebaseDatabase.getInstance()
                                                .getReference("Professor")
                                                .child(childSnapshot.getKey());
                                        updateData.child("password").setValue(passwordV);
                                        if(mToast != null) {
                                            mToast.cancel();
                                        }
                                        mToast = Toast.makeText(context, "Registering successfully", Toast.LENGTH_LONG);
                                        mToast.show();
                                        startActivity(new Intent(SignUp.this,Login.class));
                                    }else{
                                        if(mToast != null) {
                                            mToast.cancel();
                                        }
                                        mToast = Toast.makeText(context, "Invalid Secret key !!", Toast.LENGTH_LONG);
                                        mToast.show();
                                        break;
                                    }
                                }else{
                                    if(mToast != null) {
                                        mToast.cancel();
                                    }
                                    mToast = Toast.makeText(context, "Invalid ID !!", Toast.LENGTH_LONG);
                                    mToast.show();
                                    break;
                                }
                                break;
                            }else {

                                if(mToast != null) {
                                    mToast.cancel();
                                }
                                mToast = Toast.makeText(context, "Invalid code !!", Toast.LENGTH_LONG);
                                mToast.show();
                                break;
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


}

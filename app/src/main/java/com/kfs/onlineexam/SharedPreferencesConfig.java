package com.kfs.onlineexam;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesConfig {

    private SharedPreferences sharedPreferences;
    private Context context;

    public SharedPreferencesConfig(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preference),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getResources().getString(R.string.login_status_preference), status);
        editor.commit();
    }


    public boolean readLoginStatus()
    {
        boolean status = false;
        status = sharedPreferences.getBoolean(context.getResources().getString(R.string.login_status_preference), false);
        return status;
    }

    public void writeStudentOrProfessor(int i)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("who", i);
        editor.commit();
    }

    public int readStudentOrProfessor()
    {
        int status = 1;
        status = sharedPreferences.getInt("who", 1);
        return status;
    }

    public void writeName(String status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", status);
        editor.commit();
    }


    public String readName()
    {
        String status = null;
        status = sharedPreferences.getString("name", "");
        return status;
    }

    public void writeIfExam(boolean status)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("exam", status);
        editor.commit();
    }


    public boolean readIfExam()
    {
        boolean status = false;
        status = sharedPreferences.getBoolean("exam", false);
        return status;
    }
}

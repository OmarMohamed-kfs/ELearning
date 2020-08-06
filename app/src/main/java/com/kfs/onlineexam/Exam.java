package com.kfs.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

public class Exam extends AppCompatActivity {


    SharedPreferencesConfig sharedPreferencesConfig;
    int num;
    String name;
    Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);


        instantiatingReferenceForSharedPreferences();

        num = getType();

        name = getName();
       // if(sharedPreferencesConfig.readStudentOrProfessor() == 1)
        //{
          //  Intent intent = new Intent(Exam.this,GetExam.class);
           // intent.putExtra("userType",num);
            //intent.putExtra("userName",name);
            //startActivity(intent);
        //}
        Log.e("me","qq"+name);
        switch (num)
        {
            case 1:
                Student(name);
                break;
            case 2:
                Professor(name);
                break;
        }


        SimplePageAdapter adapter=new SimplePageAdapter(getSupportFragmentManager());
        ViewPager viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private String getName() {
        return sharedPreferencesConfig.readName();
    }

    private int getType() {
        return sharedPreferencesConfig.readStudentOrProfessor();
    }

    private void instantiatingReferenceForSharedPreferences() {
        sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

    }

    private void Student(String name) {

        if(mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, "Welcome "+name, Toast.LENGTH_LONG);
        mToast.show();

    }
    private void Professor(String name) {
        sharedPreferencesConfig.writeName(name);
        if(mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, "Welcome "+name, Toast.LENGTH_LONG);
        mToast.show();

    }

    public class SimplePageAdapter extends FragmentPagerAdapter {
        private String [] tabsTitles={"MCQ","True and False"};
        public static final int COUNT=2;
        public SimplePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0: return MCQ.newInstance();
                case 1:return TaF.newInstance();
                default:return MCQ.newInstance();
            }
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabsTitles[position];
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch(item.getItemId())
        {

            case R.id.log_out:
                LogOut();
                break;
        }
        return true;
    }

    private void LogOut() {
        sharedPreferencesConfig.writeLoginStatus(false);
        startActivity(new Intent(Exam.this,Login.class));
        finish();
    }
}

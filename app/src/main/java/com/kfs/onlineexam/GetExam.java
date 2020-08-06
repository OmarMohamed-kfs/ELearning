package com.kfs.onlineexam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetExam extends AppCompatActivity {


    SharedPreferencesConfig sharedPreferencesConfig;
    int num;
    String name;
    Toast mToast;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_exam);
        sharedPreferencesConfig = new SharedPreferencesConfig(this);
        Log.e("1","111111");
        SimplePageAdapter adapter = new SimplePageAdapter(getSupportFragmentManager());
        Log.e("2","111111");
        ViewPager viewPager = findViewById(R.id.view_pager);
        Log.e("3","111111");
        viewPager.setAdapter(adapter);
        Log.e("4","111111");
        TabLayout tabLayout=findViewById(R.id.tab_layout);
        Log.e("5","111111");
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SimplePageAdapter extends FragmentPagerAdapter {
        private String [] tabsTitles={"MCQq","True and Falseq"};
        public static final int COUNT=2;
        public SimplePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Log.e("6","111111");
            switch (position)
            {

                case 0: return GetExamMCQ.newInstance();
                case 1:return GetExamTrueAndFalse.newInstance();
                default:return GetExamMCQ.newInstance();
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
        startActivity(new Intent(GetExam.this,Login.class));
        finish();
    }

}
















/*
public class GetExam extends AppCompatActivity implements OnOptionItemSelected{


    DatabaseReference ref;
    FirebaseDatabase database;

    DatabaseReference answersRef;
    FirebaseDatabase answersDatabase;

    RecyclerView getExamRecyclerView;
    Button finish;


    GetExamViewH ad;
  //  List<String> listData;



    List<TrueAndFalseModel> list ;

    ArrayList<String> answers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_exam);



        finish = (Button)findViewById(R.id.finishExam);
        list = new ArrayList<TrueAndFalseModel>();
        answers = new ArrayList<String>();
        getExamRecyclerView = (RecyclerView)findViewById(R.id.getExamRecycler);
        Log.e("1","111111111111111111");
        getExamRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getExamRecyclerView.setHasFixedSize(true);
        database = FirebaseDatabase.getInstance();
       // listData=new ArrayList<>();
        ref = database.getReference().child("Professor").child("Dr Amr").child("Testing").child("True and False").child("Questions");
        Log.e("1","111111111111111111");

        ad = new GetExamViewH();
        ad.setOnOptionSelected(this);

        answersDatabase = FirebaseDatabase.getInstance();
        answersRef =answersDatabase.getReference().child("Professor").child("Dr Amr").child("Testing").child("True and False").child("Questions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("2","111111111111111111");
                if (dataSnapshot.exists()){
                    Log.e("3","111111111111111111");
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        Log.e("4","111111111111111111");
                        String l= npsnapshot.getValue(String.class);
                        Log.e("5",""+l);
                        //listData.add(l);
                        TrueAndFalseModel trueAndFalseModel = new TrueAndFalseModel();
                        trueAndFalseModel.setQ(l);
                        list.add(trueAndFalseModel);
                        ad.setQuestionModels(list);
                    }
                    Log.e("6","kkkkk");
                   // ad=new GetExamViewH(listData);
                    Log.e("7","kkkkkkkk");
                    getExamRecyclerView.setAdapter(ad);
                    Log.e("8","wwwww");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answersRef.orderByChild("Questions").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        for(DataSnapshot d : dataSnapshot.getChildren())
                        {
                                String ssss = (String) d.getValue();
                                Log.e("zxzx","q /"+ssss);
                                answers.add(ssss);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
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
        ad.notifyDataSetChanged();

        Log.e("8","11111111111111");
    }
}
*/

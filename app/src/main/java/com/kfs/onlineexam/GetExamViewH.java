package com.kfs.onlineexam;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GetExamViewH extends RecyclerView.Adapter<GetExamViewH.ViewHolder>{

    private List<MCQModel> studentAnswers;
    private OnOptionItemSelected onOptionItemSelected;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("v2","111111111111111111");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mcq_item,parent,false);
        Log.e("v3","111111111111111111");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("v4","111111111111111111");
        holder.txtid.setText(studentAnswers.get(position).getQ());
        holder.rb1.setText(studentAnswers.get(position).getA1());
        holder.rb2.setText(studentAnswers.get(position).getA2());
        holder.rb3.setText(studentAnswers.get(position).getA3());
        holder.rb4.setText(studentAnswers.get(position).getA4());

        Log.e("v5","111111111111111111");
    }

    @Override
    public int getItemCount() {

        return studentAnswers.size();
    }

    public void setOnOptionSelected(OnOptionItemSelected onOptionSelected) {
        this.onOptionItemSelected = onOptionSelected;
    }


    public List<MCQModel> getQuestionModels() {
        return studentAnswers;
    }

    public void setQuestionModels(List<MCQModel> questionModels) {
        this.studentAnswers = questionModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtid;
        RadioGroup rg;
        RadioButton rb1,rb2,rb3,rb4;
        public ViewHolder(View itemView) {
            super(itemView);
            txtid=(TextView)itemView.findViewById(R.id.MCQQuestion);
            rb1 = (RadioButton)itemView.findViewById(R.id.ans1);
            rb2 = (RadioButton)itemView.findViewById(R.id.ans2);
            rb3 = (RadioButton)itemView.findViewById(R.id.ans3);
            rb4 = (RadioButton)itemView.findViewById(R.id.ans4);
            rb1.setOnClickListener(this);
            rb2.setOnClickListener(this);
            rb3.setOnClickListener(this);
            rb4.setOnClickListener(this);
            Log.e("v3","11111111111111");
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ans1:
                    onOptionItemSelected.onOptionSelected(getAdapterPosition(), 1);
                    break;

                case R.id.ans2:
                    onOptionItemSelected.onOptionSelected(getAdapterPosition(), 2);
                    break;
                case R.id.ans3:
                    onOptionItemSelected.onOptionSelected(getAdapterPosition(), 3);
                    break;

                case R.id.ans4:
                    onOptionItemSelected.onOptionSelected(getAdapterPosition(), 4);
                    break;


            }
        }
    }
}
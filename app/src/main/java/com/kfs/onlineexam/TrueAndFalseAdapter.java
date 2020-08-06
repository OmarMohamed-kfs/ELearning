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

import java.util.ArrayList;
import java.util.List;

public class TrueAndFalseAdapter extends RecyclerView.Adapter<TrueAndFalseAdapter.ViewHolder> {

    private List<TrueAndFalseModel> questionModels;
    private OnOptionItemSelected onOptionItemSelected;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        // create a normal view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taf_item, parent, false);
        Log.e("1","11111111111111");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.question.setText(questionModels.get(position).getQ());

        viewHolder.truee.setChecked(questionModels.get(position).isC1());
        viewHolder.falsee.setChecked(questionModels.get(position).isC2());
        Log.e("2","11111111111111");

    }

    @Override
    public int getItemCount() {
        if (questionModels != null) {
            return questionModels.size();
        }
        return 0;
    }


    public void setOnOptionSelected(OnOptionItemSelected onOptionSelected) {
        this.onOptionItemSelected = onOptionSelected;
    }


    public List<TrueAndFalseModel> getQuestionModels() {
        return questionModels;
    }

    public void setQuestionModels(List<TrueAndFalseModel> questionModels) {
        this.questionModels = questionModels;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView question ;
        RadioButton truee;
        RadioButton falsee;


        ViewHolder(View convertView) {
            super(convertView);
            question = (TextView) convertView.findViewById(R.id.tafQuestion);
            truee = (RadioButton)convertView.findViewById(R.id.truee);
            falsee = (RadioButton)convertView.findViewById(R.id.falsee);
            truee.setOnClickListener(this);
            falsee.setOnClickListener(this);
            Log.e("3","11111111111111");
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.truee:
                    onOptionItemSelected.onOptionSelected(getAdapterPosition(), 1);
                    break;

                case R.id.falsee:
                    onOptionItemSelected.onOptionSelected(getAdapterPosition(), 2);
                    break;


            }
        }
    }
}

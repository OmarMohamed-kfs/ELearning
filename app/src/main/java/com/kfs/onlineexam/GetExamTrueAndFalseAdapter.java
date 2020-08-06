package com.kfs.onlineexam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GetExamTrueAndFalseAdapter extends RecyclerView.Adapter<GetExamTrueAndFalseAdapter.MyHolder> {

    private List<TrueAndFalseModel> questionModels;
    private OnOptionItemSelected onOptionItemSelected;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.taf_item, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.textView.setText(questionModels.get(position).getQ());
    }

    @Override
    public int getItemCount() {
        return questionModels.size();
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

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        RadioButton truee,falsee;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.tafQuestion);
            truee = (RadioButton)itemView.findViewById(R.id.truee);
            falsee = (RadioButton)itemView.findViewById(R.id.falsee);
            truee.setOnClickListener(this);
            falsee.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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

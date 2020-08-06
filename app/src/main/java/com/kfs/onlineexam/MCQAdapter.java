package com.kfs.onlineexam;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MCQAdapter extends RecyclerView.Adapter<MCQAdapter.ViewHolder> {

    List<MCQModel> mcqModels;
    public MCQAdapter(List<MCQModel> mcqModels) {
        this.mcqModels = mcqModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        // create a normal view
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mcq_item, parent, false);
        Log.e("1","11111111111111");
        return new MCQAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.question.setText(mcqModels.get(position).getQ());
        holder.ans1.setText(mcqModels.get(position).getA1());
        holder.ans2.setText(mcqModels.get(position).getA2());
        holder.ans3.setText(mcqModels.get(position).getA3());
        holder.ans4.setText(mcqModels.get(position).getA4());
    }

    @Override
    public int getItemCount() {
        return mcqModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView question ;
        RadioButton ans1;
        RadioButton ans2;
        RadioButton ans3;
        RadioButton ans4;


        ViewHolder(View convertView) {
            super(convertView);
            question = (TextView) convertView.findViewById(R.id.MCQQuestion);
            ans1 = (RadioButton)convertView.findViewById(R.id.ans1);
            ans2 = (RadioButton)convertView.findViewById(R.id.ans2);
            ans3 = (RadioButton)convertView.findViewById(R.id.ans3);
            ans4 = (RadioButton)convertView.findViewById(R.id.ans4);


            Log.e("3","11111111111111");
        }

    }
}

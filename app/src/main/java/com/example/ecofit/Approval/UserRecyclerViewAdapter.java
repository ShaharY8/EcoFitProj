package com.example.ecofit.Approval;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecofit.Model.User;

import java.util.List;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter {

    List<User> values;

    public UserRecyclerViewAdapter(List<User> values) {
        this.values = values;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        User user;
        TextView tvNotApproved;
        TextView tvApproved;
        TextView tvPhone;
        TextView tvName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.tvNotApproved = itemView.findViewById(tvNotApproved);
//            this.tvApproved = itemView.findViewById(tvApproved);
//            this.tvPhone = itemView.findViewById(tvPhone);
//            this.tvName = itemView.findViewById(tvName);
        }
    }
}

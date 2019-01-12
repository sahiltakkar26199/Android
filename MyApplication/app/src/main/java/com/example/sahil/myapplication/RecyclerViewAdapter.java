package com.example.sahil.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<HashMap<String,String>> userList;
    Context context;

    public RecyclerViewAdapter(List<HashMap<String,String>> list, Context context){
        userList=list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
      MyViewHolder viewHolder=new MyViewHolder(view);
      return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        HashMap<String,String> current=userList.get(i);
        String name=current.get("name");

        String age=String.valueOf(current.get("age"));
        holder.nameView.setText(name);
        holder.ageView.setText(age);
    }

    @Override
    public int getItemCount() {
        if(userList==null){
            return 0;
        }else{
            return userList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
         public TextView nameView;
          public TextView ageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView=itemView.findViewById(R.id.name);
            ageView=itemView.findViewById(R.id.age);
        }
    }
}

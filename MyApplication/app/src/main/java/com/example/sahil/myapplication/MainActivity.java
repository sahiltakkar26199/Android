package com.example.sahil.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView=findViewById(R.id.textView);
        list.add(new User("Sahil",10));
        list.add(new User("Chirag",20));
        list.add(new User("Ashsis",30));
        textView.setText(list.get(1).getName());
        textView.append(list.get(1).getName());
/*        recyclerView=findViewById(R.id.recyclerViewHorizontalList);
      //  recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));
       populateList();
       for(int i=0 ; i<list.size() ; i++)
        Log.v("RecyclerView","value of name1 is "+list.get(i).getName() + i+" "+list.size());

        adapter = new RecyclerViewAdapter(list, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
       recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);
     //   populateList();
  */
    }

    private void populateList() {

       /* User user5=new User("anshul",50);
        User user6=new User("abhishek",60);
        User user7=new User("ajay",30);
        User user8=new User("manish",22);
        User user9=new User("tavish",32);
       */
        list.add(new User("Sahil",10));
        list.add(new User("Chirag",20));
        list.add(new User("Ashsis",30));
        //adapter.notifyDataSetChanged();
    }
}

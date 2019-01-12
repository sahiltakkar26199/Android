package com.example.sahil.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private EditText nameEditTex;
    private EditText ageEditText;
    private Button addButton;
    private DatabaseReference reference2;
    private String[] keysArray = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameEditTex = findViewById(R.id.name_edittext);
        ageEditText = findViewById(R.id.age_editText);
        addButton = findViewById(R.id.add_button);


        recyclerView = findViewById(R.id.recyclerViewHorizontalList);
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.HORIZONTAL));

        adapter = new RecyclerViewAdapter(list, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalLayoutManager.setReverseLayout(true);
        horizontalLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        reference2 = database.getReference("Users");

    /*    for(int i=0 ; i<10 ; i++){
            addUser(i,reference2);
        }
    */


        // Read from the database
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();

                    list.add(map);
                    int n = list.size() - 1;
                    if (n < keysArray.length) {
                        keysArray[n] = snapshot.getKey();
                    }
                }

                adapter = new RecyclerViewAdapter(list, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", error.toException());
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditTex.getText().toString();
                String age = ageEditText.getText().toString();
                Log.v("MainActivity", "ans is " + name + age);
                nameEditTex.setText("");
                ageEditText.setText("");
                Toast.makeText(MainActivity.this, "New User Added", Toast.LENGTH_SHORT).show();
                addUser(name, age);
                String key = reference2.push().getKey();
                reference2.child(keysArray[0]).removeValue();
            }
        });

    }

    private void addUser(String name, String age) {
        Map<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("age", age);
        Map<String, Object> ans = new HashMap<>();
        String key = reference2.push().getKey();
        ans.put(key, result);
        reference2.updateChildren(ans);
    }

}

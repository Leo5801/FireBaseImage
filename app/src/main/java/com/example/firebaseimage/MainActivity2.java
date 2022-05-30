package com.example.firebaseimage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {


    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ArrayList<Malumot> malumotArrayList;
    MalumotAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView=findViewById(R.id.recyclerview1);
        malumotArrayList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Malumot");
        storageReference= FirebaseStorage.getInstance().getReference().child("Malumot");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                malumotArrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Malumot malumot=dataSnapshot.getValue(Malumot.class);
                    malumotArrayList.add(malumot);
                }

                adapter=new MalumotAdapter(MainActivity2.this,malumotArrayList);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
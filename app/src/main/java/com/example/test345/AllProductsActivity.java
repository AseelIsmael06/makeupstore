package com.example.test345;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllProductsActivity extends AppCompatActivity
{
     RecyclerView recyclerView;
     DatabaseReference database;
     MyAdapter myAdapter;
     ArrayList<Product>list;
     Button Load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
    }
        public void onStart() {
            super.onStart();
            recyclerView = findViewById(R.id.productList);
            Load = findViewById(R.id.btnLoad);
            database = FirebaseDatabase.getInstance().getReference("Products");
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            list = new ArrayList<>();
            myAdapter = new MyAdapter(this, list);
            recyclerView.setAdapter(myAdapter);
            Load.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    database.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                Product product = dataSnapshot.getValue(Product.class);
                                list.add(product);
                            }
                            myAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error)
                        {

                        }
                    });
                }
            });



        }

}


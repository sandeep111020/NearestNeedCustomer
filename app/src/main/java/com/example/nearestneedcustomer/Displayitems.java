package com.example.nearestneedcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import com.example.nearestneedcustomer.Adapters.ItemsAdapter;
import com.example.nearestneedcustomer.Models.ItemModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Displayitems extends AppCompatActivity {


    RecyclerView recyclerView;
    ItemsAdapter adapter;
    BottomSheetDialog bottomSheetDialog;
    View sheetview;
    String userid;
    private String temp;
    private DatabaseReference databaseRef5;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayitems);
        lottieAnimationView=findViewById(R.id.lav_actionBar);
        recyclerView = findViewById(R.id.idRVItems);
        userid=getIntent().getStringExtra("id");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Items").child(userid);
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.getChildrenCount() == 0) {

                    //FinalValue finval= new FinalValue("0");
                    //databaseRef5.child("Final").setValue(finval);
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();

                }
                // totalprice = snapshot.child("Final").child("finalprice").getValue(String.class);
                // place.setText(totalprice);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Items").child(userid), ItemModel.class)
                        .build();

        adapter = new ItemsAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
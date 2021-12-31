package com.example.nearestneedcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.nearestneedcustomer.Adapters.ItemsAdapter;
import com.example.nearestneedcustomer.Adapters.ShopsAdapter;
import com.example.nearestneedcustomer.Models.shopsmodel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    RecyclerView recyclerView;
    ShopsAdapter adapter;
    GpsTracker gpsTracker;
    double latitude,longitude;
    String userid;
    private String temp;
    private List<shopsmodel> exampleList=new ArrayList<>();
    private DatabaseReference databaseRef5;
    private LottieAnimationView lottieAnimationView;
    private shopsmodel shops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottieAnimationView=findViewById(R.id.lav_actionBar);
        recyclerView = findViewById(R.id.idRVItemsmain);
        userid=getIntent().getStringExtra("id");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        gpsTracker = new GpsTracker(MainActivity.this);
        if(gpsTracker.canGetLocation()){
            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();


        }else{
            gpsTracker.showSettingsAlert();
        }
        databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Merchants").child("Profile");
        databaseRef5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.getChildrenCount() == 0) {


                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
      /*  FirebaseRecyclerOptions<shopsmodel> options =
                new FirebaseRecyclerOptions.Builder<shopsmodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Merchants").child("Profile"), shopsmodel.class)
                        .build();

        adapter = new ShopsAdapter(options,getApplicationContext());*/



    /*    recyclerView.setAdapter(adapter);*/
        fillExampleList();
    }
    private void fillExampleList() {

        List<Double> l = new ArrayList<>();
        List<Double> lcheck = new ArrayList<>();
        List<Double> testl = new ArrayList<>();
        List<Double> lfin = new ArrayList<>();
        databaseRef5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    double temp;
                    String lat=ds.child("lat").getValue().toString();
                    String lon=ds.child("lon").getValue().toString();
                    System.out.println(distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K"));
                    temp =distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K");
                    l.add(temp);
                    lcheck.add(temp);
                    testl.add(Double.parseDouble(lat));
                }
                System.out.println(l);
                Collections.sort(l);
               for (int i=0; i<l.size();i++){
                   int pos = lcheck.indexOf(l.get(i));
                   double tt = testl.get(pos);
                   lfin.add(tt);
               }
                System.out.println(l);
                System.out.println(testl);
                System.out.println(lfin);



                System.out.println(dataSnapshot);
                for ( int i =0;i<lfin.size();i++){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        String name=ds.child("shopname").getValue().toString();
                        String des=ds.child("shopdesc").getValue().toString();
                        String im=ds.child("shopimage").getValue().toString();
                        String lat=ds.child("lat").getValue().toString();
                        String lon=ds.child("lon").getValue().toString();
                        String upi=ds.child("upi").getValue().toString();
                        String phone=ds.child("phone").getValue().toString();
                        String userid=ds.child("userid").getValue().toString();
                        System.out.println(distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K"));
                        if(lat.equals(lfin.get(i).toString())){
                            shopsmodel item=new shopsmodel(name,des,phone,im,userid,lat,lon,upi);
                            exampleList.add(item);
                            System.out.println(lat);
                            System.out.println("wyudfweyi");
                        }

                    }

                }

                /*for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String name=ds.child("shopname").getValue().toString();
                    String des=ds.child("shopdesc").getValue().toString();
                    String im=ds.child("shopimage").getValue().toString();
                    String lat=ds.child("lat").getValue().toString();
                    String lon=ds.child("lon").getValue().toString();
                    String upi=ds.child("upi").getValue().toString();
                    String phone=ds.child("phone").getValue().toString();
                    String userid=ds.child("userid").getValue().toString();
                    System.out.println(distance(latitude,longitude,Double.parseDouble(lat),Double.parseDouble(lon),"K"));
                    shopsmodel item=new shopsmodel(name,des,phone,im,userid,lat,lon,upi);
                    exampleList.add(item);
                }*/
                RecyclerView recyclerView = findViewById(R.id.idRVItemsmain);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                adapter = new ShopsAdapter(exampleList,getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        exampleList.add(new ExampleItem(R.drawable.kddd, "Ali"));
//        exampleList.add(new ExampleItem(R.drawable.kid, "Uzma"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Aslam"));
//        exampleList.add(new ExampleItem(R.drawable.kddd, "Sara"));
//        exampleList.add(new ExampleItem(R.drawable.kid,  "Ammara"));
//        exampleList.add(new ExampleItem(R.drawable.kidd,  "EasyCoding"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Rasheed"));
//        exampleList.add(new ExampleItem(R.drawable.kddd,  "Areeb"));
//        exampleList.add(new ExampleItem(R.drawable.kiddd,  "Khan"));
    }
    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            System.out.println(dist);
            Toast.makeText(MainActivity.this,dist+"Kilometers",Toast.LENGTH_SHORT).show();
            return (dist);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_questions);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }


}
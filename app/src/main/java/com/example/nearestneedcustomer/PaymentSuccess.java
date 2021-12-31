package com.example.nearestneedcustomer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.nearestneedcustomer.Models.BookingModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentSuccess extends AppCompatActivity {

    private DatabaseReference databaseRefp;
    private DatabaseReference databaseRef;
    String itemkey,name,number,address,area,city,state,pin,amount,items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);
        itemkey=getIntent().getStringExtra("itemkey");
        name=getIntent().getStringExtra("name");
        number=getIntent().getStringExtra("number");
        address=getIntent().getStringExtra("address");
        area=getIntent().getStringExtra("area");
        city=getIntent().getStringExtra("city");
        state=getIntent().getStringExtra("state");
        pin=getIntent().getStringExtra("pin");
        amount=getIntent().getStringExtra("ammount");
        items =getIntent().getStringExtra("itemname");
        //senddata();
    }
//    public void senddata(){
//
//        databaseRefp = FirebaseDatabase.getInstance().getReference("Merchant").child("Orders").child(userid);
//
//        BookingModel model= new BookingModel(name,number,address,area,city,state,pin,currentuser);
//        databaseRef = FirebaseDatabase.getInstance().getReference().child("Customers").child("Orders").child(userid);
//        BookingModel model2= new BookingModel(name,number,address,area,city,state,pin,currentuser);
//
//        String uploadid=databaseRef.push().getKey();
//        databaseRef.child(uploadid).setValue(model);
//
//
//
//
//    }
}
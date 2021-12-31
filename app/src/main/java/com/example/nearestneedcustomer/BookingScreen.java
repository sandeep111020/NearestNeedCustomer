package com.example.nearestneedcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingScreen extends AppCompatActivity {
    EditText name,number,address,area,city,state,pin;
    String Sname,Snumber,Saddress,Sarea,Scity,Sstate,Spin,itemkey;
    Button submitl;
    TextView ammount,txtcount;
    String amount,count;
    private RecyclerView recyclerView;

    private DatabaseReference databaseRef;
    ArrayList<String> items = new ArrayList<String>();
    private DatabaseReference databaseRef3,databaseRef5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_screen);
        recyclerView = findViewById(R.id.idRVItems);
        name=findViewById(R.id.edt_name);
        number=findViewById(R.id.edt_phone_number);
        address=findViewById(R.id.edt_address1);
        area=findViewById(R.id.edt_area);
        city=findViewById(R.id.edt_address2);
        state=findViewById(R.id.edt_address3);
        pin=findViewById(R.id.edt_pin);
        txtcount=findViewById(R.id.txt_count);
        submitl=findViewById(R.id.btn_pay);
        ammount=findViewById(R.id.txt_amount);

        amount=getIntent().getStringExtra("price");
        count=getIntent().getStringExtra("count");
        itemkey= getIntent().getStringExtra("itemkey");
        ammount.setText(amount);
        txtcount.setText(count);
        submitl.setText("Pay:"+amount+"/-");
        submitl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sname=name.getText().toString();
                Snumber=number.getText().toString();
                Saddress=address.getText().toString();
                Sarea=area.getText().toString();
                Scity=city.getText().toString();
                Sstate=state.getText().toString();
                Spin=pin.getText().toString();
                if (TextUtils.isEmpty(Sname)){
                    Toast.makeText(BookingScreen.this,"Please enter name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Snumber)){
                    Toast.makeText(BookingScreen.this,"Please enter number",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Saddress)){
                    Toast.makeText(BookingScreen.this,"Please enter address",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Sarea)){
                    Toast.makeText(BookingScreen.this,"Please enter area",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Scity)){
                    Toast.makeText(BookingScreen.this,"Please enter city",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Sstate)){
                    Toast.makeText(BookingScreen.this,"Please enter state",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(Spin)){
                    Toast.makeText(BookingScreen.this,"Please enter pincode",Toast.LENGTH_SHORT).show();

                }
                else{
                    Intent i = new Intent(BookingScreen.this,PaymentActivity.class);
                    i.putExtra("name",Sname);
                    i.putExtra("number",Snumber);
                    i.putExtra("address",Saddress);
                    i.putExtra("area",Sarea);
                    i.putExtra("city",Scity);
                    i.putExtra("state",Sstate);
                    i.putExtra("pin",Spin);
                    i.putExtra("ammount",amount);
                    i.putExtra("itemkey",itemkey);
                    i.putExtra("itemname",String.valueOf(items));
                    startActivity(i);
                  /*  databaseRef = FirebaseDatabase.getInstance().getReference().child("NewBookings");
                    String UploadId = databaseRef.push().getKey();*/
                    //  Booking book = new Booking(Sname,Snumber,Saddress,Sarea,Scity,Sstate,Spin,price,bookeddata,currentuser);
                    //   databaseRef.child(UploadId).setValue(book);





                }

            }
        });


       /* recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(currentuser).child("Buffer"), ItemModel.class)
                        .build();

        adapter = new BookingAdapter(options,getApplicationContext());
        recyclerView.setAdapter(adapter);*/

    }
   /* @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        databaseRef5.child("Buffer").removeValue();

        Intent i = new Intent(BookingScreen.this,MyCartActivity.class);
        startActivity(i);
    }*/
}
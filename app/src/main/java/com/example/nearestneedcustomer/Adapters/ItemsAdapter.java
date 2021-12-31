package com.example.nearestneedcustomer.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.nearestneedcustomer.BookingScreen;
import com.example.nearestneedcustomer.Models.ItemModel;
import com.example.nearestneedcustomer.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ItemsAdapter extends FirebaseRecyclerAdapter<ItemModel, ItemsAdapter.myviewholder> {


    Context context;

    int i=0;
    String itemkey;




    public ItemsAdapter(@NonNull FirebaseRecyclerOptions<ItemModel> options, Context context) {
        super(options);
        this.context = context;


    }




    @NonNull
    @Override
    public com.example.nearestneedcustomer.Adapters.ItemsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemslayout, parent, false);

        return new com.example.nearestneedcustomer.Adapters.ItemsAdapter.myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ItemModel model) {
        holder.name.setText(" "+model.getItemname());
        holder.price.setText("₹"+model.getSellingprice()+"/-");

        itemkey= getRef(position).getKey();

        Glide.with(context).load(model.getItemimage()).into(holder.image);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*
                Intent intent = new Intent(context, SingleItem.class);
                intent.putExtra("name",model.getItemname());
                intent.putExtra("image",model.getItemimage());
                intent.putExtra("desc",model.getItemdesc());
                intent.putExtra("price",model.getItemprice());
                intent.putExtra("sellingprice",model.getSellingprice());
                intent.putExtra("model",model.getModel());
                intent.putExtra("type",model.getItemtype());
                intent.putExtra("key",itemkey);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/


            }
        });
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BookingScreen.class);
                i.putExtra("price",model.getSellingprice());
                i.putExtra("itemkey",itemkey);
                //amount=getIntent().getStringExtra("price");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });


    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, desc,price;


        ImageView image;
        LinearLayout layout;
        Button btn;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Specname);
            layout=itemView.findViewById(R.id.layoutclick);
            price = (TextView) itemView.findViewById(R.id.Specprice);
            image=  itemView.findViewById(R.id.imageview);
            btn = itemView.findViewById(R.id.buybtn);




        }
    }



}
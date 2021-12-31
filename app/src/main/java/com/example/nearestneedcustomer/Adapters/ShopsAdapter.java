package com.example.nearestneedcustomer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nearestneedcustomer.Displayitems;
import com.example.nearestneedcustomer.Models.ItemModel;
import com.example.nearestneedcustomer.Models.shopsmodel;
import com.example.nearestneedcustomer.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.myviewholder> implements Filterable {

    private List<shopsmodel> exampleList;
    private List<shopsmodel> exampleListFull;
    Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    String Sdate;
    int i=0;
    String itemkey;

    private DatabaseReference databaseRef,databaseRef4;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseRef5;



    @Override
    public Filter getFilter() {
        return examplefilter;
    }

  /*  public ShopsAdapter(@NonNull FirebaseRecyclerOptions<shopsmodel> options, Context context) {
        super(options);
        this.context = context;


    }*/




    @NonNull
    @Override
    public com.example.nearestneedcustomer.Adapters.ShopsAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdisplaylayout, parent, false);

        return new com.example.nearestneedcustomer.Adapters.ShopsAdapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        shopsmodel model = exampleList.get(position);
        holder.name.setText(" "+model.getShopname());
        holder.price.setText(model.getPhone());
        holder.desc.setText(model.getShopdesc());



        Glide.with(context).load(model.getShopimage()).into(holder.image);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Displayitems.class);
                i.putExtra("id",model.getUserid());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }




    class myviewholder extends RecyclerView.ViewHolder {

        TextView name, desc,price;

        ImageView addcart,addfav;

        ImageView image;
        LinearLayout layout;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.Specname);
            layout=itemView.findViewById(R.id.layoutclick);
            price = (TextView) itemView.findViewById(R.id.Specprice);
            image=  itemView.findViewById(R.id.imageview);
            desc = itemView.findViewById(R.id.Specdesc);
            addfav=(ImageView) itemView.findViewById(R.id.addfav);
            addcart=(ImageView) itemView.findViewById(R.id.addcartbtton);



        }

    }
    public ShopsAdapter(List<shopsmodel> exampleList,Context context) {

        this.exampleList = exampleList;
        this.context=context;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    private Filter examplefilter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<shopsmodel> filterlist=new ArrayList<>();
            if(constraint==null|| constraint.length()==0){
                filterlist.addAll(exampleListFull);
            }
            else{
                String pattrn=constraint.toString().toLowerCase().trim();
                for(shopsmodel item :exampleListFull){
                    if(item.getShopdesc().toLowerCase().contains(pattrn)){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


}

package com.example.nearestneedcustomer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.PaymentApp;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

//check payment real
public class PaymentActivity extends AppCompatActivity implements PaymentStatusListener {
    public static final String PAYTM_PACKAGE_NAME = "net.one97.paytm";
    private LottieAnimationView lottieAnimationView;
    ImageView addressclick,shippingclick;
    int i=0,j=0;
    TextView Fname,Fnumber,Faddress,Fcity,Fstate,Fpin,Famount,Fshippingprice,Ftotalamount;
    String name=" Sandeep Kumar",upi="8142410835@paytm",msg="hi",amount="0";
    Button pay,razor;
    String number,address,area, city,state,pin,items;
    LinearLayout shippinglay,addressalay;
    private Uri uri;
    int finalamount;
    String itemkey;
    TextView msgtxt;
    private String status;
    private DatabaseReference databaseRef1,databaseRef,databaseRef3;

    private DatabaseReference databaseRef5;
    private DatabaseReference databaseRef6;
    private EasyUpiPayment easyUpiPayment;

    String transcId;
    private DatabaseReference databaseRefp,databaseRefm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        lottieAnimationView=findViewById(R.id.lav_actionBar);
        addressclick=findViewById(R.id.addressclick);
        shippingclick=findViewById(R.id.shippingclick);
        shippinglay=findViewById(R.id.shippinglayout);
        addressalay=findViewById(R.id.addresslayout);
        Fname=findViewById(R.id.name);
        Fnumber=findViewById(R.id.number);
        Faddress=findViewById(R.id.address);
        Fcity=findViewById(R.id.city);
        Fstate=findViewById(R.id.state);
        Fpin=findViewById(R.id.pin);
        Famount=findViewById(R.id.amount);
        Fshippingprice=findViewById(R.id.shippingprice);
        Ftotalamount=findViewById(R.id.totalamount);
        pay=findViewById(R.id.paybutton);
        razor=findViewById(R.id.payviarazorpay);
        msgtxt=findViewById(R.id.msgtxt);
        lottieAnimationView.playAnimation();
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

        Fname.setText("Name: "+name);
        Fnumber.setText("Number: "+number);
        Faddress.setText("Address: "+address);
        Fcity.setText("City: "+city);
        Fstate.setText("State: "+state);
        Fpin.setText("Pin Code: "+pin);
        Famount.setText(amount);
        Fshippingprice.setText("50");
        finalamount=Integer.parseInt(amount)+50;
        Ftotalamount.setText(finalamount+"");
        pay.setText(finalamount+"");
        razor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentActivity.this,RazorpayActivity.class);
                startActivity(i);
            }
        });
        addressclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i%2!=0) {

                    addressclick.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    addressalay.setVisibility(View.VISIBLE);
                }
                else if (i%2==0){
                    addressclick.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    addressalay.setVisibility(View.GONE);
                }
            }
        });
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss", Locale.getDefault());
        transcId = df.format(c);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // uri = getPayTmUri(name, upi, msg, finalamount+"");
              //  payWithPayTm(PAYTM_PACKAGE_NAME);
                pay();
               // makePayment(String.valueOf("10.00"), upi, name, msg, transcId);

            }
        });
        shippingclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                j++;
                if (j%2!=0){
                    shippingclick.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    shippinglay.setVisibility(View.VISIBLE);
                } else if(j%2==0){
                    shippingclick.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    shippinglay.setVisibility(View.GONE);
                }

            }
        });
    }
    @SuppressLint("NonConstantResourceId")
    private void pay() {
        String payeeVpa = upi;
        String payeeName = name;
        String payeeMerchantCode = transcId;
        String transactionId = transcId;
        String transactionRefId = transcId;
        String description = msg;
        String amount = "100.00";

        PaymentApp paymentApp;
        paymentApp = PaymentApp.ALL;




        // START PAYMENT INITIALIZATION
        EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(this)
                .with(paymentApp)
                .setPayeeVpa(payeeVpa)
                .setPayeeName(payeeName)
                .setTransactionId(transactionId)
                .setTransactionRefId(transactionRefId)
                .setPayeeMerchantCode(payeeMerchantCode)
                .setDescription(description)
                .setAmount(amount);
        // END INITIALIZATION

        try {
            // Build instance
            easyUpiPayment = builder.build();

            // Register Listener for Events
            easyUpiPayment.setPaymentStatusListener(this);

            // Start payment / transaction
            easyUpiPayment.startPayment();
        } catch (Exception exception) {
            exception.printStackTrace();
            //toast("Error: " + exception.getMessage());
        }
    }
    @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        // Transaction Completed
        Log.d("TransactionDetails", transactionDetails.toString());
        //statusView.setText(transactionDetails.toString());

        switch (transactionDetails.getTransactionStatus()) {
            case SUCCESS:
                onTransactionSuccess();
                break;
            case FAILURE:
                onTransactionFailed();
                break;
            case SUBMITTED:
                onTransactionSubmitted();
                break;
        }
    }

    @Override
    public void onTransactionCancelled() {
        // Payment Cancelled by User
        toast("Cancelled by user");
       // imageView.setImageResource(R.drawable.ic_success);
    }

    private void onTransactionSuccess() {
        // Payment Success

        Intent i = new Intent(PaymentActivity.this,PaymentSuccess.class);
        i.putExtra("itemkey",itemkey);
        i.putExtra("name",name);
        i.putExtra("number",number);
        i.putExtra("address",address);
        i.putExtra("area",area);
        i.putExtra("city",city);
        i.putExtra("state",state);
        i.putExtra("pin",pin);
        i.putExtra("ammount",amount);
        i.putExtra("itemname",String.valueOf(items));
        startActivity(i);
        toast("Success");
       // imageView.setImageResource(R.drawable.ic_success);
    }

    private void onTransactionSubmitted() {
        // Payment Pending
        toast("Pending | Submitted");
       // imageView.setImageResource(R.drawable.ic_success);
    }

    private void onTransactionFailed() {
        // Payment Failed
        toast("Failed");
        //imageView.setImageResource(R.drawable.ic_success);
    }


    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
/*
    private void makePayment(String amount, String upi, String name, String desc, String transactionId) {
        // on below line we are calling an easy payment method and passing
        // all parameters to it such as upi id,name, description and others.
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(this)
                // on below line we are adding upi id.
                .setPayeeVpa(upi)
                // on below line we are setting name to which we are making oayment.
                .setPayeeName(name)
                // on below line we are passing transaction id.
                .setTransactionId(transactionId)
                // on below line we are passing transaction ref id.
                .setTransactionRefId(transactionId)
                // on below line we are adding description to payment.
                .setDescription(desc)
                // on below line we are passing amount which is being paid.
                .setAmount(amount)
                // on below line we are calling a build method to build this ui.
                .build();
        // on below line we are calling a start
        // payment method to start a payment.
        easyUpiPayment.startPayment();
        // on below line we are calling a set payment
        // status listener method to call other payment methods.
        easyUpiPayment.setPaymentStatusListener(this);
    }
*/

 /*   @Override
    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        // on below line we are getting details about transaction when completed.
        String transcDetails = transactionDetails.getStatus().toString() + "\n" + "Transaction ID : " + transactionDetails.getTransactionId();
        //transactionDetailsTV.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Tr"+transcDetails, Toast.LENGTH_SHORT).show();

        // on below line we are setting details to our text view.
        /// transactionDetailsTV.setText(transcDetails);
    }*/




 /*   @Override
    public void onTransactionSuccess() {
        // this method is called when transaction is successful and we are displaying a toast message.
        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionSubmitted() {
        // this method is called when transaction is done
        // but it may be successful or failure.
        Log.e("TAG", "TRANSACTION SUBMIT");
    }

    @Override
    public void onTransactionFailed() {
        // this method is called when transaction is failure.
        Toast.makeText(this, "Failed to complete transaction", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCancelled() {
        // this method is called when transaction is cancelled.
        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAppNotFound() {
        // this method is called when the users device is not having any app installed for making payment.
        Toast.makeText(this, "No app found for making transaction..", Toast.LENGTH_SHORT).show();
    }*/
 /*   private static Uri getPayTmUri(String name, String upiId, String note, String amount) {
        return new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();
    }

    private void payWithPayTm(String packageName) {

        if (isAppInstalled(this, packageName)) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(uri);
            i.setPackage(packageName);
            startActivityForResult(i, 0);

        } else {
            Toast.makeText(this, "Paytm is not installed Please install and try again.", Toast.LENGTH_SHORT).show();
            *//* senddata();
             *//**//*  String to ="prabishaapp@gmail.com";
            String subject="New Oder by "+name;
            String message=amount+" rupees are paid to your account for jewlley order";*//**//*
             *//**//*  databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
            databaseRef5.child("Buffer").removeValue();
            databaseRef5.child("MyCart").removeValue();*//**//*
             *//**//*      Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);

            //need this to prompts email client only
            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));*//**//*

            FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",name+"Makes an Order",
                    amount+"is Paid through UPI for "+items,getApplicationContext(),PaymentScreen.this);

            notificationsSender.SendNotifications();
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentScreen.this);
            builder.setMessage("Congratulations! Your order has been placed successfully");
            builder.setTitle("Success");
            builder.setCancelable(false);


            builder
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                            databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
                            databaseRef5.child("Buffer").removeValue();
                            databaseRef5.child("MyCart").removeValue();
                            Intent i = new Intent(PaymentScreen.this,HomeActivity.class);

                            startActivity(i);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();*//*
        }


    }

    // Hi all today we are going to integrate paytm payment with android app.
    // without using any sdk.
    // so lets have the demo first.
    // so lets get started.
    // Please like share and dont forget to subscribe.
    // so transaction successful and we also got the transac ref no
    // Thanks for watching see you in the next video


    //cheking paytm app is install or not

    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            status = data.getStringExtra("Status").toLowerCase();
        }
        if ((RESULT_OK == resultCode) && status.equals("success")) {
            Toast.makeText(PaymentActivity.this, "Transaction successful." + data.getStringExtra("ApprovalRefNo"), Toast.LENGTH_SHORT).show();
            msgtxt.setText("Transaction successful of ₹" + amount);
            msgtxt.setTextColor(Color.GREEN);
            senddata();
         /*   FcmNotificationsSender notificationsSender=new FcmNotificationsSender("/topics/all",name+"   Makes an Order",
                    amount+"/- is Paid through UPI for "+items,getApplicationContext(),PaymentScreen.this);

            notificationsSender.SendNotifications();*/
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
            builder.setMessage("Congratulations! Your order has been placed successfully");
            builder.setTitle("Success");
            builder.setCancelable(false);


            builder
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which)
                        {
                         /*   databaseRef5 = FirebaseDatabase.getInstance().getReference().child("Users").child(currentuser);
                            databaseRef5.child("Buffer").removeValue();
                            databaseRef5.child("MyCart").removeValue();
                            Intent i = new Intent(PaymentActivity.this,MainActivity.class);

                            startActivity(i);*/
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        } else {
            Toast.makeText(PaymentActivity.this, "Transaction cancelled or failed please try again.", Toast.LENGTH_SHORT).show();
            msgtxt.setText("Transaction Failed of ₹" + amount);
            msgtxt.setTextColor(Color.RED);
        }

    }
    public void senddata(){




    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
package com.example.myfuckingpc.gigshub1.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.R;

public class BuyTicketActivity extends AppCompatActivity {
    private TextView price;
    String priceString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        price = findViewById(R.id.tv_buy_ticket_price);
        priceString = price.getText().toString();
    }

    public void clickToPay(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm payment:");
        builder.setMessage("Do you want to pay "+priceString+" for this Ticket?");
        builder.setCancelable(false);
        builder.setPositiveButton("No, thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(BuyTicketActivity.this, "Payment success", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void clicktoCancelPay(View view) {
        onBackPressed();
    }
}

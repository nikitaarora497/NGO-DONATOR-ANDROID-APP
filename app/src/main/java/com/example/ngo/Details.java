package com.example.ngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    TextView d_name,d_address,d_contact,d_donated,d_amount;
    String name,user_balance,d_id,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        d_name = findViewById(R.id.name_value);
        d_address = findViewById(R.id.address_value);
        d_contact = findViewById(R.id.contact_value);
        d_donated = findViewById(R.id.donated_value);
        d_amount = findViewById(R.id.amount_value);

        Bundle i = getIntent().getExtras();
        name = i.getString("name");
        String address = i.getString("address");
        String contact = i.getString("contact");
        String donate = i.getString("donated");
        amount = i.getString("amount");
        user_balance = i.getString("user_balance");
        d_id = i.getString("id");


        d_name.setText(name);
        d_address.setText(address);
        d_contact.setText(contact);
        d_donated.setText(donate);
        d_amount.setText(amount);
    }

    public void donate(View view) {
        Intent payment  = new Intent(Details.this,Payment.class);
        payment.putExtra("pay_name",String.valueOf(name));
        payment.putExtra("avail_bal",String.valueOf(user_balance));
        payment.putExtra("amount",String.valueOf(amount));
        payment.putExtra("d_id",String.valueOf(d_id));
        startActivity(payment);
    }
}
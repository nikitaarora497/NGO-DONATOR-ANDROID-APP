package com.example.ngo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Dashboard extends AppCompatActivity {

    TextView d_name,d_email,d_gender,d_wallet;
    FirebaseAuth f_auth;
    FirebaseFirestore f_store;
    String userid,user_balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        d_name = (TextView) findViewById(R.id.name_value);
        d_email = (TextView) findViewById(R.id.email_value);
        d_gender = (TextView) findViewById(R.id.gender_value);
        d_wallet = (TextView)findViewById(R.id.walletB);


        f_auth = FirebaseAuth.getInstance();
        f_store = FirebaseFirestore.getInstance();

        userid = f_auth.getCurrentUser().getUid();

        DocumentReference documentReference = f_store.collection("users").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                d_name.setText(documentSnapshot.getString("fullName"));
                d_email.setText(documentSnapshot.getString("email"));
                d_gender.setText(documentSnapshot.getString("gender"));
                d_wallet.setText(documentSnapshot.getString("availB"));

                user_balance = documentSnapshot.getString("availB");
            }
        });


    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent logout_intent = new Intent(Dashboard.this,MainActivity.class);
        startActivity(logout_intent);
        finish();
    }


    public void available(View view) {
        Intent available_intent = new Intent(Dashboard.this,Available.class);
        available_intent.putExtra("user_balance",String.valueOf(user_balance));
        startActivity(available_intent);
    }

    public void money(View view) {
        Intent add_money_intent = new Intent(Dashboard.this,Add_money.class);
        add_money_intent.putExtra("avail_bal",String.valueOf(user_balance));
        startActivity(add_money_intent);
    }

    public void register_ngo(View view) {
        Intent register_ngo_intent = new Intent(Dashboard.this,Registe_ngo.class);
        startActivity(register_ngo_intent);
    }
}
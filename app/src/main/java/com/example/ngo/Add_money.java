package com.example.ngo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Add_money extends AppCompatActivity {

    TextView p_wallet;
    EditText p_amount;
    ProgressBar progressBar;
    Button b1;
    FirebaseAuth f_auth;
    FirebaseFirestore f_store;

    String amount, userid;
    int p_int_amount,a_avail_bal;
    int new_amount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        p_amount = findViewById(R.id.amount);
        p_wallet = findViewById(R.id.walletB);
        b1 = findViewById(R.id.m_add);
        progressBar = findViewById(R.id.progressBar);

        Bundle i = getIntent().getExtras();
        final String avail_bal = i.getString("avail_bal");
        p_wallet.setText(avail_bal);

         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 amount = p_amount.getText().toString(); //string

                 p_int_amount = Integer.parseInt(amount); // int
                 a_avail_bal = Integer.parseInt(avail_bal);

                 if (p_int_amount < 99) {
                     Toast.makeText(Add_money.this, "Error !! Please enter the amount greater than 100", Toast.LENGTH_SHORT).show();
                 }
                 else if (p_int_amount > 5000) {
                     Toast.makeText(Add_money.this, "Error !! Please enter the amount less than 5000", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     new_amount = a_avail_bal + p_int_amount;
                     if(new_amount <= 5000)
                     {
                         progressBar.setVisibility(View.VISIBLE);
                         amount = String.valueOf(new_amount); //string
                         f_auth = FirebaseAuth.getInstance();
                         f_store = FirebaseFirestore.getInstance();
                         userid = f_auth.getCurrentUser().getUid();

                         DocumentReference documentReference = f_store.collection("users").document(userid);
                         Map<String, Object> user = new HashMap<>();
                         user.put("availB", amount);
                         documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {
                                 Toast.makeText(Add_money.this, "Amount added Successfully", Toast.LENGTH_SHORT).show();
                                 Intent i = new Intent(Add_money.this,Dashboard.class);
                                 startActivity(i);
                                 finish();
                             }
                         })
                                 .addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                         Toast.makeText(Add_money.this, "Error !! Amount not added Successfully", Toast.LENGTH_SHORT).show();
                                         progressBar.setVisibility(View.INVISIBLE);
                                     }
                                 });
                     }
                     else
                     {
                         Toast.makeText(Add_money.this, "Balance can not be more than 5000", Toast.LENGTH_SHORT).show();
                         progressBar.setVisibility(View.INVISIBLE);
                     }
                 }
             }
         });
    }

}
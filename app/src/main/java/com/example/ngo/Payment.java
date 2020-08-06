package com.example.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity {

    TextView p_name,p_wallet,p_id_value;
    EditText p_amount;
    Button b1;
    FirebaseAuth f_auth;
    FirebaseFirestore f_store;

    int i_ubal,i_tbal,i_want;

    String pay_name,user_balance,total_amount,d_id,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        p_name = findViewById(R.id.Ngo_name);
        p_wallet = findViewById(R.id.walletB);
        p_amount = findViewById(R.id.value);
        p_id_value = findViewById(R.id.id_value);
        b1 = findViewById(R.id.doit);

        Bundle i = getIntent().getExtras();
        pay_name = i.getString("pay_name");
        user_balance = i.getString("avail_bal");
        d_id = i.getString("d_id");
        total_amount = i.getString("amount"); // total amount donated
        p_wallet.setText(user_balance);
        p_name.setText(pay_name);
        p_id_value.setText(d_id);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i_ubal = Integer.parseInt(user_balance); // int user bal
                i_tbal = Integer.parseInt(total_amount); // int total donated
                i_want = Integer.parseInt(p_amount.getText().toString()); // int value to be donated

                if(i_want > i_ubal)
                {
                    Toast.makeText(Payment.this, "Insufficient Balance !! ", Toast.LENGTH_SHORT).show();
                }
                else if (i_want == 0)
                {
                    Toast.makeText(Payment.this, "Value can't be 0", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    i_tbal = i_tbal + i_want;
                    i_ubal = i_ubal-i_want;

                    // Update donated balance in database
                    f_auth = FirebaseAuth.getInstance();
                    f_store = FirebaseFirestore.getInstance();

                    total_amount = String.valueOf(i_tbal); // string value
                    user_balance = String.valueOf(i_ubal); // string value

                    userid = f_auth.getCurrentUser().getUid();

                    DocumentReference documentReference1 = f_store.collection("users").document(userid);
                    Map<String, Object> user = new HashMap<>();
                    user.put("availB", user_balance);

                    documentReference1.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DocumentReference documentReference = f_store.collection("Available_ngo").document(d_id);
                            Map<String, Object> avail = new HashMap<>();
                            avail.put("amount", total_amount);
                            documentReference.update(avail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Payment.this, "Donation Successfully", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(Payment.this, "Balance Updated Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(Payment.this,Dashboard.class);
                                    startActivity(i);
                                    finish();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Payment.this, "Error !! Donation Unsuccessfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
                }
            }
        });
    }

}

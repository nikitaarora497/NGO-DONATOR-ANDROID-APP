package com.example.ngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registe_ngo extends AppCompatActivity {

    EditText e1,e2,e3;
    Button b1;
    ProgressBar progress;

    FirebaseAuth f_auth;
    FirebaseFirestore f_store;
    String id = "1" ;
    int i_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe_ngo);

        e1 = findViewById(R.id.name);
        e2 = findViewById(R.id.Address);
        e3 = findViewById(R.id.phone);
        progress = (ProgressBar) findViewById(R.id.progress);
        b1 = findViewById(R.id.register);
        i_id = Integer.parseInt(id);
        i_id++;

        f_auth = FirebaseAuth.getInstance();
        f_store = FirebaseFirestore.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);

                String name = e1.getText().toString();
                String address = e2.getText().toString();
                String phone = e3.getText().toString();
                id = String.valueOf(i_id);

                Toast.makeText(Registe_ngo.this, "" + name, Toast.LENGTH_SHORT).show();
                Toast.makeText(Registe_ngo.this, "" + address, Toast.LENGTH_SHORT).show();
                Toast.makeText(Registe_ngo.this, "" + phone, Toast.LENGTH_SHORT).show();
                Toast.makeText(Registe_ngo.this, "" + id, Toast.LENGTH_SHORT).show();

                Map<String,Object> user1 = new HashMap<>();
                user1.put("name", name);
                user1.put("address", address);
                user1.put("contact", phone);
                user1.put("id",id);
                user1.put("amount","0");
                user1.put("donated","0");
                DocumentReference documentReference = f_store.collection("Available_ngo").document(id);
                documentReference.set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Registe_ngo.this, "Ngo Successfully Registered ", Toast.LENGTH_SHORT).show();
                        Intent register_intent = new Intent(Registe_ngo.this, Dashboard.class);
                        startActivity(register_intent);
                    }
                });

            }
        });


    }
}
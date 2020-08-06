package com.example.ngo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText r_name,r_email,r_pass,c_pass;
    Button r_register;
    RadioGroup r_radiogroup;
    RadioButton r_gender;
    public FirebaseAuth f_auth;
    FirebaseFirestore f_store;
    ProgressBar progress;
    int selectedId;
    String userid;
    public String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        r_name = (EditText)findViewById(R.id.name);
        r_email = (EditText)findViewById(R.id.email);
        r_pass = (EditText)findViewById(R.id.password);
        c_pass = (EditText)findViewById(R.id.cpassword);
        r_register = (Button)findViewById(R.id.register);
        r_radiogroup = (RadioGroup)findViewById(R.id.radioGroup);
        progress = (ProgressBar) findViewById(R.id.progress);

        r_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)radioGroup.findViewById(i);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                r_gender = (RadioButton)radioGroup.findViewById(selectedId);
                gender = r_gender.getText().toString().trim();
            }
        });

        f_auth = FirebaseAuth.getInstance();
        f_store = FirebaseFirestore.getInstance();
        r_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = r_email.getText().toString().trim();
                final String pass = r_pass.getText().toString().trim();
                final String con_pass = c_pass.getText().toString().trim();
                final String name = r_name.getText().toString().trim();

                if(pass.equals(con_pass))
                {
                    if (pass.length() < 6) {
                        r_pass.setError("Password should have more than 6 charachters");
                    }
                    if (email.isEmpty()) {
                        r_email.setError("Email is Required");
                        return;
                    }
                    if (pass.isEmpty()) {
                        r_pass.setError("password is Required");
                        return;
                    }

                    progress.setVisibility(View.VISIBLE);

                    f_auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                userid = f_auth.getCurrentUser().getUid();
                                DocumentReference documentReference = f_store.collection("users").document(userid);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fullName", name);
                                user.put("email", email);
                                user.put("gender", gender);
                                user.put("availB","0");
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent register_intent = new Intent(Register.this, Login.class);
                                        startActivity(register_intent);
                                    }
                                });
                            }
                            else {
                                Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Register.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
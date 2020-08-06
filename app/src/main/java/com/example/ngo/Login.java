package com.example.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final EditText l_email, l_password;
        Button l_login;
        TextView l_register;
        final FirebaseAuth f_auth;
        final ProgressBar l_progress;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        l_email = (EditText) findViewById(R.id.email);
        l_password = (EditText) findViewById(R.id.password);
        l_login = (Button) findViewById(R.id.login);
        l_register = (TextView) findViewById(R.id.register);
        l_progress = (ProgressBar) findViewById(R.id.progress);


        l_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent r_intent = new Intent(Login.this, Register.class);
                startActivity(r_intent);
            }
        });

        f_auth = FirebaseAuth.getInstance(); // status of firebase
        l_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = l_email.getText().toString().trim();
                String pass  = l_password.getText().toString().trim();

                if (pass.length() < 6) {
                    l_password.setError("Password should have  more than 6 charachters");
                    return;
                }
                if (email.isEmpty()) {
                    l_email.setError("Email is Required");
                    return;
                }
                if (pass.isEmpty()) {
                    l_password.setError("password is Required");
                    return;
                }
                l_progress.setVisibility(View.VISIBLE);

                f_auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this,"Login Successful", Toast.LENGTH_SHORT).show();
                            Intent main_register = new Intent(Login.this,Dashboard.class);
                            startActivity(main_register);
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            l_progress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
    }
}
package com.example.ngo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button m_login;
    Button m_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_login = (Button)findViewById(R.id.login);
        m_register = (Button)findViewById(R.id.mainregister);

        m_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_login = new Intent(MainActivity.this,Login.class);
                startActivity(main_login);
            }
        });

        m_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_register = new Intent(MainActivity.this,Register.class);
                startActivity(main_register);
            }
        });

    }
}
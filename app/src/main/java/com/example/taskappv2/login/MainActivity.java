/***************************************************************************************
 *    Title: Android-Notes-Memos-Application source code
 *    Author: moustafa-dimashkieh
 *    Date: 2021
 *    Code version: 1.0
 *    Availability: https://github.com/moustafa-dimashkieh/Android-Notes-Memos-Application
 *
 ***************************************************************************************/

package com.example.taskappv2.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskappv2.HomeActivity;
import com.example.taskappv2.R;

public class MainActivity extends AppCompatActivity {
    Context context;
    SharedPreferences prefs;

    EditText txtUsername, txtPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        context = this;
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                if(!prefs.getString("username", "##########").equals(username)) {
                    Toast.makeText(context, "Invalid username!", Toast.LENGTH_SHORT).show();
                } else if(!prefs.getString("password", "##########").equals(password)) {
                    Toast.makeText(context, "Wrong password!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!prefs.getString("username", "").equals("")) {
            btnRegister.setVisibility(View.GONE);
        } else {
            txtUsername.setEnabled(true);
            txtPassword.setEnabled(true);
        }
    }
}
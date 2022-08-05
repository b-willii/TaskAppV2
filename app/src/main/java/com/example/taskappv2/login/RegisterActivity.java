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

import com.example.taskappv2.R;

public class RegisterActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword, txtPassConfirm;
    Button btnRegister;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        context = this;
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassConfirm = findViewById(R.id.txtPassConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();
                String passconfirm = txtPassConfirm.getText().toString();
                if(username.equals("") || password.equals("") || passconfirm.equals("")){
                    Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                } else if(password.length()<4) {
                    Toast.makeText(context, "Password too short!", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(passconfirm)) {
                    Toast.makeText(context, "Password entered does not match!", Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();

                    Toast.makeText(context, "Registration Complete!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
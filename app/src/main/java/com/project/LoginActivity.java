package com.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import utils.ShowToast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etName = findViewById(R.id.et_name);
        EditText etPassword = findViewById(R.id.et_password);
        Button login = findViewById(R.id.btn_login);
        Button register = findViewById(R.id.btn_register);
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        CheckBox rememberPass = findViewById(R.id.cb_rememberPass);
        boolean isRemember = sp.getBoolean("remember_pass",false);
        if (isRemember){
            String name = sp.getString("name",null);
            String password = sp.getString("password",null);
            etName.setText(name);
            etPassword.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (name.equals("admin") && password.equals("123456")){
                    SharedPreferences.Editor editor = sp.edit();
                    if (rememberPass.isChecked()){
                        editor.putString("name",name);
                        editor.putString("password",password);
                        editor.putBoolean("remember_pass",true);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else if (etName.getText().toString().trim().equals("") || etPassword.getText().toString().trim().equals("")){
                    ShowToast.shortToast(LoginActivity.this,"用户名或密码不能为空");
                }else {
                    ShowToast.shortToast(LoginActivity.this,"用户名或密码错误");
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
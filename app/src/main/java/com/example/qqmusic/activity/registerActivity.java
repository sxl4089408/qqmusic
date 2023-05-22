package com.example.qqmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqmusic.R;

public class registerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    }

    public void btn_sbumit_register_onclick(View view) {
        EditText edId=findViewById(R.id.et_reg_id);
        EditText edEmail=findViewById(R.id.et_reg_email);
        EditText edPassword=findViewById(R.id.et_reg_password);
        Intent intent=new Intent();
        intent.putExtra("id",edId.getText().toString());
        intent.putExtra("email",edEmail.getText().toString());
        intent.putExtra("password",edPassword.getText().toString());
        setResult(0,intent);
        finish();
    }
}

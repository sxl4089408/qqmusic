package com.example.qqmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.qqmusic.R;

public class MainActivity extends AppCompatActivity {
    private TextView tvStatus;
    private TextView tvID;
    private TextView tvEmail;
    private Button register;
    private Button login;
    private Button home;
    private static  final int REQUEST_REGISTER_CODE=1;
    private static  final int REQUEST_LOGIN_CODE=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//首页界面
        tvStatus=findViewById(R.id.tv_status);
        tvID=findViewById(R.id.tv_id);
        tvEmail=findViewById(R.id.tv_email);
        register=findViewById(R.id.btn_start_register);
        login=findViewById(R.id.btn_start_login);
        home=findViewById(R.id.btn_start_home);
        home.setVisibility(View.GONE);

    }

    public void btn_start_login_onclick(View view) {
        Intent intent=new Intent(this, loginActivity.class);
        startActivityForResult(intent,REQUEST_LOGIN_CODE);
    }

    public void btn_start_register_onclick(View view) {
        Intent intent=new Intent(this, registerActivity.class);
        startActivityForResult(intent,REQUEST_REGISTER_CODE);
    }
    public void btn_start_home_onclick(View view) {
        Intent intent=new Intent(this, homeActivity.class);

        startActivity(intent);
        intent.putExtra("id", tvID.getText().toString());
        finish();//将主要销毁，即点击后退后不会来
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        super.onActivityResult(requestCode,resultCode,intent);
        if(intent==null)
        {
            return;
        }
        switch(requestCode)
        {
            case REQUEST_REGISTER_CODE:
                register.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);

                //tvStatus.setText("注册成功");
                //tvEmail.setText("Email:"+intent.getStringExtra("email"));
                tvID.setText("注册成功！");
                break;
            case REQUEST_LOGIN_CODE:
                register.setVisibility(View.GONE);
                login.setVisibility(View.GONE);
                home.setVisibility(View.VISIBLE);

                // tvStatus.setText("登录成功");
                // tvID.setText("用户名:"+intent.getStringExtra("id"));
                tvEmail.setText("登陆成功！");

                break;
        }

    }


}
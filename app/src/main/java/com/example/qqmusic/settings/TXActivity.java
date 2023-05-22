package com.example.qqmusic.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqmusic.R;


public class TXActivity extends AppCompatActivity {
    private TextView Qcode,user_name;
    private ImageView tx;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pi);

        tx=findViewById(R.id.pi_tx);
        user_name=findViewById(R.id.User_name);
        intent=getIntent();
        String name=intent.getStringExtra("user");
        user_name.setText(name);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);

            }
        });
    }


    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent == null) {
            return;
        }
        switch (requestCode) {
            case 2:
                user_name.setText(intent.getStringExtra("user"));
        }
    }
    @Override//重写后退键
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("user",user_name.getText().toString());//点击后退键后让name进行更改
        this.setResult(0, intent);
        this.finish();
    }
}
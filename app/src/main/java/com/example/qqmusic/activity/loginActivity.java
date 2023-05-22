package com.example.qqmusic.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qqmusic.R;
import com.example.qqmusic.helper.MySQLiteHelper;
import com.example.qqmusic.playerfragment.singerActivity;

public class loginActivity extends AppCompatActivity {
    private EditText edId;
    private EditText edPassword;
    private CheckBox cbRemember;
    private Button  btLogin;
    private SharedPreferences pre;
    private SharedPreferences.Editor editor;
    private MySQLiteHelper sqLiteHelp;
    private SQLiteDatabase Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edId = findViewById(R.id.et_lg_id);
        edPassword = findViewById(R.id.et_lg_password);
        cbRemember = findViewById(R.id.RemeberMe);

        sqLiteHelp = new MySQLiteHelper(loginActivity.this, "usersdb.db", null, 1);
        Db = sqLiteHelp.getWritableDatabase();//完成创建或打开数据库
//判断是否记住密码来初始化登录界面
        pre = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pre.edit();
        boolean isRemembered = pre.getBoolean("remembered", false);
        if (isRemembered) {
            Cursor c = Db.rawQuery("select * from users where remembered=1", null);
            if (c.moveToFirst()) {
                edId.setText(c.getString(c.getColumnIndex("userid")));
                edPassword.setText(c.getString(c.getColumnIndex("password")));
                cbRemember.setChecked(true);
            }
        }






    }

    private boolean CheckLog(){
//判断用户登录信息是否正确
        String id=edId.getText().toString();
        String pwd=edPassword.getText().toString();
//Cursor c=myDb.rawQuery("select * from users where userid=? and password=?",new String[]{id,pwd};
        Cursor c=Db.query("users" ,new String[]{"userid"},
                "userid=? and password=?" ,new String[]{id,pwd},null,null,null);
        if(c.moveToFirst()) {
            return true;
        }else {
            return false;
        }
    }

    public void btn_sbumit_login_onclick(View view) {
        btLogin = findViewById(R.id.btn_lg_submit);
        String rmstate="false";
        if (CheckLog()) {
                  //登录信息正确，判断是否需要保存当前登录信息
            if (cbRemember.isChecked()) {
                String id = edId.getText().toString();
                  //更新数据库中的记住密码标志
                Db.execSQL("update users set remembered=1 where userid=?",
                        new String[]{id});
                Db.execSQL("update users set remembered=0 where userid<>?",
                        new String[]{id});
                editor.putBoolean("remembered", true);
            } else {
                     //更新数据库中的记住密码标志
                Db.execSQL("update users set remembered=0");
                editor.clear();//清除SharedPreferences数据
            }
            editor.apply();//使SharedPreferences修改生效
            Toast.makeText(loginActivity.this, "登录成功! ", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent();
            // intent.putExtra("id",edId.getText().toString());
            setResult(0,intent);
            finish();

        } else {
            Toast.makeText(loginActivity.this, "用户名或密码错误! ",
                    Toast.LENGTH_SHORT).show();
        }
    }

}



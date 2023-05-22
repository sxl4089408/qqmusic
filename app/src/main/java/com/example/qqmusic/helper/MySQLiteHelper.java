package com.example.qqmusic.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static String CREATE_TABLE_USER = "create table users(" +
            "id integer primary key autoincrement," +
            "userid text,password text,remembered integer)";
    private Context sContext;
    public MySQLiteHelper(Context context, String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context,name,factory,version);
        sContext=context;}
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USER);//创建表
           //添加记录用于登录验证
            db.execSQL("insert into users(userid,password,remembered) values(?,?,0)",
            new String[]{"admin","123456"});
            db.execSQL("insert into users(userid,password,remembered) values(?,?,0)",
                    new String[]{"lln'","123456"});
            Toast.makeText(sContext,"数据库初始化成功! ",Toast.LENGTH_LONG).show();}
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }


}

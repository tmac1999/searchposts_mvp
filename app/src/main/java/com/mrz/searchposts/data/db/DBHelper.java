package com.mrz.searchposts.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	private static final String name = "linkdb"; //数据库名称
    private static final int version = 7; //数据库版本
	private static final String TAG = "DBHelper";
	public DBHelper(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
		db.execSQL("CREATE TABLE IF NOT EXISTS link (_id integer primary key autoincrement, url varchar(30), title varchar(100), author varchar(50), posttime varchar(20), replycount varchar(10))");
		db.execSQL("CREATE TABLE IF NOT EXISTS tiebalist (_id integer primary key autoincrement, name varchar(100), encodename varchar(100), createtime varchar(20), isdelete varchar(10) )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "onUpgrade");
		db.execSQL("CREATE TABLE IF NOT EXISTS tiebalist (_id integer primary key autoincrement, name varchar(100), encodename varchar(100), createtime varchar(20) , isdelete varchar(10))");
        //????
       // db.execSQL("alter table tiebalist add isdelete varchar(10)");
       db.execSQL("update tiebalist set isdelete = 'false'");
		
	}
	
}

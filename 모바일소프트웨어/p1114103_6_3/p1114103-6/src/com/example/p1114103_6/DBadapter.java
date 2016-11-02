package com.example.p1114103_6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBadapter extends SQLiteOpenHelper{

	static String NAME = "Activity.db";
	
	public DBadapter(Context mContext){
		super(mContext, NAME, null,1);
	}
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE Activity( _id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, name TEXT, date TEXT, path TEXT, keyword TEXT, comment TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}

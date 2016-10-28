package com.example.pingsmouse;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class DBHelper extends SQLiteOpenHelper {

	static String NAME = "Pings.db";

	public DBHelper(Context context) {
		super(context, NAME, null, 1);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE Pings( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, ip_address TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}

package com.example.p111410_7;

import java.util.ArrayList;

import android.R.anim;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.AndroidCharacter;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DBadapter {
	public final static String TABLE_NAME = "activity10";
	public static final String _ID = "_ID";
	public static final String NUMBER = "name";
	public static final String DATE = "date";
	public final static String DB_NAME = "lab5.db";
	public final static String CREATE_TABLE = "create table if not exists  "
			+ TABLE_NAME + " (" + _ID + " integer primary key autoincrement,"
			+ NUMBER + " text," + DATE + " text )";

	Context mContext;
	SQLiteOpenHelper mHelper = null;
	SQLiteDatabase mDb = null;

	public DBadapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	class DBHelper extends SQLiteOpenHelper {
		public DBHelper(Context context) {
			super(context, DB_NAME, null, 1);
		}

		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);
		}

		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table if exists " + TABLE_NAME);
			onCreate(db);
		}
	}

	public void open() {
		mHelper = new DBHelper(mContext);
		mDb = mHelper.getWritableDatabase();
	}

	public void close() {
		mHelper.close();
	}

	public Cursor fetchAllNotes() {
		return mDb.query(TABLE_NAME, new String[] { _ID, NUMBER, DATE }, // projection
				null, // selection
				null, // selectionArgs
				null, // group By
				null, // having
				_ID + " DESC");// order By
	}

	public Cursor check(long id) {
		String[] selectionArgs = { String.valueOf(id) };
		Cursor c = mDb.query(TABLE_NAME, null, _ID + "=?", selectionArgs, null,
				null, null);
		return c;
	}

	public long insertNote(String arg1, String arg2) {
		ContentValues values = new ContentValues();
		values.put(NUMBER, arg1);
		values.put(DATE, arg2);

		return mDb.insert(TABLE_NAME, null, values);
	}

	public boolean updateNote(String arg1, String arg2, long id) {
		ContentValues values = new ContentValues();
		values.put(NUMBER, arg1);
		values.put(DATE, arg2);

		String[] selectionArgs = { String.valueOf(id) };
		return mDb.update(TABLE_NAME, values, _ID + "=?", selectionArgs) > 0;
	}

	public boolean deleteNote(long id) {
		String[] selectionArgs = { String.valueOf(id) };
		return mDb.delete(TABLE_NAME, _ID + "=?", selectionArgs) > 0;
	}

	public ArrayList<String> fetchNode(int mod) throws SQLException {
		Cursor result;
		ArrayList<String> arr = new ArrayList<String>();
		if(mod ==1){
			result = mDb.rawQuery(
					"select number from activity10",
					null);
			if (result.moveToFirst()) {
				do {
					String argNumber= result.getString(0);
				
					arr.add(argNumber);
					Log.i(null, argNumber);

				} while (result.moveToNext());
			}
			result.close();
		}
		else if(mod==2){
			result = mDb.rawQuery(
					"select date from activity10",
					null);
			if (result.moveToFirst()) {
				do {
					String argDate= result.getString(1);
				
					arr.add(argDate);
					Log.i(null, argDate);

				} while (result.moveToNext());
			}
			result.close();
		}
		return arr;
	}
}

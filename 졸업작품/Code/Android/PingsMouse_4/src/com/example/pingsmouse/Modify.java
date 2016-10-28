package com.example.pingsmouse;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;

public class Modify extends Activity {

	DBHelper mHelper;
	String NAME, IP = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		mHelper = new DBHelper(this);

		String arg3 = getIntent().getExtras().getString("arg3");
		int _arg3 = Integer.parseInt(arg3);

		String query = "SELECT * FROM Pings WHERE _id = '" + _arg3 + "'";

		EditText edt_name = (EditText) findViewById(R.id.editText_name);
		EditText edt_ip = (EditText) findViewById(R.id.editText_ip);

		SQLiteDatabase db;

		db = mHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		String mname = "";
		String mip = "";

		if (cursor.moveToNext()) {
			int name_idx = cursor.getColumnIndex("name");
			mname = cursor.getString(name_idx);
			int ip_idx = cursor.getColumnIndex("ip_address");
			mip = cursor.getString(ip_idx);
		}
		edt_name.setText(mname);
		edt_ip.setText(mip);

		Button btn_save = (Button) findViewById(R.id.button_save);
		Button btn_cancel = (Button) findViewById(R.id.button_cancel);

		btn_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				SQLiteDatabase db;

				db = mHelper.getWritableDatabase();

				String arg3 = getIntent().getExtras().getString("arg3");
				int _arg3 = Integer.parseInt(arg3);

				String q = "DELETE FROM Pings WHERE _id = '" + _arg3 + "'";
				db.execSQL(q);

				EditText edt_name = (EditText) findViewById(R.id.editText_name);
				EditText edt_ip = (EditText) findViewById(R.id.editText_ip);

				NAME = edt_name.getText().toString();
				IP = edt_ip.getText().toString();

				ContentValues values = new ContentValues();
				values.put("name", NAME);
				values.put("ip_address", IP);

				db.insert("Pings", null, values);

				edt_name.setText(" ");
				edt_ip.setText(" ");

				Intent intent = new Intent(Modify.this, ListActivity.class);
				startActivity(intent);
				finish();
			}
		});

		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Modify.this, ListActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

}

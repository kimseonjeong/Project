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

public class Addlist extends Activity {

	DBHelper mHelper;

	String NAME, IP = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		mHelper = new DBHelper(this);

		Button btn_save = (Button) findViewById(R.id.button_save);
		Button btn_cancel = (Button) findViewById(R.id.button_cancel);

		btn_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				EditText edt_name = (EditText) findViewById(R.id.editText_name);
				EditText edt_ip = (EditText) findViewById(R.id.editText_ip);

				NAME = edt_name.getText().toString();
				IP = edt_ip.getText().toString();

				SQLiteDatabase db;

				db = mHelper.getWritableDatabase();

				ContentValues values = new ContentValues();
				values.put("name", NAME);
				values.put("ip_address", IP);

				db.insert("Pings", null, values);

				edt_name.setText(" ");
				edt_ip.setText(" ");

				Intent intent = new Intent(Addlist.this, ListActivity.class);
				startActivity(intent);
				finish();
			}
		});

		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Addlist.this, ListActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

}

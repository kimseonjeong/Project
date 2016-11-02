package com.example.p1114103_6;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddActivity extends Activity {

	ImageView image;
	Button save, cancel;
	TextView nametv, datetv;
	EditText keywordet, commentet;

	DBadapter dbadapter;

	long ID;
	String NAME, DATE, PATH, KEYWORD, COMMENT = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		// TODO Auto-generated method stub

		dbadapter = new DBadapter(this);

		ID = getIntent().getExtras().getLong("ID");
		NAME = getIntent().getExtras().getString("title");
		DATE = getIntent().getExtras().getString("date");
		PATH = getIntent().getExtras().getString("path");

		image = (ImageView) findViewById(R.id.imgageview1);

		Uri uri = ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI,
				ID);
		try {
			Bitmap bm = Images.Media.getBitmap(getContentResolver(), uri); // Bitmap
																			// ·Îµå
			image.setImageBitmap(bm);
		} catch (Exception e) {
		}

		nametv = (TextView)findViewById(R.id.nametv);
		datetv = (TextView)findViewById(R.id.datetv);
		
		nametv.setText(NAME);
		datetv.setText(DATE);
		
		save = (Button) findViewById(R.id.savebtn);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				keywordet = (EditText) findViewById(R.id.keywordet);
				commentet = (EditText) findViewById(R.id.commentet);

				KEYWORD = keywordet.getText().toString();
				COMMENT = commentet.getText().toString();

				SQLiteDatabase db;

				db = dbadapter.getWritableDatabase();

				ContentValues values = new ContentValues();
				values.put("id", ID);
				values.put("name", NAME);
				values.put("date", DATE);
				values.put("path", PATH);
				values.put("keyword", KEYWORD);
				values.put("comment", COMMENT);

				db.insert("Activity", null, values);

				Intent intent = new Intent(AddActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		});

		cancel = (Button) findViewById(R.id.cancelbtn);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		});
	}

}

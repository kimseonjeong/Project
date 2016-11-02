package com.example.p1114103_6;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

	Button addbtn, searchbtn;
	ListView list;
	EditText searchet;

	DBadapter dbadapter;
	SimpleCursorAdapter adapter;
	Cursor cursor;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ////////추가/////////
		addbtn = (Button) findViewById(R.id.addbtn);
		addbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this, AddActivity.class));
				finish();
			}

		});

		// ////리스트 보여주는거/////////
		final Context context = this;

		dbadapter = new DBadapter(this);

		db = dbadapter.getWritableDatabase();

		cursor = db.rawQuery("SELECT * FROM Activity", null);

		startManagingCursor(cursor);

		adapter = null;
		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, new String[] {
						"name", "date" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		list = (ListView) findViewById(R.id.listview);
		list.setAdapter(adapter);

		// ///////리스트에서 길게 눌렀을 때 삭제///////////
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1,
					int position, final long arg3) {
				// TODO Auto-generated method stub
				CharSequence[] items = { "DELETE" };

				AlertDialog.Builder alertDlg = new AlertDialog.Builder(arg1
						.getContext());
				alertDlg.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						SQLiteDatabase db = dbadapter.getWritableDatabase();

						// TODO Auto-generated method stub
						switch (which) {
						case 0: // delete
							String q = "DELETE FROM Activity WHERE _id = '"
									+ arg3 + "'";
							db.execSQL(q);
							cursor.requery();
							break;

						default:
							break;
						}
					}
				});
				alertDlg.show();
				return false;
			}

		});

		////////////리스트에서 눌렀을 때///////////
		//startActivity(new Intent(MainActivity.this, AddActivity.class));
		list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, modifyActivity.class);
				intent.putExtra("id", Long.toString(id));
				startActivity(intent);
				finish();
			}
			
		});
		
		// ///////검색/////////
		searchet = (EditText) findViewById(R.id.searchet);
		String searchstr = searchet.getText().toString();

		searchbtn = (Button) findViewById(R.id.searchbtn);
		searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}

		});
	}

}

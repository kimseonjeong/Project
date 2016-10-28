package com.example.pingsmouse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.example.pingsmouse.MouseActivity.Client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class ListActivity extends Activity implements OnClickListener {

	DBHelper mHelper;
	Cursor cursor;
	Addlist ADD;

	Client client;

	String IP;

	int w, h;

	private final Handler handler = new Handler();
	private ListActivity current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list); 

//		Display display = ((WindowManager) this
//				.getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
//		w = display.getWidth();
//		h = display.getHeight();
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		w = displayMetrics.widthPixels;
		h = displayMetrics.heightPixels;

		current = this;

		// db연결
		mHelper = new DBHelper(this);

		final SQLiteDatabase db = mHelper.getWritableDatabase();

		// 커서 연결
		cursor = db.rawQuery("SELECT * FROM Pings", null);
		// 자동 정렬
		startManagingCursor(cursor);

		// 어댑터 연결
		SimpleCursorAdapter Adapter = null;
		Adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, new String[] {
						"name", "ip_address" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		ListView listview = (ListView) findViewById(R.id.listview);
		listview.setAdapter(Adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// //////////db에서 ip꺼내오기//////
				String c_q = "SELECT * FROM Pings WHERE _id='" + arg3 + "'";

				cursor = db.rawQuery(c_q, null);

				String list_name = "";
				String list_ip = "";

				if (cursor.moveToNext()) {
					int name_idx = cursor.getColumnIndex("name");
					list_name = cursor.getString(name_idx);
					int ip_idx = cursor.getColumnIndex("ip_address");
					list_ip = cursor.getString(ip_idx);
				}

				IP = list_ip;

//				w = (w / 100) - 1;
//				h = h / 100;

				String display = "_" + w + "_" + h + "_";
				byte[] displaytostr = display.getBytes();

				client = new Client(displaytostr);
				client.execute();

				startActivity(new Intent(ListActivity.this, MouseActivity.class)
						.putExtra("ip", list_ip));
				finish();

			}

		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, final long arg3) {

				CharSequence[] items = { "MODIFY", "DELETE" };

				AlertDialog.Builder alertDlg = new AlertDialog.Builder(arg1
						.getContext());
				alertDlg.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch (which) {
						case 0: // modify
							Intent intent = new Intent(ListActivity.this,
									Modify.class);
							intent.putExtra("arg3", Long.toString(arg3));
							startActivity(intent);
							break;

						case 1: // delete
							String q = "DELETE FROM Pings WHERE _id = '" + arg3
									+ "'";
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

		ImageButton btn_add = (ImageButton) findViewById(R.id.imbtn_add);
		ImageButton btn_help = (ImageButton) findViewById(R.id.imbtn_help);
		ImageButton btn_trash = (ImageButton) findViewById(R.id.imbtn_trash);

		btn_add.setOnClickListener(this);
		btn_help.setOnClickListener(this);
		btn_trash.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ListActivity.this)
						.setTitle("전체 삭제하시겠습니까?")
						.setPositiveButton(
								"확인",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										String r = "DELETE FROM Pings;";
										db.execSQL(r);
										cursor.requery();
									}

								})
						.setNegativeButton(
								"취소",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}

								}).show();

			}

		});

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.imbtn_add) {
			Intent intent = new Intent(ListActivity.this, Addlist.class);
			startActivity(intent);
		}

		if (v.getId() == R.id.imbtn_help) {
			startActivity(new Intent(ListActivity.this, Helpslide.class));
		}

	}

	// ////socket//////////
	public class Client extends AsyncTask {

		byte[] number;

		public Client(byte[] number) {
			this.number = number;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			try {
				Log.d("TCP", "server connecting");
				InetAddress serverAddr = InetAddress.getByName(IP);
				Socket sock = new Socket(serverAddr, 7007);

				DataInputStream input = new DataInputStream(
						sock.getInputStream());
				DataOutputStream output = new DataOutputStream(
						sock.getOutputStream());

				try {
					// 데이터 송신 부분!
					if (sock.isConnected()) {
						output.write(number);
					}
				} catch (IOException e) {

				}

			} catch (UnknownHostException e) {

			} catch (IOException e) {

			}

			return null;
		}

	}

}

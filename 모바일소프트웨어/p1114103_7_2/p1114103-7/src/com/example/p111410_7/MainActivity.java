package com.example.p111410_7;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText textTIme;
	int time, index;
	EditText editText;
	private Long mRowId;
	private DBadapter mDbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mDbHelper = new DBadapter(this);
		mDbHelper.open();
		mRowId = (savedInstanceState == null) ? null
				: (Long) savedInstanceState.getSerializable(DBadapter._ID);
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();
			mRowId = extras != null ? extras.getLong(DBadapter._ID) : null;
		}
		Cursor c = managedQuery(Uri.parse("content://sms/inbox"), null, null,
				null, null);

		int addressIdx = c.getColumnIndex("address");
		int dateIdx = c.getColumnIndex("date");
		String number = null;
		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");

		if (c.moveToFirst())
			do {
				number = c.getString(addressIdx);
				date = sdf.format(new Date(c.getLong(dateIdx)));
				Log.i("ii", date);
				if (mRowId == null) {
					long id = mDbHelper.insertNote(number, date);
					if (id > 0) {
						mRowId = id;
					}
				} else {
					mDbHelper.updateNote(number, date, mRowId);
				}

			} while (c.moveToNext());
		stopManagingCursor(c);

	}

	public void mOnclick(View v) {
		mDbHelper.open();
		int flag = -1;
		ArrayList<String> arr = new ArrayList<String>();
		editText = (EditText) this.findViewById(R.id.getPhone);
		String phone = editText.getText().toString();
		AlarmManager al = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent intent;
		PendingIntent sender;

		switch (v.getId()) {
		case R.id.onetime:
			arr = mDbHelper.fetchNode(1);
			for (int i = 0; i < arr.size(); i++) {
				if (arr.get(i).equals(phone)) {
					flag = 0;
					index = i;
				}
			}
			if (flag == 0) {
				intent = new Intent(this, IncomingSms.class);
				sender = PendingIntent.getBroadcast(this, 0, intent, 0);
	
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());

				calendar.add(calendar.SECOND, 10);

				al.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
			}
			break;

		}
	}

}

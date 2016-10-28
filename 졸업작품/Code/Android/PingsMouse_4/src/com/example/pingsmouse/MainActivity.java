package com.example.pingsmouse;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				startActivity(new Intent(MainActivity.this, ListActivity.class));
				// listâ���� ��ȯ
				finish();
			}
		};

		handler.sendEmptyMessageDelayed(0, 1000); // 1���� ȭ�� �ڵ� ��ȯ

	}

}

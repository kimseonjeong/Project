package com.example.p111410_7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Toast.makeText(context, "메시지를 확인하세요.10분이 지났습니다. ", Toast.LENGTH_SHORT).show();
	}
	

}

package com.example.p111410_7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		Toast.makeText(context, "�޽����� Ȯ���ϼ���.10���� �������ϴ�. ", Toast.LENGTH_SHORT).show();
	}
	

}

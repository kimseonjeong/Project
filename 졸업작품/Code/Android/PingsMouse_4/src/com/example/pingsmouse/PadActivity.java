package com.example.pingsmouse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Bundle;

public class PadActivity extends Activity {

	TouchEventView touch;
	Client client;

	// /////socket ip주소
	String IP;

	// 시간측정!!!!!!
	// long lastUpdate = -1;
	long mLastTime;

	// x축 이동거리, y축 이동거리
	int initial_x;
	int initial_y;
	int last_x;
	int last_y;

	PadActivity current;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		touch = new TouchEventView(this);
		setContentView(touch);

		current = this;

		// //////list에서 ip에서 받아옴!!!!!!!!!//////////
		String ip = getIntent().getExtras().getString("ip");
		// socket에서 ip적용
		IP = ip;

		// socket 객체 생성//////////////////
		client = new Client();
		client.execute();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "List로 가기");
		menu.add(0, 2, 0, "Snap로 가기");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == 1) {
			client.close();
			startActivity(new Intent(PadActivity.this, ListActivity.class));
			finish();
		}
		if (item.getItemId() == 2) {
			client.close();
			startActivity(new Intent(PadActivity.this, MouseActivity.class)
					.putExtra("ip", IP));
			finish();
		}

		return true;
	}

	public class TouchEventView extends View {

		int x; // x좌표
		int y; // y좌표

		String str = "없음"; // touch mode

		Bitmap background;

		long curtime;
		long lasttime;

		// long time1;
		// long time2;
		// long time3;

		long lastUpdate = -1;
		long curTime;

		int down_x, down_y, up_x, up_y;

		int pad_state = 0;

		int state = 1; // touch mode

		public TouchEventView(Context context) {
			super(context);
			background = BitmapFactory.decodeResource(getResources(),
					R.drawable.pad_background);
		}

		protected void onDraw(Canvas canvas) {
			Paint p = new Paint();
			p.setTextSize(30);
			p.setColor(Color.WHITE);

			canvas.drawBitmap(background, 0, 0, null);
			canvas.drawText("X :" + x + " Y :" + y, 45, 70, p);

		}

		public boolean onTouchEvent(MotionEvent event) {

			x = (int) event.getX();
			y = (int) event.getY();

			// time1 = System.currentTimeMillis();

			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				str = "ACTION_DOWN";

				state = 2;

				down_x = x;
				down_y = y;

				curtime = System.currentTimeMillis();

				client.send("DD");

				initial_x = x;
				initial_y = y;

				if (pad_state == 0) {
					last_x = x;
					last_y = y;
				}

				pad_state = 1;

			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				str = "ACTION_MOVE";

				state = 3;

				last_x = x;
				last_y = y;

				pad_state = 0;

			}

			if (event.getAction() == MotionEvent.ACTION_UP) {
				str = "ACTION_UP";

				up_x = x;
				up_y = y;

				lasttime = System.currentTimeMillis();

			}

			// ////왼쪽클릭, 오른쪽 클릭///
			if (down_x == up_x && down_y == up_y) {
				if ((lasttime - curtime) > 500)
					client.send("MR");
				else
					client.send("ML");
			}

			// ////패드 상대좌표 보내기//////
			int xx = (int)((last_x - initial_x)*2.3);
			int yy = (int)((last_y - initial_y)*2.3);

			String pad = "P/" + xx + "/" + yy + "/";
			String pad1 = "initial x" + initial_x + "last x" + last_x + "/";
			String pad2 = "initial y" + initial_y + "last y" + last_y + "/";

			if (pad_state != 1)
				client.send(pad);

			pad_state = 0;

			initial_x = last_x;
			initial_y = last_y;

			Log.d("pad", pad);
			Log.d("pad", pad1);
			Log.d("pad", pad2);

			invalidate(); // 화면 갱신 (새로고침)

			return true;
		}

	}

	public class Client extends AsyncTask<Void, byte[], Boolean> {
		Socket socket; // Network Socket
		InputStream nis; // Network Input Stream
		OutputStream nos; // Network Output Stream

		protected Boolean doInBackground(Void... params) { // This runs on a
															// different thread
			boolean result = false;
			try {
				Log.i("AsyncTask", "doInBackground: Creating socket");
				SocketAddress sockaddr = new InetSocketAddress(IP, 7007);
				socket = new Socket();
				socket.connect(sockaddr); // 10 second connection

				if (socket.isConnected()) {
					nis = socket.getInputStream();
					nos = socket.getOutputStream();
					Log.i("AsyncTask",
							"doInBackground: Socket created, streams assigned");
					Log.i("AsyncTask",
							"doInBackground: Waiting for inital data...");
					byte[] buffer = new byte[4096];
					int read = nis.read(buffer, 0, 4096); // This is blocking
					while (read != -1) {
						byte[] tempdata = new byte[read];
						System.arraycopy(buffer, 0, tempdata, 0, read);
						publishProgress(tempdata);
						Log.i("AsyncTask", "doInBackground: Got some data");
						read = nis.read(buffer, 0, 4096); // This is blocking
					}
				}
			} catch (IOException e) {

				// Log.i("AsyncTask", "doInBackground: IOException");

				result = true;
			} catch (Exception e) {

				// Log.i("AsyncTask", "doInBackground: Exception");

				result = true;
			} finally {
				try {
					nis.close();
					nos.close();
					socket.close();
				} catch (IOException e) {

				} catch (Exception e) {

				}
				// Log.i("AsyncTask", "doInBackground: Finished");
			}

			return result;
		}

		public void send(String cmd) { // You run this from the main thread.
			try {
				if (socket.isConnected()) {
					// Log.i("AsyncTask",
					// "SendDataToNetwork: Writing received message to socket");

					nos.write(cmd.getBytes());
				} else {
					// Log.i("AsyncTask",
					// "SendDataToNetwork: Cannot send message. Socket is closed");
				}
			} catch (Exception e) {
				// Log.i("AsyncTask",
				// "SendDataToNetwork: Message send failed. Caught an exception");

			}
		}

		public void close() {
			try {
				nis.close();
				nos.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block

			}

		}

	}

}

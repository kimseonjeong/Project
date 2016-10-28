package com.example.pingsmouse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;
import android.os.Bundle;

public class MouseActivity extends Activity implements OnClickListener,
		SensorEventListener {

	// ///버튼!
	ImageButton btn_point, btn_leftmouse, btn_wheel_up, btn_wheel_down,
			btn_rightmouse, btn_keyboard, btn_next, button_keyboard,btn_list;
	InputMethodManager Imm;
	// 키보드 띄우기 위한 edittext
	EditText et01;
	int key_state = 0;

	// ///////실제 ip주소!!!!!!!!!!!!!
	String IP;

	InputMethodManager imm;
	char str;

	// /자이로 센서를 보내기 위한 것들
	SensorManager sensorManager;
	int PINGS_SIZE = 50;
	List<Sensor> sensors;

	int w;
	int h;
	float x;
	float y;

	int weight;
	int height;

	// 시간 계산을 위한 변수
	long lastUpdate = -1;

	// ///socket 객체 생성
	Client client;

	MouseActivity current;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN
						| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// jw(getWindow)

		setContentView(R.layout.mouse);

		current = this;

		// ////list activity에서 ip주소 넘겨진 string/////////
		final String dbtoip = getIntent().getExtras().getString("ip");
		IP = dbtoip;

		// //socket 객체 생성
		client = new Client();
		client.execute();

		// //해상도 체크
		Display display = ((WindowManager) this
				.getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
		w = display.getWidth();
		h = display.getHeight();

		this.x = (w - PINGS_SIZE) / 2f;
		this.y = (h - PINGS_SIZE) / 2f;

		// //키보드 띄우기
		Imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		// //자이로 센서
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		sensors = sensorManager
				.getSensorList(Sensor.TYPE_ACCELEROMETER);

		if (sensors.size() > 0) {
			sensorManager.registerListener(this, sensors.get(0),
					SensorManager.SENSOR_DELAY_GAME);
		}

		// ///버튼!
		EditText e = new EditText(this);
		et01 = (EditText) findViewById(R.id.et01);
		button_keyboard = (ImageButton) findViewById(R.id.keyboard);
		button_keyboard.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, 0);

				key_state++;
				
				/////////////자이로 확인//////////////
				if(key_state % 2 != 0)
					onDestroy();
				else if(key_state % 2 == 0)
					onStart();
			}
			
		});
		
		// 키보드 여기서부터
		et01.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String a = s.toString();
				client.send("K" + a);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				et01.removeTextChangedListener(this);
				et01.setText("");
				et01.addTextChangedListener(this);

			}

		});

		et01.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN) {

					int key = event.getUnicodeChar();
					String keytostr = Integer.toString(key);
					client.send("k" + keytostr);

					et01.setText("");

					return true;
				}
				
				return false;
			}

		}); // 엔터를 눌렀을 때만 이동함
		// 엔터는 10 백스페이스는 0

		// 여기까지

		btn_point = (ImageButton) findViewById(R.id.imbtn_point);
		btn_point.setOnClickListener(this);

		btn_leftmouse = (ImageButton) findViewById(R.id.imbtn_leftmouse);
		btn_leftmouse.setOnClickListener(this);

		btn_wheel_up = (ImageButton) findViewById(R.id.imbtn_wheel_up);
		btn_wheel_up.setOnClickListener(this);

		btn_wheel_down = (ImageButton) findViewById(R.id.imbtn_wheel_down);
		btn_wheel_down.setOnClickListener(this);

		btn_rightmouse = (ImageButton) findViewById(R.id.imbtn_rightmouse);
		btn_rightmouse.setOnClickListener(this);

		btn_list = (ImageButton) findViewById(R.id.imbtn_gotolist);
		btn_list.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0){
				client.close();

				Intent intent = new Intent(MouseActivity.this,
						ListActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		btn_next = (ImageButton) findViewById(R.id.imbtn_next);
		btn_next.setOnClickListener(new OnClickListener() {
			// /////패드 넘기기(ip주소랑 같이)////////////
			@Override
			public void onClick(View arg0) {

				client.close();

				Intent intent = new Intent(MouseActivity.this,
						PadActivity.class);
				intent.putExtra("ip", dbtoip);
				startActivity(intent);
				finish();

			}

		});
	}
	
	// //자이로센서//////
	@Override
	protected void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this);
	}
	
	protected void onStart(){
		super.onStart();
		sensorManager.registerListener(this, sensors.get(0),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onAccuracyChanged(final Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(final SensorEvent event) {
		Sensor sensor = event.sensor;

		switch (sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:

			move(event.values[0], event.values[1]);
			break;
		default:
			break;
		}
	}

	public void move(float mx, float my) {

		this.x -= (mx * 4f);
		this.y += (my * 4f);

		if (this.x < 0) {
			this.x = 0;
		} else if ((this.x + PINGS_SIZE) > this.w) {
			this.x = this.w - PINGS_SIZE;
		}
		if (this.y < 0) {
			this.y = 0;
		} else if ((this.y + PINGS_SIZE) > this.h) {
			this.y = this.h - PINGS_SIZE;
		}

		//Log.d("SENSOR", "x: " + x + " y: " + y);

		long curTime = System.currentTimeMillis();

		if ((curTime - lastUpdate) > 10) {
			long diffTime = (curTime - lastUpdate);
			lastUpdate = curTime;

			int xx = (int) x;
			int yy = (int) y;

			String xx_string = Integer.toString(xx);
			String yy_string = Integer.toString(yy);
			String xy_string = "M/" + xx_string + "/" + yy_string + "/";

			// 좌표 보내기
			client.send(xy_string);
		}

	}

	// //버튼들!
	@Override
	public void onClick(View v) {

		
		
		if (v.getId() == R.id.imbtn_point) {
			client.send("MP");
		}
		if (v.getId() == R.id.imbtn_leftmouse) {
			client.send("ML");
		}
		if (v.getId() == R.id.imbtn_wheel_up) {
			client.send("MU");
		}
		if (v.getId() == R.id.imbtn_wheel_down) {
			client.send("MD");
		}
		if (v.getId() == R.id.imbtn_rightmouse) {
			client.send("MR");
		}

	}

	// ///////socket///////////
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
				socket.connect(sockaddr); // 10 second connection timeout
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
			
//				Log.i("AsyncTask", "doInBackground: IOException");

				result = true;
			} catch (Exception e) {
				
//				Log.i("AsyncTask", "doInBackground: Exception");

				result = true;
			} finally {
				try {
					nis.close();
					nos.close();
					socket.close();
				} catch (IOException e) {
				
				} catch (Exception e) {
					
				}
//				Log.i("AsyncTask", "doInBackground: Finished");
			}
			return result;
		}

		public void send(String cmd) { // You run this from the main thread.
			try {
				if (socket.isConnected()) {
//					Log.i("AsyncTask",
//							"SendDataToNetwork: Writing received message to socket");
					nos.write(cmd.getBytes());
				} else {
//					Log.i("AsyncTask",
//							"SendDataToNetwork: Cannot send message. Socket is closed");
				}
			} catch (Exception e) {
//				Log.i("AsyncTask",
//						"SendDataToNetwork: Message send failed. Caught an exception");

			}
		}

		public void close() {
			try {
				nis.close();
				nos.close();
				socket.close();
			} catch (IOException e) {
				
			}

		}

	}

}

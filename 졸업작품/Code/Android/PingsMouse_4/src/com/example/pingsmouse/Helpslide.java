package com.example.pingsmouse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Helpslide extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    SlidingView sv = new SlidingView(this);
	    
	    View v1 = View.inflate(this, R.layout.help1, null);
	    View v2 = View.inflate(this, R.layout.help2, null);
	    View v3 = View.inflate(this, R.layout.help3, null);
	    View v4 = View.inflate(this, R.layout.help4, null);
	    
	    sv.addView(v1);
	    sv.addView(v2);
	    sv.addView(v3);
	    sv.addView(v4);
	    
	    setContentView(sv);
	}

}

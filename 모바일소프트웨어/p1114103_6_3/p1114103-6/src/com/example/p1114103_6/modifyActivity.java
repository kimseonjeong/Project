package com.example.p1114103_6;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class modifyActivity extends Activity {

	ImageView image;
	TextView nametv, datetv, keywordtv, commenttv;
	
	DBadapter dbadapter;
	String NAME, DATE, KEYWORD, COMMENT = "";
	
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.confirm);
	    // TODO Auto-generated method stub
	    
	    dbadapter = new DBadapter(this);
	    
	    String id = getIntent().getExtras().getString("id");
	    int _id = Integer.parseInt(id);
	    
	    String query = "SELECT * FROM Activity WHERE _id = '" + _id + "'";
	    
	    //////////이미지 보이기///////////////
	    image = (ImageView)findViewById(R.id.imgageview);
	    
	    nametv = (TextView)findViewById(R.id.nametv);
	    datetv = (TextView)findViewById(R.id.datetv);
	    keywordtv = (TextView)findViewById(R.id.keywordtv);
	    commenttv = (TextView)findViewById(R.id.commenttv);
	    
	    SQLiteDatabase db;
	    
	    db = dbadapter.getWritableDatabase();
	    Cursor cursor = db.rawQuery(query, null);
	    
	    String name_ = "";
	    String date_ = "";
	    String keyword_ = "";
	    String comment_ = "";
	    
	    if(cursor.moveToNext()){
	    	int name_idx = cursor.getColumnIndex("name");
	    	name_ = cursor.getString(name_idx);
	    	int date_idx = cursor.getColumnIndex("date");
	    	date_ = cursor.getString(date_idx);
	    	int keyword_idx = cursor.getColumnIndex("keyword");
	    	keyword_ = cursor.getString(keyword_idx);
	    	int comment_idx = cursor.getColumnIndex("comment");
	    	comment_ = cursor.getString(comment_idx);
	    }
	    
	    nametv.setText(name_);
	    datetv.setText(date_);
	    keywordtv.setText(keyword_);
	    commenttv.setText(comment_);
	}

}

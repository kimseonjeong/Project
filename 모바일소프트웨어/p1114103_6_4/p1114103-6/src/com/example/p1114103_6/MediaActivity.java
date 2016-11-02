package com.example.p1114103_6;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MediaActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media);

		setTitle("사진을 선택하세요");
		
		final Cursor c = getContentResolver().query(
				Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		startManagingCursor(c);

		ImageCursorAdapter adapter = new ImageCursorAdapter(this, c);
		Gallery gallery = (Gallery) findViewById(R.id.gallery);

		gallery.setAdapter(adapter);
		
		gallery.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				c.moveToPosition(position);
				String Path = c.getString(c.getColumnIndex(Images.Media.DATA));
				String Title = c.getString(c.getColumnIndex(Images.Media.DISPLAY_NAME));
				String Date = c.getString(c.getColumnIndex(Images.Media.DATE_ADDED));
				Long Id = c.getLong(c.getColumnIndex(Images.Media._ID));
				
				Intent intent = new Intent(MediaActivity.this,AddActivity.class);
				intent.putExtra("path", Path);
				intent.putExtra("title", Title);
				intent.putExtra("date", Date);
				intent.putExtra("ID", Id);
				startActivity(intent);
				finish();
			}
			
		});
	}

	// 커스텀 Apater 클래스 설계
	class ImageCursorAdapter extends CursorAdapter {
		public ImageCursorAdapter(Context context, Cursor c) {
			super(context, c);
		}

		// 커서에서 데이터를 받아서 뷰에 연결시키는 작업
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ImageView img = (ImageView) view;
			int index = cursor.getColumnIndex(Images.Media._ID);
			long id = cursor.getLong(index);
			// 개별 이미지에 대한 URI 생성
			Uri uri = ContentUris.withAppendedId(
					Images.Media.EXTERNAL_CONTENT_URI, id);

			try {
				Bitmap bm = Images.Media.getBitmap(getContentResolver(), uri); // Bitmap
																				// 로드
				img.setImageBitmap(bm);
			} catch (Exception e) {
			}
		}

		// 새로운 View를 추가하며 어떤 레이아웃을 구성할 것인지를 결정
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			ImageView view = new ImageView(context);
			view.setLayoutParams(new Gallery.LayoutParams(80, 80));
			view.setScaleType(ImageView.ScaleType.FIT_CENTER);
			return view;
		}
	}
}

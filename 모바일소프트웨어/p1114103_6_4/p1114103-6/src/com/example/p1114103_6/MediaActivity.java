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

		setTitle("������ �����ϼ���");
		
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

	// Ŀ���� Apater Ŭ���� ����
	class ImageCursorAdapter extends CursorAdapter {
		public ImageCursorAdapter(Context context, Cursor c) {
			super(context, c);
		}

		// Ŀ������ �����͸� �޾Ƽ� �信 �����Ű�� �۾�
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			ImageView img = (ImageView) view;
			int index = cursor.getColumnIndex(Images.Media._ID);
			long id = cursor.getLong(index);
			// ���� �̹����� ���� URI ����
			Uri uri = ContentUris.withAppendedId(
					Images.Media.EXTERNAL_CONTENT_URI, id);

			try {
				Bitmap bm = Images.Media.getBitmap(getContentResolver(), uri); // Bitmap
																				// �ε�
				img.setImageBitmap(bm);
			} catch (Exception e) {
			}
		}

		// ���ο� View�� �߰��ϸ� � ���̾ƿ��� ������ �������� ����
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			ImageView view = new ImageView(context);
			view.setLayoutParams(new Gallery.LayoutParams(80, 80));
			view.setScaleType(ImageView.ScaleType.FIT_CENTER);
			return view;
		}
	}
}

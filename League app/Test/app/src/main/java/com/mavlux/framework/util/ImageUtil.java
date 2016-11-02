

package com.mavlux.framework.util;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;


public class ImageUtil {
	
	public static final Bitmap maskBitmap( Bitmap bitmap, Bitmap maskBitmap ) {
	
		int maxWidth = Math.max(bitmap.getWidth(), maskBitmap.getWidth());
		int macHeight = Math.max(bitmap.getHeight(), maskBitmap.getHeight());
		
		try {
			
			Bitmap resultBitmap = Bitmap.createBitmap(maxWidth, macHeight, Bitmap.Config.ARGB_8888);
			Canvas localCanvas = new Canvas( resultBitmap );
			
			localCanvas.drawBitmap( bitmap, new Rect( 0, 0, bitmap.getWidth(), bitmap.getHeight() ), new Rect( 0, 0, maxWidth, macHeight ), null );
			
			Paint localPaint = new Paint();
			localPaint.setFilterBitmap( false );
			localPaint.setXfermode( new PorterDuffXfermode( PorterDuff.Mode.DST_IN ) );
			
			localCanvas.drawBitmap( maskBitmap, new Rect( 0, 0, maskBitmap.getWidth(), maskBitmap.getHeight() ), new Rect( 0, 0, maxWidth, macHeight ), localPaint );
			
			return resultBitmap;
		}
		catch ( Exception localException ) {
			
			return null;
		}
	}
	
	public static final Bitmap convertDrawable( Drawable drawable, int width, int height ) {
	
		Drawable localDrawable = null;
		
		if ( drawable instanceof NinePatchDrawable) {
			
			localDrawable = drawable.mutate();
			( (NinePatchDrawable) localDrawable ).setBounds( 0, 0, width, height );
		}
		while ( true ) {
			
			try {
				
				Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
				localDrawable.draw( new Canvas( resultBitmap ) );
				
				return resultBitmap;
			}
			catch ( Exception localException ) {
				
				return null;
			}
			catch ( Error localError ) {
				
				return null;
			}
		}
	}
	
}

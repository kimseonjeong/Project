package com.example.pingsmouse;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

public class SlidingView extends ViewGroup {
	private static final String TAG = "SlidingView";

	// �巡�� �ӵ��� ������ �Ǵ��ϴ� Ŭ����
	private VelocityTracker mVelocityTracker = null;

	// ȭ�� ��ȯ�� ���� �巡�� �ӵ��� �ּҰ� pixel/s (100 ������ �ӵ��� �̵��ϸ� ȭ����ȯ���� �ν�)
	private static final int SNAP_VELOCITY = 100;

	// ȭ�鿡 ���� ��ġ�̹�Ʈ�� ȭ�� ��ȯ�� ���� ��ġ���� �� ȭ���� ���������� ���� ��ġ������ �����ϴ� ��
	// (�������¿��� 10px �̵��ϸ� ȭ�� �̵����� �ν�)
	private int mTouchSlop = 10;
	private Bitmap mWallpaper = null;// ���ȭ���� ���� ��Ʈ��
	private Paint mPaint = null;

	// ȭ�� �ڵ� ��ȯ�� ���� �ٽ� Ŭ���� (ȭ�� �巡���� ���� ���� �� ȭ�� ��ȯ�̳� ���� ȭ������ �ڵ����� ��ũ�� �Ǵ� ���� �����ϴ�
	// Ŭ����
	private Scroller mScroller = null;
	private PointF mLastPoint = null;// ������ ��ġ ������ �����ϴ� Ŭ����
	private int mCurPage = 0;// ���� ȭ�� ������

	private int mCurTouchState;// ���� ��ġ�� ����
	private static final int TOUCH_STATE_SCROLLING = 0;// ���� ��ũ�� ���̶�� ����
	private static final int TOUCH_STATE_NORMAL = 1;// ���� ��ũ�� ���°� �ƴ�

	public SlidingView(Context context) {
		super(context);
		init();
	}

	public SlidingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public SlidingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		mWallpaper = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		mPaint = new Paint();
		mScroller = new Scroller(getContext());// ��ũ�ѷ� Ŭ���� ����
		mLastPoint = new PointF();
	}

	// ���ϵ���� ũ�⸦ �����ϴ� �ݹ� �޼���
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(TAG, "onMeasure");
		for (int i = 0; i < getChildCount(); i++) {
			// �� ���ϵ���� ũ��� �����ϰ� ����
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	// ���ϵ���� ��ġ�� �����ϴ� �ݹ� �޼���
	protected void onLayout(boolean changed, int I, int t, int r, int b) {
		Log.d(TAG, "onLayout");
		// �ٽ� ���� �κ�, ���ϵ����� ���ʴ�� ��ġ�� �ʰ� ������ �����ؼ� ��ġ�� ��ġ
		// ������ ���ʴ�� ��ġ�� �س��� ��ũ���� ���� ������ �̵��ϴ� ���� ����
		for (int i = 0; i < getChildCount(); i++) {
			int w = getChildAt(i).getMeasuredWidth() * i;
			getChildAt(i).layout(w, t, w + getChildAt(i).getMeasuredWidth(),
					getChildAt(i).getMeasuredHeight());

		}
	}

	// viewgroup Ŭ������ onDraw �޼���� �����ϸ� ��
	protected void dispatichDraw(Canvas canvas) {
		canvas.drawBitmap(mWallpaper, 0, 0, mPaint); // ����ȭ��
		for (int i = 0; i < getChildCount(); i++) {
			drawChild(canvas, getChildAt(i), 100); // ���ϵ� ����� �ϳ� �ϳ� �׸���
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "event Action : " + event.getAction());
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();

		// ��ġ�Ǵ� ��� ��ǥ���� ����, ��ġ �巡�� �ӵ��� �Ǵ��ϴ� ���ʸ� �����
		mVelocityTracker.addMovement(event);

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			// ���� ȭ���� �ڵ� ��ũ�� ���̶�� (ACTION_UP�� mScroller �κ� ����)
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();// �ڵ���ũ�� �����ϰ� ��ġ ������ ���缭 ���� ��
			}
			mLastPoint.set(event.getX(), event.getY());// ��ġ���� ����
			break;

		case MotionEvent.ACTION_MOVE:
			// ���� ��ġ������ ���� ��ġ������ ���̸� ���ؼ� ȭ�� ��ũ�� �ϴµ� �̵�
			int x = (int) (event.getX() - mLastPoint.x);
			scrollBy(-x, 0);// ���̸�ŭ ȭ�� ��ũ��
			invalidate();// �ٽ� �׸���
			mLastPoint.set(event.getX(), event.getY());
			break;

		case MotionEvent.ACTION_UP:
			// pixel/ms ������ �巡�� �ӵ��� ���Ұ��ΰ� ����(1�ʷ� ����)
			mVelocityTracker.computeCurrentVelocity(1000);
			int v = (int) mVelocityTracker.getXVelocity();
			int gap = getScrollX() - mCurPage * getWidth();
			Log.d(TAG, "mVelocityTracker : " + v);
			int nextPage = mCurPage;

			if ((v > SNAP_VELOCITY || gap < -getWidth() / 2) && mCurPage > 0) {
				nextPage--;
			} else if ((v < -SNAP_VELOCITY || gap > getWidth() / 2)
					&& mCurPage < getChildCount() - 1) {
				nextPage++;
			}

			int move = 0;
			if (mCurPage != nextPage) {
				move = getChildAt(0).getWidth() * nextPage - getScrollX();
			} else {
				move = getWidth() * mCurPage - getScrollX();
			}

			mScroller.startScroll(getScrollX(), 0, move, 0, Math.abs(move));

			invalidate();
			mCurPage = nextPage;

			mCurTouchState = TOUCH_STATE_NORMAL;
			mVelocityTracker.recycle();
			mVelocityTracker = null;
			break;
		}
		return true;
	}

	// �ٽ�. �� �ݹ� �޼���, ��ũ�� �ɶ� ���� ������ ����
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidate();
		}
	}

	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.d(TAG, "onInterceotTouchEvent : " + ev.getAction());
		int action = ev.getAction();
		int x = (int) ev.getX();
		int y = (int) ev.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mCurTouchState = mScroller.isFinished() ? TOUCH_STATE_NORMAL
					: TOUCH_STATE_SCROLLING;
			mLastPoint.set(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			int move_x = Math.abs(x - (int) mLastPoint.x);
			if (move_x > mTouchSlop) {
				mCurTouchState = TOUCH_STATE_SCROLLING;
				mLastPoint.set(x, y);
			}
			break;
		}

		return mCurTouchState == TOUCH_STATE_SCROLLING;
	}

}

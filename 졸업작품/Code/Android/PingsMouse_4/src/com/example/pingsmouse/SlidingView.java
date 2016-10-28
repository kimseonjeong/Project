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

	// 드래그 속도와 방향을 판단하는 클래스
	private VelocityTracker mVelocityTracker = null;

	// 화면 전환을 위한 드래그 속도의 최소값 pixel/s (100 정도의 속도로 이동하면 화면전환으로 인식)
	private static final int SNAP_VELOCITY = 100;

	// 화면에 대한 터치이번트가 화면 전환을 위한 터치인지 현 화면의 위젯동작을 위한 터치인지를 구분하는 값
	// (누른상태에서 10px 이동하면 화면 이동으로 인식)
	private int mTouchSlop = 10;
	private Bitmap mWallpaper = null;// 배경화면을 위한 비트맵
	private Paint mPaint = null;

	// 화면 자동 전환을 위한 핵심 클래스 (화면 드래그후 손을 뗏을 때 화면 전환이나 원래 화면으로 자동으로 스크롤 되는 동작 구현하는
	// 클래스
	private Scroller mScroller = null;
	private PointF mLastPoint = null;// 마지막 터치 지점을 저장하는 클래스
	private int mCurPage = 0;// 현재 화면 페이지

	private int mCurTouchState;// 현재 터치의 상태
	private static final int TOUCH_STATE_SCROLLING = 0;// 현재 스크롤 중이라는 상태
	private static final int TOUCH_STATE_NORMAL = 1;// 현재 스크롤 상태가 아님

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
		mScroller = new Scroller(getContext());// 스크롤러 클래스 생성
		mLastPoint = new PointF();
	}

	// 차일드뷰의 크기를 지정하는 콜백 메서드
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d(TAG, "onMeasure");
		for (int i = 0; i < getChildCount(); i++) {
			// 각 차일드뷰의 크기는 동일하게 설정
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	// 차일드뷰의 위치를 지정하는 콜백 메서드
	protected void onLayout(boolean changed, int I, int t, int r, int b) {
		Log.d(TAG, "onLayout");
		// 핵심 구현 부분, 차일드뷰들을 차례대로 겹치지 않게 옆으로 나열해서 위치를 배치
		// 옆으로 차례대로 배치를 해놔야 스크롤을 통해 옆으로 이동하는 것이 가능
		for (int i = 0; i < getChildCount(); i++) {
			int w = getChildAt(i).getMeasuredWidth() * i;
			getChildAt(i).layout(w, t, w + getChildAt(i).getMeasuredWidth(),
					getChildAt(i).getMeasuredHeight());

		}
	}

	// viewgroup 클래스의 onDraw 메서드라 생각하면 됨
	protected void dispatichDraw(Canvas canvas) {
		canvas.drawBitmap(mWallpaper, 0, 0, mPaint); // 바탕화면
		for (int i = 0; i < getChildCount(); i++) {
			drawChild(canvas, getChildAt(i), 100); // 차일드 뷰들을 하나 하나 그리기
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "event Action : " + event.getAction());
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();

		// 터치되는 모든 좌표들을 저장, 터치 드래그 속도를 판단하는 기초를 만들기
		mVelocityTracker.addMovement(event);

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			// 현재 화면이 자동 스크롤 중이라면 (ACTION_UP의 mScroller 부분 참조)
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();// 자동스크롤 중지하고 터치 지점에 멈춰서 있을 것
			}
			mLastPoint.set(event.getX(), event.getY());// 터치지점 저장
			break;

		case MotionEvent.ACTION_MOVE:
			// 이전 터치지점과 현재 터치지점의 차이를 구해서 화면 스크롤 하는데 이동
			int x = (int) (event.getX() - mLastPoint.x);
			scrollBy(-x, 0);// 차이만큼 화면 스크롤
			invalidate();// 다시 그리기
			mLastPoint.set(event.getX(), event.getY());
			break;

		case MotionEvent.ACTION_UP:
			// pixel/ms 단위로 드래그 속도를 구할것인갈 지정(1초로 지정)
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

	// 핵심. 인 콜백 메서드, 스크롤 될때 마다 무조건 실행
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

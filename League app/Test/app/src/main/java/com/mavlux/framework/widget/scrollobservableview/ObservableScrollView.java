package com.mavlux.framework.widget.scrollobservableview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;


public class ObservableScrollView extends ScrollView {

    private int scrollY;

    private float previousTouchMoveY;
    private ScrollState scrollState;

    private OnTouchScrollListener onTouchScrollListener;
    private boolean isLock = false;

    public ObservableScrollView(Context context) {

        this(context, null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);

        scrollY = t;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!isLock) {

            switch (ev.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    touchDown(ev.getRawY());
                    break;

                case MotionEvent.ACTION_MOVE:

                    if (touchMove(ev.getRawY()))
                        return true;
                    else
                        break;
            }

            return super.onInterceptTouchEvent(ev);
        } else {

            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!isLock) {

            switch (ev.getAction()) {

                case MotionEvent.ACTION_MOVE:

                    if (touchMove(ev.getRawY()))
                        return true;
                    else
                        break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:

                    if (touchUp(ev.getRawY()))
                        return true;
                    else
                        break;
            }

            return super.onTouchEvent(ev);
        } else {

            return false;
        }
    }

    private void touchDown(float touchDownY) {

        previousTouchMoveY = touchDownY;
        scrollState = ScrollState.STOP;

        if (null != onTouchScrollListener)
            onTouchScrollListener.onTouchDown(scrollY);
    }

    private boolean touchMove(float touchMoveY) {

        float touchDeltaY = touchMoveY - previousTouchMoveY;
        previousTouchMoveY = touchMoveY;
        scrollState = ScrollState.getScrollState(touchDeltaY);

        if (null != onTouchScrollListener) {

            if (ScrollState.UP == scrollState)
                return onTouchScrollListener.onScrollUp(touchDeltaY, scrollY);
            else if (ScrollState.DOWN == scrollState)
                return onTouchScrollListener.onScrollDown(touchDeltaY, scrollY);
            else
                return onTouchScrollListener.onScrollStop(scrollY);
        } else {

            return false;
        }
    }

    private boolean touchUp(float touchUpY) {

        float touchDeltaY = previousTouchMoveY - touchUpY;
        previousTouchMoveY = touchUpY;
        this.scrollState = ScrollState.STOP;

        if (null != onTouchScrollListener)
            return onTouchScrollListener.onTouchUp(touchDeltaY, scrollY);
        else
            return false;
    }

    public void setOnTouchScrollListener(OnTouchScrollListener onTouchScrollListener) {

        this.onTouchScrollListener = onTouchScrollListener;
    }

    public void setIsLock(boolean isLock) {

        this.isLock = isLock;
    }

}

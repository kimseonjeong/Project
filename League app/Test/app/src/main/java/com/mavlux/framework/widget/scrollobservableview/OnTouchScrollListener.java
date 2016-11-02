package com.mavlux.framework.widget.scrollobservableview;


public interface OnTouchScrollListener {

	public void onTouchDown(int scrollY);

	public boolean onScrollUp(float touchDeltaY, int scrollY);

	public boolean onScrollStop(int scrollY);

	public boolean onScrollDown(float touchDeltaY, int scrollY);

	public boolean onTouchUp(float touchDeltaY, int scrollY);

}

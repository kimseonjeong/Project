package com.mavlux.framework.widget.scrollobservableview;


public enum ScrollState {

	UP, STOP, DOWN;

	public static ScrollState getScrollState( float deltaY ) {

		if ( 0f > deltaY )
			return ScrollState.DOWN;
		else if ( 0f < deltaY )
			return ScrollState.UP;
		else
			return ScrollState.STOP;
	}

}

#pragma once
#include "afxwin.h"
class CMouse :
	public CWnd
{
public:
	CMouse(void);
	~CMouse(void);

	void CheckMouse(char *buf);
	void Pixel(char *buf);
	void Pad(char *buf);
	void PadDown();

	void MouseP(int x, int y);
	void CursorShow();
	void LeftClick();
	void WheelUp();
	void WheelDown();
	void RightClick();
	void Point();
};
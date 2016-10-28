#pragma once
#include "afxwin.h"
class CWinFunction :
	public CWnd
{
public:
	CWinFunction(void);
	~CWinFunction(void);

	void Check(char *buf);
	void EnterDel(char *buf);
	void keydown(BYTE key);
	 
	 void Upper(int code);
	 void Number(int code);
	 void Lower(int code);

	 //void CWinFunction::MouseP(int x, int y);

	 DECLARE_MESSAGE_MAP()
//	 afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	 afx_msg void OnMouseHWheel(UINT nFlags, short zDelta, CPoint pt);
};


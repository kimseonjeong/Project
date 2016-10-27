
// MFCObjectDetectionDlg.h : ��� ����
//

#pragma once
#include "afxwin.h"
#include "opencv/cv.h"
#include "opencv/cxcore.h"
#include "opencv/highgui.h"
#include "opencv2/opencv.hpp"

using namespace cv;

// CMFCObjectDetectionDlg ��ȭ ����
class CMFCObjectDetectionDlg : public CDialogEx
{
// �����Դϴ�.
public:
	CMFCObjectDetectionDlg(CWnd* pParent = NULL);	// ǥ�� �������Դϴ�.

// ��ȭ ���� �������Դϴ�.
	enum { IDD = IDD_MFCOBJECTDETECTION_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV �����Դϴ�.


// �����Դϴ�.
protected:
	HICON m_hIcon;

	// ������ �޽��� �� �Լ�
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
public:
	CStatic m_stcCam;
	VideoCapture m_videoCapture;

	Mat m_CaptureImage;
	Mat m_DisplayImage;
	Mat m_OriCopyImage;
	Mat m_PatternImage;
	Mat m_PatternResultImage;
	Mat m_PatternDisplayImage;

	int m_nWidth;
	int m_nHeight;

	int m_nWidthDisplay;
	int m_nHeightDisplay;
	int m_nWidthPatternDisplay;
	int m_nHeightPatternDisplay;

	BOOL m_bDrawRectStart;
	CRect m_Rect;
	CRect m_rectPatternResult;

	void UpdateMouosePos(int iX, int iY);
	void DrawRect(int iX, int iY, BOOL leftButtonDown);
	void PatternSave();
	void PatternMatching();

	static void OnMousePatternDefine(int iEvent, int iX, int iY, int iFlags, void* Userdata);
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnDestroy();
};

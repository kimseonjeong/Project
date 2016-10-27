
// MFCCamDlg.h : ��� ����
//

#pragma once
#include "afxwin.h"
#include "opencv/cv.h"
#include "opencv/cxcore.h"
#include "opencv/highgui.h"
#include "opencv2/opencv.hpp"

using namespace cv;

// CMFCCamDlg ��ȭ ����
class CMFCCamDlg : public CDialogEx
{
// �����Դϴ�.
public:
	CMFCCamDlg(CWnd* pParent = NULL);	// ǥ�� �������Դϴ�.

// ��ȭ ���� �������Դϴ�.
	enum { IDD = IDD_MFCCAM_DIALOG };

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

	Mat m_DisplayImage;
	Mat m_CaptureImage;

	int m_nWidth;
	int m_nHeight;

	int m_nWidthDisplay;
	int m_nHeightDisplay;
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnDestroy();
};

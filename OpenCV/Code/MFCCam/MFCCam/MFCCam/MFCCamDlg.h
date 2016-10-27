
// MFCCamDlg.h : 헤더 파일
//

#pragma once
#include "afxwin.h"
#include "opencv/cv.h"
#include "opencv/cxcore.h"
#include "opencv/highgui.h"
#include "opencv2/opencv.hpp"

using namespace cv;

// CMFCCamDlg 대화 상자
class CMFCCamDlg : public CDialogEx
{
// 생성입니다.
public:
	CMFCCamDlg(CWnd* pParent = NULL);	// 표준 생성자입니다.

// 대화 상자 데이터입니다.
	enum { IDD = IDD_MFCCAM_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 지원입니다.


// 구현입니다.
protected:
	HICON m_hIcon;

	// 생성된 메시지 맵 함수
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

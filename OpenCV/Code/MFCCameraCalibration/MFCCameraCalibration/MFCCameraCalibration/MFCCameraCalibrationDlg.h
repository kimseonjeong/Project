
// MFCCameraCalibrationDlg.h : 헤더 파일
//

#pragma once
#include "CamOpenCV.h"
#include "CamCalib.h"

// CMFCCameraCalibrationDlg 대화 상자
class CMFCCameraCalibrationDlg : public CDialogEx
{
// 생성입니다.
public:
	CMFCCameraCalibrationDlg(CWnd* pParent = NULL);	// 표준 생성자입니다.

	CamCalib* _calib;
	CamOpenCV* _camera;

	bool _rec_chessboard;

// 대화 상자 데이터입니다.
	enum { IDD = IDD_MFCCAMERACALIBRATION_DIALOG };

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
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnDestroy();
	afx_msg void OnBnClickedButtonChessboard();
};

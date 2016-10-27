
// MFCCameraCalibrationDlg.h : ��� ����
//

#pragma once
#include "CamOpenCV.h"
#include "CamCalib.h"

// CMFCCameraCalibrationDlg ��ȭ ����
class CMFCCameraCalibrationDlg : public CDialogEx
{
// �����Դϴ�.
public:
	CMFCCameraCalibrationDlg(CWnd* pParent = NULL);	// ǥ�� �������Դϴ�.

	CamCalib* _calib;
	CamOpenCV* _camera;

	bool _rec_chessboard;

// ��ȭ ���� �������Դϴ�.
	enum { IDD = IDD_MFCCAMERACALIBRATION_DIALOG };

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
	afx_msg void OnTimer(UINT_PTR nIDEvent);
	afx_msg void OnDestroy();
	afx_msg void OnBnClickedButtonChessboard();
};

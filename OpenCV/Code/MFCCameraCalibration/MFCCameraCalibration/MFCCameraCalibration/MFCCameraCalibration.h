
// MFCCameraCalibration.h : PROJECT_NAME ���� ���α׷��� ���� �� ��� �����Դϴ�.
//

#pragma once

#ifndef __AFXWIN_H__
	#error "PCH�� ���� �� ������ �����ϱ� ���� 'stdafx.h'�� �����մϴ�."
#endif

#include "resource.h"		// �� ��ȣ�Դϴ�.


// CMFCCameraCalibrationApp:
// �� Ŭ������ ������ ���ؼ��� MFCCameraCalibration.cpp�� �����Ͻʽÿ�.
//

class CMFCCameraCalibrationApp : public CWinApp
{
public:
	CMFCCameraCalibrationApp();

// �������Դϴ�.
public:
	virtual BOOL InitInstance();

// �����Դϴ�.

	DECLARE_MESSAGE_MAP()
};

extern CMFCCameraCalibrationApp theApp;
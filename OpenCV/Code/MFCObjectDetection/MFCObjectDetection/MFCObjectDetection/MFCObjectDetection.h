
// MFCObjectDetection.h : PROJECT_NAME ���� ���α׷��� ���� �� ��� �����Դϴ�.
//

#pragma once

#ifndef __AFXWIN_H__
	#error "PCH�� ���� �� ������ �����ϱ� ���� 'stdafx.h'�� �����մϴ�."
#endif

#include "resource.h"		// �� ��ȣ�Դϴ�.


// CMFCObjectDetectionApp:
// �� Ŭ������ ������ ���ؼ��� MFCObjectDetection.cpp�� �����Ͻʽÿ�.
//

class CMFCObjectDetectionApp : public CWinApp
{
public:
	CMFCObjectDetectionApp();

// �������Դϴ�.
public:
	virtual BOOL InitInstance();

// �����Դϴ�.

	DECLARE_MESSAGE_MAP()
};

extern CMFCObjectDetectionApp theApp;
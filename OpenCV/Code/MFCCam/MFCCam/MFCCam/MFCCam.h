
// MFCCam.h : PROJECT_NAME ���� ���α׷��� ���� �� ��� �����Դϴ�.
//

#pragma once

#ifndef __AFXWIN_H__
	#error "PCH�� ���� �� ������ �����ϱ� ���� 'stdafx.h'�� �����մϴ�."
#endif

#include "resource.h"		// �� ��ȣ�Դϴ�.


// CMFCCamApp:
// �� Ŭ������ ������ ���ؼ��� MFCCam.cpp�� �����Ͻʽÿ�.
//

class CMFCCamApp : public CWinApp
{
public:
	CMFCCamApp();

// �������Դϴ�.
public:
	virtual BOOL InitInstance();

// �����Դϴ�.

	DECLARE_MESSAGE_MAP()
};

extern CMFCCamApp theApp;
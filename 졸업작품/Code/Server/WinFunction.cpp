#include "stdafx.h"
#include "Server.h"
#include "ClientSock.h"
#include "ServerDlg.h"
#include "WinFunction.h"


	CWinFunction::CWinFunction()
	{
	}
	CWinFunction::~CWinFunction()
	{
	}

	void keybd_event(      
       BYTE bVk,              // ���� Ű�ڵ�
       BYTE bScan,          // �ϵ���� ��ĵ �ڵ�
       DWORD dwFlags     // ���� ���� Flag
	);

	void CWinFunction::keydown(BYTE key)
	{
     keybd_event( key, 0, KEYEVENTF_EXTENDEDKEY , 0 ); 
     Sleep(1);   //�ð� 1ms ����
	 keybd_event( key, 0, KEYEVENTF_KEYUP , 0 );
     Sleep(1);
	}

	void CWinFunction::Check(char *buf)
	{
		int keycode;

		keycode = buf[1];
		//printf(" keycode %d\n ", keycode);

		//printf("key %d\n",keycode);
		//printf("buf %d\n",buf[1]);

		if(65 <= keycode && keycode <=90)
			Upper(keycode);

		else if(48 <= keycode && keycode <= 57)
			Number(keycode);

		else if(97 <= keycode && keycode <= 122)
			Lower(keycode);

		else if(keycode== 32)
			keybd_event(32, 0, 0, 0);


		switch(keycode){

		   case 63: //?
				Upper(191);
				break;
		   case 46: //.
				Number(190);
				break;
		   case 42: //*
				Number(106);
				break;
		   case 45: //-
				Number(109);
				break;
		   case 43: //+
				Number(107);
				break;
		   case 61: //=
				Number(187);
				break;
		   case 59: //;
				Number(186);
				break;
		   case 44: //,
				Number(188);
				break;
		   case 33: //!
				Upper(49);
				break;
		   case 64: //@
				Upper(50);
				break;

		   /*case 35: //# 
			m_func.Upper(50);
			break;*/
		   case 36: //$
				Upper(51);
				break;
		   case 94:  //^
				Upper(54);
				break;
		   case 38: //&
				Upper(55);
				break;
		   case 40: //(
				Upper(57);
				break;
		   case 41: //)
				Upper(48);
				break;		
		   case 126:
				Upper(126);
				break;
		   case 47:
			   Number(47);
			   break;
		   case 58:
			   Number(58);
			   break;
    
    
		  // default:
			//printf("no\n");
  
		  }

	}

	void  CWinFunction::EnterDel(char *buf)
	{
		if(buf[1]=='0')
				keybd_event(8,0,0,0);		
	
		else if(buf[1]=='1')
				keybd_event(13,0,0,0);
	}


	void CWinFunction::Upper(int code)
	{
		
		keybd_event(VK_SHIFT, 0, 0, 0);
		keybd_event(code,0,0,0);
		Sleep(1);
		keybd_event(code,0,KEYEVENTF_KEYUP,0);
		keybd_event(VK_SHIFT, 0, KEYEVENTF_KEYUP, 0);
	}

	void CWinFunction::Number(int code)
	{
		

		keybd_event(code,0,0,0);
		Sleep(1);
		keybd_event(code,0,KEYEVENTF_KEYUP,0);

	}

	void CWinFunction::Lower(int code)
	{
		

		keybd_event(code-32,0,0,0);
		Sleep(2);
		keybd_event(code-32,0,KEYEVENTF_KEYUP,0);
	}


	BEGIN_MESSAGE_MAP(CWinFunction, CWnd)
//	ON_WM_LBUTTONDOWN()
	ON_WM_MOUSEHWHEEL()
	END_MESSAGE_MAP()




	void CWinFunction::OnMouseHWheel(UINT nFlags, short zDelta, CPoint pt)
	{
		// �� ����� ����Ϸ��� Windows Vista �̻��� �־�� �մϴ�.
		// _WIN32_WINNT ��ȣ�� 0x0600���� ũ�ų� ���ƾ� �մϴ�.
		// TODO: ���⿡ �޽��� ó���� �ڵ带 �߰� ��/�Ǵ� �⺻���� ȣ���մϴ�.
		CString strTmp = _T("");
		strTmp.Format(TEXT("OnmOUSEwHEEL()zDelta : %d\n"),zDelta);

		TRACE(strTmp);

		CWnd::OnMouseHWheel(nFlags, zDelta, pt);
	}

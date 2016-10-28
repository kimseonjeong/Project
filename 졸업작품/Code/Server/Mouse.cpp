
#include "stdafx.h"
#include "Server.h"
#include "ClientSock.h"
#include "ServerDlg.h"
#include "Mouse.h"
#include <stdio.h>
#include <Windows.h>


int AppWid,AppLen;
int wid, len ;
int a=0, b=0,MoveX=0, MoveY=0;
int ax,ay,bx,by,cx,cy;

	CMouse::CMouse()
	{
		
	}
	CMouse::~CMouse()
	{
	}

	void CMouse::Pixel(char *buf)
	{
		int i=1, num;
		int j=0;
		char temp[10]= {0};
		char temp2[10]= {0};
		
		//-----------------화면해상도------------------------------
		wid = GetSystemMetrics(SM_CXSCREEN); //가로크기 구하기.
		len = GetSystemMetrics(SM_CYSCREEN); //세로크기 구하기.
		//-----------------화면해상도------------------------------

		//printf("wid: %d\n",wid);
		//printf("len: %d\n",len);

		while(buf[i] != '_')
		{
			temp[i-1] = buf[i];
			i++;
		}
		num=atoi((const char *)temp);
		AppWid = num;
		//printf("Appwid : %d\n",AppWid);

		i++;
		j=0;
		while(buf[i] != '_'){
			temp2[j] =buf[i];
			i++;
			j++;
		}
		num = atoi((const char *)temp2);
		AppLen=num;
		//printf("AppLen %d",AppLen);

	}


	void CMouse::CheckMouse(char *buf)
	{
		int i, num;	
		int x,y,j;
		char temp[10]= {0};
		char temp2[10]= {0};


		if(buf[1] == 'P')
			Point();

		else if(buf[1] == 'L')
			LeftClick();

		else if(buf[1] == 'R')
			RightClick();

		else if(buf[1] == 'U')
			WheelUp();

		else if(buf[1] == 'D')
			WheelDown();

		else if (buf[1] == '/')
		{
			i=2;

			while(buf[i] != '/'){
				temp[i-2] = buf[i];
				i++;
			}

			num=atoi((const char *)temp);
			x =num;
			//printf("자이로x %d \n",x);

			i++;
			j=0;
			while(buf[i] != '/'){
				temp2[j] = buf[i];
				i++;
				j++;
			}
			num=atoi((const char *)temp2);
			y=num;
			//printf("자이로y %d \n",y);

		
			MouseP(x*(wid/AppWid)*1.27,y*(AppLen/len));
			//MouseP(x*3, y*1.5);
		}

	}

	void CMouse::PadDown() //누를때 
	{
		// --------현재 마우스 위치 좌표----------
			POINT p;
			GetCursorPos(&p);
		// --------------------------------------
		ax= p.x;
		ay = p.y;
		
		a=1;
	}

	void CMouse::Pad(char *buf)
	{
		int i, num;	
		int x=0,y=0,j=0;
		char temp[10]= {0};
		char temp2[10]= {0};

		if(buf[1] == '/')
		{
			i=2;

			while(buf[i] != '/'){
				temp[i-2] = buf[i];
				i++;
			}

			num=atoi((const char *)temp);
			x =num;
			//printf("패드x %d  ",x);

			i++;
			j=0;
			while(buf[i] != '/'){
				temp2[j] = buf[i];
				i++;
				j++;
			}
			num=atoi((const char *)temp2);
			y=num;
			//printf("패드y %d \n",y);
		}

			MoveX = ax + x;
			MoveY = ay + y;
			//printf("movex %d movey %d\n",MoveX,MoveY);
			
			if(a != 1){
				MouseP( MoveX, MoveY);
			}

			ax = MoveX;
			ay = MoveY;
			a = 0;

	}



	void CMouse::MouseP(int x, int y)
	{
		SetCursorPos(x,y);
		
	}

	void CMouse::LeftClick()
	{
		POINT mousepos;
		//HWND hWnd;
		//CRect rect; 
		//GetWindowRect(&rect); //현재 윈도우의  위치
		GetCursorPos(&mousepos);

		mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, GetMessageExtraInfo());    // 마우스의 왼쪽버튼을 누른다 (누른상태)
		Sleep(5);
		mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, GetMessageExtraInfo()); // 마우스의 왼쪽버튼을 땐다
		Sleep(5);
		//SendMessage(WM_LBUTTONDOWN,(WPARAM)mousepos.x,(LPARAM)mousepos.y);

	}

	
	void CMouse::WheelUp()
	{
		int ScrollValue;
		ScrollValue = 15;
		mouse_event(MOUSEEVENTF_WHEEL, 0, 0,ScrollValue, GetMessageExtraInfo());

	}

	void CMouse::WheelDown()
	{
		int ScrollValue;
		ScrollValue = -15;
		mouse_event(MOUSEEVENTF_WHEEL, 0, 0,ScrollValue, GetMessageExtraInfo());

	}

	void CMouse:: RightClick()
	{
		 int  nX = GetSystemMetrics( SM_CXSCREEN );              //실제 화면크기(해상도) 구하기 (가로)
		 int  nY = GetSystemMetrics( SM_CYSCREEN );              //실제 화면크기(해상도) 구하기 (세로)
		 int  x  =  150 * (65535 / nX);           //X축 계산   150(목표점) * (65535 / 가로해상도)
		 int  y  =  150 * (65535 / nY);           //Y축 계산   150(목표점) * (65535 / 세로해상도)


		POINT mousepos;
		GetCursorPos(&mousepos);

		mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, GetMessageExtraInfo());   
		Sleep(5);
		mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, GetMessageExtraInfo());
		Sleep(5);

		//mouse_event(MOUSEEVENTF_ABSOLUTE | MOUSEEVENTF_RIGHTDOWN, x, y, 0, 0);
		
	}

	void CMouse::Point()
	{
		keybd_event(VK_CONTROL, 0, 0, 0);   //ctrl(Down)
		keybd_event(VK_CONTROL, 0, KEYEVENTF_KEYUP, 0);   //ctrl(Up)

	}
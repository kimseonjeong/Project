// ServerDlg.cpp : implementation file
//
#include "stdafx.h"
#include "Server.h"
#include "ServerDlg.h"
#include "WinFunction.h"
#include "Mouse.h"

//#pragma comment(linker, "/entry:WinMainCRTStartup /subsystem:console")

#ifdef _DEBUG
#define new DEBUG_NEW//디버그 모드로 실행X, 
#undef THIS_FILE//재정의
static char THIS_FILE[] = __FILE__;
#endif

CWinFunction m_func;
CMouse m;

int count=0;

/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About
//CAboutDlg 대화상자
class CAboutDlg : public CDialog
{
public:
	CAboutDlg();
	

	// Dialog Data
	//{{AFX_DATA(CAboutDlg)
	enum { IDD = IDD_ABOUTBOX };//대화 상자 데이터
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CAboutDlg)
protected:
	virtual void DoDataExchange(CDataExchange* pDX);   
	// DDX/DDV 지원
	//}}AFX_VIRTUAL

	// Implementation
protected:
	//{{AFX_MSG(CAboutDlg)
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};

//CAboutDlg::IDD는 다이로그 아이디
CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
	//{{AFX_DATA_INIT(CAboutDlg)
	//}}AFX_DATA_INIT
}


void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CAboutDlg)
	//}}AFX_DATA_MAP
}

//Dialog 메세지 맵은 DialogProc같은거..
BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	//{{AFX_MSG_MAP(CAboutDlg)
	// No message handlers
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CServerDlg dialog
//생성자에서 초기화된 모습
CServerDlg::CServerDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CServerDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CServerDlg)
	//m_strSend = _T("");
	//}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	//m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

CServerDlg::~CServerDlg()//소멸자
{
	delete m_pServerSock;
	//연결 리스트 제일 앞 노드의 위치를 얻기
	POSITION pos = m_clientSocks.GetHeadPosition();
	while (pos != NULL)
		delete m_clientSocks.GetNext(pos);
	//GetNext(): 현재 위치의 노드 객체를 얻고 다음 위치로 이동
}

//DoDataExchange()에 추가된 모습
void CServerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CServerDlg)
	//DDX_Control(pDX, IDC_LIST_RECEIVE, m_listReceive);
	//DDX_Text(pDX, IDC_EDIT_SEND, m_strSend);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CServerDlg, CDialog)
	//{{AFX_MSG_MAP(CServerDlg)
	//ON_BN_CLICKED(IDC_SEND, OnSend)
	//}}AFX_MSG_MAP

	//ON_WM_PAINT()
	ON_EN_CHANGE(IDC_EDIT_SEND, &CServerDlg::OnEnChangeEditSend)
	ON_BN_CLICKED(IDC_SEND, &CServerDlg::OnBnClickedSend)
	//ON_LBN_SELCHANGE(IDC_LIST_RECEIVE, &CServerDlg::OnLbnSelchangeListReceive)
	//ON_NOTIFY(IPN_FIELDCHANGED, IDC_IPADDRESS1, &CServerDlg::OnIpnFieldchangedIpaddress1)
	//ON_BN_CLICKED(IDC_CANCLE, &CServerDlg::OnBnClickedCancle)
	ON_BN_CLICKED(IDC_BUTTON1, &CServerDlg::OnBnClickedButton1)
	ON_BN_CLICKED(IDC_BUTTON2, &CServerDlg::OnBnClickedButton2)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CServerDlg message handlers

BOOL CServerDlg::OnInitDialog()
{
	CDialog::OnInitDialog();//컨트롤 초기화

	// Add "About..." menu item to system menu.

	// IDM_ABOUTBOX must be in the system command range.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	HICON hlcon = NULL;
	hlcon = AfxGetApp() -> LoadIcon(IDR_MAINFRAME);
	SetIcon(hlcon, TRUE);   // 큰 아이콘을 설정합니다.
	//SetIcon(hlcon, FALSE);  // 작은 아이콘을 설정합니다. 

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	//SetIcon(m_hIcon, TRUE);			// Set big icon
	//SetIcon(m_hIcon, FALSE);		// Set small icon


	// TODO: Add extra initialization here
	//소캣 생셩 및 listen함수(접속 요청을 대기)
	m_pServerSock = new CServerSock();
	m_pServerSock->Create(7007);
	

	return TRUE;  // return TRUE  unless you set the focus to a control
}

//void CServerDlg::OnSysCommand(UINT nID, LPARAM lParam)
//{
//	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
//	{
//		CAboutDlg dlgAbout;
//		dlgAbout.DoModal();
//	}
//	else
//	{
//		CDialog::OnSysCommand(nID, lParam);
//	}
//}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

//void CServerDlg::OnPaint() 
//{
//	if (IsIconic())
//	{
//		CPaintDC dc(this); // device context for painting
//
//		//SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);
//
//		// Center icon in client rectangle
//		int cxIcon = GetSystemMetrics(SM_CXICON);
//		int cyIcon = GetSystemMetrics(SM_CYICON);
//		CRect rect;
//		GetClientRect(&rect);
//		int x = (rect.Width() - cxIcon + 1) / 2;
//		int y = (rect.Height() - cyIcon + 1) / 2;
//
//		// Draw the icon
//		dc.DrawIcon(x, y, m_hIcon);
//	}
//	else
//	{
//		CDialog::OnPaint();
//	}
//}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
//HCURSOR CServerDlg::OnQueryDragIcon()
//{
//	return (HCURSOR) m_hIcon;
//}

//접속 요청을 받아들이고 새로운 소켓을 생성하여 접속 요청에 연결
void CServerDlg::OnAccept()
{
	CClientSock *p = new CClientSock;
	m_pServerSock->Accept(*p);
	//p->Send("연결되었습니다.", 16);
	m_clientSocks.AddTail(p);//뒷쪽에 추가
}

void CServerDlg::OnReceive(CClientSock* pSock)//데이터 수신될때 호출
{
	char buf[100];
	pSock->Receive(buf,100);
	//m_listReceive.InsertString(0, buf);//리스트 박스에 buf출력
	Send(buf);//데이터를 전송

}

/*void CServerDlg::OnSend() //데이터 송신될때 호출
{
// TODO: Add your control notification handler code here
UpdateData(TRUE);
char buf[200];
lstrcpy(buf, "Server:");
lstrcat(buf, (const char*)m_strSend);
Send(buf);
}*/

void CServerDlg::Send(char *buf)
{
	int keycode;
	int num = 0;

	POSITION pos = m_clientSocks.GetHeadPosition();//문자열의 시작위치를 얻기
	//문자열의 끝이 나올때 까지 반복
	//GetNext함수는 현재 위치의 문자열을 반환하고 현재 위치를 다음 문자열로 옮김

	CClientSock *p = m_clientSocks.GetNext(pos);
	p -> Receive(buf,10);
	//m_listReceive.InsertString(0, buf);

	keycode = buf[0];
	//printf(" keycode %d\n ", keycode);

	//printf("key %d\n",keycode);
	//printf("buf %d\n",buf[0]);

	switch(buf[0]){

		case '_':
			m.Pixel(buf);
			break;

		case 'M':
			m.CheckMouse(buf);
			break;

		case 'k':
			m_func.EnterDel(buf);
			break;
		
		case 'K':
			m_func.Check(buf);
			break;

		case 'P':
			m.Pad(buf);
			break;

		case 'D':
			m.PadDown();
			break;

		//default:
			//printf("no\n");

	}

	
}

/*
void CServerDlg::OnPaint()
{
CPaintDC dc(this); // device context for painting
PostMessage (WM_SHOWWINDOW,FALSE, SW_OTHERUNZOOM);
// TODO: 여기에 메시지 처리기 코드를 추가합니다.
// 그리기 메시지에 대해서는 CDialog::OnPaint()을(를) 호출하지 마십시오.
} */


void CServerDlg::OnEnChangeEditSend()
{
	// TODO:  RICHEDIT 컨트롤인 경우, 이 컨트롤은
	// CDialog::OnInitDialog() 함수를 재지정 
	//하고 마스크에 OR 연산하여 설정된 ENM_CHANGE 플래그를 지정하여 CRichEditCtrl().SetEventMask()를 호출하지 않으면
	// 이 알림 메시지를 보내지 않습니다.

	// TODO:  여기에 컨트롤 알림 처리기 코드를 추가합니다.
}


void CServerDlg::OnBnClickedSend()
{
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	PostQuitMessage(0);
}


void CServerDlg::OnLbnSelchangeListReceive()
{
	
	u_long m_ipAddress;
	//printf("");
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	UpdateData(TRUE);
	CString strTemp = _T("");
	DWORD dwAddress = ntohl(m_ipAddress);
	strTemp = inet_ntoa( *(IN_ADDR*) &dwAddress );

	AfxMessageBox(strTemp);
	//printf("%s",strTemp);
}


void CServerDlg::OnIpnFieldchangedIpaddress1(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMIPADDRESS pIPAddr = reinterpret_cast<LPNMIPADDRESS>(pNMHDR);
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	*pResult = 0;
}



void CServerDlg::OnBnClickedButton1()
{
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	/*u_long m_ipAddress=1;
	printf("");
	
	UpdateData(TRUE);
	CString strTemp = _T("");

	DWORD dwAddress = ntohl(m_ipAddress);
	strTemp = inet_ntoa( *(IN_ADDR*) &dwAddress );

	AfxMessageBox(strTemp);
	printf("%s",strTemp);*/

	WORD wVersionRequested;
	WSADATA wsaData;
	char name[255];
	CString ip;
	PHOSTENT hostinfo;
	wVersionRequested = MAKEWORD(2,0);
	CWnd *p = GetDlgItem(IDC_EDIT1);

	if(WSAStartup(wVersionRequested, &wsaData)==0)
	{
		if(gethostname(name,sizeof(name))==0)
		{
			if((hostinfo = gethostbyname(name)) != NULL)
			{
				ip = inet_ntoa(*(struct in_addr *)*hostinfo -> h_addr_list);
				printf("%s",ip);
				
				p->SetWindowText(ip);
			}
		}
		WSACleanup();
	}


}


void CServerDlg::OnBnClickedButton2()
{

	m_pServerSock->Listen();
	SendMessage( WM_SYSCOMMAND, SC_MINIMIZE );
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
}

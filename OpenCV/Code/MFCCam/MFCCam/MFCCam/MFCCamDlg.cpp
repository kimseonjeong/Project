
// MFCCamDlg.cpp : 구현 파일
//

#include "stdafx.h"
#include "MFCCam.h"
#include "MFCCamDlg.h"
#include "afxdialogex.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// 응용 프로그램 정보에 사용되는 CAboutDlg 대화 상자입니다.

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// 대화 상자 데이터입니다.
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 지원입니다.

// 구현입니다.
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// CMFCCamDlg 대화 상자



CMFCCamDlg::CMFCCamDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CMFCCamDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);

	m_nWidth = 0;
	m_nHeight = 0;

	m_nWidthDisplay = 0;
	m_nHeightDisplay = 0;
}

void CMFCCamDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_PIC_CAM, m_stcCam);
}

BEGIN_MESSAGE_MAP(CMFCCamDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_TIMER()
	ON_WM_DESTROY()
END_MESSAGE_MAP()


// CMFCCamDlg 메시지 처리기

BOOL CMFCCamDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// 시스템 메뉴에 "정보..." 메뉴 항목을 추가합니다.

	// IDM_ABOUTBOX는 시스템 명령 범위에 있어야 합니다.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 이 대화 상자의 아이콘을 설정합니다.  응용 프로그램의 주 창이 대화 상자가 아닐 경우에는
	//  프레임워크가 이 작업을 자동으로 수행합니다.
	SetIcon(m_hIcon, TRUE);			// 큰 아이콘을 설정합니다.
	SetIcon(m_hIcon, FALSE);		// 작은 아이콘을 설정합니다.

	// TODO: 여기에 추가 초기화 작업을 추가합니다.
	m_videoCapture.open(0);

	CRect rect;
	GetDlgItem(IDC_PIC_CAM)->GetClientRect(&rect);
	m_nWidthDisplay = rect.right - rect.left;
	m_nHeightDisplay = rect.bottom - rect.top;
	m_CaptureImage.create(m_nHeight, m_nWidth, CV_8UC1);
	m_DisplayImage.create(m_nHeightDisplay, m_nWidthDisplay, CV_8UC1);

	if (m_videoCapture.isOpened() == true)
		SetTimer(1, 30, NULL);

	return TRUE;  // 포커스를 컨트롤에 설정하지 않으면 TRUE를 반환합니다.
}

void CMFCCamDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// 대화 상자에 최소화 단추를 추가할 경우 아이콘을 그리려면
//  아래 코드가 필요합니다.  문서/뷰 모델을 사용하는 MFC 응용 프로그램의 경우에는
//  프레임워크에서 이 작업을 자동으로 수행합니다.

void CMFCCamDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 그리기를 위한 디바이스 컨텍스트입니다.

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 클라이언트 사각형에서 아이콘을 가운데에 맞춥니다.
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 아이콘을 그립니다.
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

// 사용자가 최소화된 창을 끄는 동안에 커서가 표시되도록 시스템에서
//  이 함수를 호출합니다.
HCURSOR CMFCCamDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMFCCamDlg::OnTimer(UINT_PTR nIDEvent)
{
	// TODO: Add your message handler code here and/or call default
	if (m_videoCapture.isOpened() == true)
	{
		m_videoCapture >> m_CaptureImage;

		// resize		
		Size dSize;
		dSize.width = m_DisplayImage.cols;
		dSize.height = m_DisplayImage.rows;
		resize(m_CaptureImage, m_DisplayImage, dSize, 0, 0, INTER_LINEAR);

		// Mat to Bitmap        
		HDC hdc = m_stcCam.GetDC()->m_hDC;
		char m_chBmpBuf[2048];
		BITMAPINFO *m_pBmpInfo = 0;
		m_pBmpInfo = (BITMAPINFO *)m_chBmpBuf;
		m_pBmpInfo->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
		m_pBmpInfo->bmiHeader.biWidth = m_DisplayImage.cols;
		m_pBmpInfo->bmiHeader.biHeight = -(m_DisplayImage.rows);	
		m_pBmpInfo->bmiHeader.biBitCount = 24;
		m_pBmpInfo->bmiHeader.biPlanes = 1;
		m_pBmpInfo->bmiHeader.biCompression = BI_RGB;
		m_pBmpInfo->bmiHeader.biSizeImage = 0;
		m_pBmpInfo->bmiHeader.biXPelsPerMeter = 0;
		m_pBmpInfo->bmiHeader.biYPelsPerMeter = 0;
		m_pBmpInfo->bmiHeader.biClrUsed = 0;
		m_pBmpInfo->bmiHeader.biClrImportant = 0;
		StretchDIBits(hdc, 0, 0, m_DisplayImage.cols, m_DisplayImage.rows,
			0, 0, m_DisplayImage.cols, m_DisplayImage.rows,
			m_DisplayImage.data, m_pBmpInfo,
			DIB_RGB_COLORS, SRCCOPY);
		Invalidate(FALSE);
	}

	CDialogEx::OnTimer(nIDEvent);
}


void CMFCCamDlg::OnDestroy()
{
	CDialogEx::OnDestroy();

	// TODO: Add your message handler code here
	KillTimer(1);
	m_videoCapture.release();
}

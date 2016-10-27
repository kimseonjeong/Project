
// MFCCamDlg.cpp : ���� ����
//

#include "stdafx.h"
#include "MFCCam.h"
#include "MFCCamDlg.h"
#include "afxdialogex.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// ���� ���α׷� ������ ���Ǵ� CAboutDlg ��ȭ �����Դϴ�.

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// ��ȭ ���� �������Դϴ�.
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV �����Դϴ�.

// �����Դϴ�.
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


// CMFCCamDlg ��ȭ ����



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


// CMFCCamDlg �޽��� ó����

BOOL CMFCCamDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// �ý��� �޴��� "����..." �޴� �׸��� �߰��մϴ�.

	// IDM_ABOUTBOX�� �ý��� ��� ������ �־�� �մϴ�.
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

	// �� ��ȭ ������ �������� �����մϴ�.  ���� ���α׷��� �� â�� ��ȭ ���ڰ� �ƴ� ��쿡��
	//  �����ӿ�ũ�� �� �۾��� �ڵ����� �����մϴ�.
	SetIcon(m_hIcon, TRUE);			// ū �������� �����մϴ�.
	SetIcon(m_hIcon, FALSE);		// ���� �������� �����մϴ�.

	// TODO: ���⿡ �߰� �ʱ�ȭ �۾��� �߰��մϴ�.
	m_videoCapture.open(0);

	CRect rect;
	GetDlgItem(IDC_PIC_CAM)->GetClientRect(&rect);
	m_nWidthDisplay = rect.right - rect.left;
	m_nHeightDisplay = rect.bottom - rect.top;
	m_CaptureImage.create(m_nHeight, m_nWidth, CV_8UC1);
	m_DisplayImage.create(m_nHeightDisplay, m_nWidthDisplay, CV_8UC1);

	if (m_videoCapture.isOpened() == true)
		SetTimer(1, 30, NULL);

	return TRUE;  // ��Ŀ���� ��Ʈ�ѿ� �������� ������ TRUE�� ��ȯ�մϴ�.
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

// ��ȭ ���ڿ� �ּ�ȭ ���߸� �߰��� ��� �������� �׸�����
//  �Ʒ� �ڵ尡 �ʿ��մϴ�.  ����/�� ���� ����ϴ� MFC ���� ���α׷��� ��쿡��
//  �����ӿ�ũ���� �� �۾��� �ڵ����� �����մϴ�.

void CMFCCamDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // �׸��⸦ ���� ����̽� ���ؽ�Ʈ�Դϴ�.

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Ŭ���̾�Ʈ �簢������ �������� ����� ����ϴ�.
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// �������� �׸��ϴ�.
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
}

// ����ڰ� �ּ�ȭ�� â�� ���� ���ȿ� Ŀ���� ǥ�õǵ��� �ý��ۿ���
//  �� �Լ��� ȣ���մϴ�.
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

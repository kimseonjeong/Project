
// MFCObjectDetectionDlg.cpp : ���� ����
//

#include "stdafx.h"
#include "MFCObjectDetection.h"
#include "MFCObjectDetectionDlg.h"
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


// CMFCObjectDetectionDlg ��ȭ ����



CMFCObjectDetectionDlg::CMFCObjectDetectionDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CMFCObjectDetectionDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);

	m_nWidth = 0;
	m_nHeight = 0;

	m_nWidthDisplay = 0;
	m_nHeightDisplay = 0;
	m_nWidthPatternDisplay = 0;
	m_nHeightPatternDisplay = 0;

	m_bDrawRectStart = FALSE;

	m_rectPatternResult.SetRectEmpty();
}

void CMFCObjectDetectionDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_PIC_CAM, m_stcCam);
}

BEGIN_MESSAGE_MAP(CMFCObjectDetectionDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_TIMER()
	ON_WM_DESTROY()
END_MESSAGE_MAP()


// CMFCObjectDetectionDlg �޽��� ó����

BOOL CMFCObjectDetectionDlg::OnInitDialog()
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

	//init Display rect and create image
	CRect rect;
	GetDlgItem(IDC_PIC_CAM)->GetClientRect(&rect);
	m_nWidthDisplay = rect.right - rect.left;
	m_nHeightDisplay = rect.bottom - rect.top;
	m_CaptureImage.create(m_nHeight, m_nWidth, CV_8UC1);
	m_OriCopyImage.create(m_nHeight, m_nWidth, CV_8UC1);
	m_DisplayImage.create(m_nHeightDisplay, m_nWidthDisplay, CV_8UC1);
	m_PatternImage.create(m_nHeight, m_nWidth, CV_8UC1);
	GetDlgItem(IDC_PIC_PATTERN_TARGET)->GetClientRect(&rect);
	m_nWidthPatternDisplay = rect.right - rect.left;
	m_nHeightPatternDisplay = rect.bottom - rect.top;
	m_PatternDisplayImage.create(m_nHeightPatternDisplay, m_nWidthPatternDisplay, CV_8UC1);

	if (m_videoCapture.isOpened() == true)
		SetTimer(1, 30, NULL);

	return TRUE;  // ��Ŀ���� ��Ʈ�ѿ� �������� ������ TRUE�� ��ȯ�մϴ�.
}

void CMFCObjectDetectionDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CMFCObjectDetectionDlg::OnPaint()
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
HCURSOR CMFCObjectDetectionDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMFCObjectDetectionDlg::OnTimer(UINT_PTR nIDEvent)
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

		if (m_rectPatternResult.IsRectEmpty() == FALSE)
		{
			// do overlay pattern result
			double dWidthRatio = (double)m_nWidthDisplay / (double)m_CaptureImage.cols;
			double dHeightRatio = (double)m_nHeightDisplay / (double)m_CaptureImage.rows;
			Rect rect;
			rect.x = (int)(m_rectPatternResult.left * dWidthRatio);
			rect.y = (int)(m_rectPatternResult.top * dHeightRatio);
			rect.width = (int)((m_rectPatternResult.right - m_rectPatternResult.left) * dWidthRatio);
			rect.height = (int)((m_rectPatternResult.bottom - m_rectPatternResult.top) * dHeightRatio);
			rectangle(m_DisplayImage, rect, Scalar(0, 255, 0), 1, 8, 0);
		}

		// Mat to Bitmap        
		HDC hdc = m_stcCam.GetDC()->m_hDC;
		char m_chBmpBuf[2048];
		BITMAPINFO *m_pBmpInfo = 0;
		m_pBmpInfo = (BITMAPINFO *)m_chBmpBuf;
		m_pBmpInfo->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
		m_pBmpInfo->bmiHeader.biWidth = m_DisplayImage.cols;
		m_pBmpInfo->bmiHeader.biHeight = -(m_DisplayImage.rows);	// -�� ���� ������ x���� �����Ǿ� ���÷��� �ȴ�. �̹����� �������̹Ƿ� �������� ����.
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

		m_OriCopyImage = m_CaptureImage.clone();
		imshow("PatternDefine", m_OriCopyImage);
		setMouseCallback("PatternDefine", OnMousePatternDefine, this);
		PatternMatching();
	}

	CDialogEx::OnTimer(nIDEvent);
}

void CMFCObjectDetectionDlg::OnMousePatternDefine(int iEvent, int iX, int iY, int iFlags, void* Userdata)
{
	CMFCObjectDetectionDlg* pCMFCObjectDetectionDlg = (CMFCObjectDetectionDlg*)Userdata;
	switch (iEvent)
	{
	case EVENT_MOUSEMOVE:
	{
		pCMFCObjectDetectionDlg->UpdateMouosePos(iX, iY);
	}break;
	case EVENT_LBUTTONDOWN:
	{
		pCMFCObjectDetectionDlg->DrawRect(iX, iY, TRUE);
	}break;
	case EVENT_LBUTTONUP:
	{
		pCMFCObjectDetectionDlg->DrawRect(iX, iY, FALSE);
		pCMFCObjectDetectionDlg->PatternSave();
		pCMFCObjectDetectionDlg->PatternMatching();
	}break;
	}
}

void CMFCObjectDetectionDlg::UpdateMouosePos(int iX, int iY)
{
	CString strText = _T("");
	strText.Format(_T("(%4d, %4d)"), iX, iY);
	GetDlgItem(IDC_STC_PATTERN_MOUSE_POS)->SetWindowText(strText);
}

void CMFCObjectDetectionDlg::DrawRect(int iX, int iY, BOOL leftButtonDown)
{
	if (leftButtonDown == TRUE)
	{
		// left button down
		if (m_bDrawRectStart == FALSE)
		{
			// not start rect drawing
			m_Rect.left = iX;
			m_Rect.top = iY;
			m_bDrawRectStart = TRUE;
		}
	}
	else
	{
		// left button up
		if (m_bDrawRectStart == TRUE)
		{
			// now stop rect drawing
			m_Rect.right = iX;
			m_Rect.bottom = iY;
			m_bDrawRectStart = FALSE;
		}
	}
}

void CMFCObjectDetectionDlg::PatternSave()
{
	if (m_OriCopyImage.empty() == true)
		return;

	// check rect range
	Rect rectRoi;
	rectRoi.x = m_Rect.left;
	rectRoi.y = m_Rect.top;
	rectRoi.width = m_Rect.right - m_Rect.left;
	rectRoi.height = m_Rect.bottom - m_Rect.top;

	if (m_Rect.left < 0 || m_Rect.top < 0 || m_Rect.right >= m_OriCopyImage.cols || m_Rect.bottom >= m_OriCopyImage.rows)
		return;

	if (rectRoi.width == 0 || rectRoi.height == 0)
		return;

	// get pattern image
	Mat matTemp(m_OriCopyImage, Rect(rectRoi.x, rectRoi.y, rectRoi.width, rectRoi.height));
	m_PatternImage = matTemp;

	//resize pattern image
	Size dSize;
	dSize.width = m_PatternDisplayImage.cols;
	dSize.height = m_PatternDisplayImage.rows;
	resize(m_PatternImage, m_PatternDisplayImage, dSize, 0, 0, INTER_LINEAR);

	// display pattern image
	// Mat to Bitmap
	HDC hdc = GetDlgItem(IDC_PIC_PATTERN_TARGET)->GetDC()->m_hDC;
	char m_chBmpBuf[2048];
	BITMAPINFO *m_pBmpInfo = 0;
	m_pBmpInfo = (BITMAPINFO *)m_chBmpBuf;
	m_pBmpInfo->bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
	m_pBmpInfo->bmiHeader.biWidth = m_PatternDisplayImage.cols;
	m_pBmpInfo->bmiHeader.biHeight = -(m_PatternDisplayImage.rows);	// -�� ���� ������ x���� �����Ǿ� ���÷��� �ȴ�. �̹����� �������̹Ƿ� �������� ����.
	m_pBmpInfo->bmiHeader.biBitCount = 24;
	m_pBmpInfo->bmiHeader.biPlanes = 1;
	m_pBmpInfo->bmiHeader.biCompression = BI_RGB;
	m_pBmpInfo->bmiHeader.biSizeImage = 0;
	m_pBmpInfo->bmiHeader.biXPelsPerMeter = 0;
	m_pBmpInfo->bmiHeader.biYPelsPerMeter = 0;
	m_pBmpInfo->bmiHeader.biClrUsed = 0;
	m_pBmpInfo->bmiHeader.biClrImportant = 0;
	StretchDIBits(hdc, 0, 0, m_PatternDisplayImage.cols, m_PatternDisplayImage.rows,
		0, 0, m_PatternDisplayImage.cols, m_PatternDisplayImage.rows,
		m_PatternDisplayImage.data, m_pBmpInfo,
		DIB_RGB_COLORS, SRCCOPY);
	Invalidate(FALSE);
}

void CMFCObjectDetectionDlg::PatternMatching()
{
	CString strText = _T("No Result");
	m_rectPatternResult.SetRectEmpty();
	if (m_PatternImage.empty() == true) 
	{
		GetDlgItem(IDC_STC_PATTERN_SCORE)->SetWindowText(strText);
		GetDlgItem(IDC_STC_PATTERN_RECT)->SetWindowText(strText);
		return;
	}

	// do matching pattern
	int match_method = CV_TM_SQDIFF_NORMED;
	matchTemplate(m_CaptureImage, m_PatternImage, m_PatternResultImage, match_method);

	double minVal = 0.0;
	double maxVal = 0.0;
	Point minLoc;
	Point maxLoc;
	Point matchLoc;
	double scoreResult = 0.0;

	// find most similar position
	minMaxLoc(m_PatternResultImage, &minVal, &maxVal, &minLoc, &maxLoc, Mat());
	// For SQDIFF and SQDIFF_NORMED, hte best matches are lower values. 
	// For all the other methods, the higher the better
	if (match_method == CV_TM_SQDIFF || match_method == CV_TM_SQDIFF_NORMED)
	{
		matchLoc = minLoc;
		scoreResult = minVal;
	}
	else
	{
		matchLoc = maxLoc;
		scoreResult = maxVal;
	}

	m_rectPatternResult.left = matchLoc.x;
	m_rectPatternResult.top = matchLoc.y;
	m_rectPatternResult.right = matchLoc.x + m_PatternImage.cols;
	m_rectPatternResult.bottom = matchLoc.y + m_PatternImage.rows;

	strText.Format(_T("(%d, %d, %d, %d)"), m_rectPatternResult.left,
		m_rectPatternResult.top,
		m_rectPatternResult.right,
		m_rectPatternResult.bottom);
	GetDlgItem(IDC_STC_PATTERN_RECT)->SetWindowText(strText);

	strText.Format(_T("min :%.3f, max : %.3f"), minVal, maxVal);
	GetDlgItem(IDC_STC_PATTERN_SCORE)->SetWindowText(strText);
}


void CMFCObjectDetectionDlg::OnDestroy()
{
	CDialogEx::OnDestroy();

	// TODO: Add your message handler code here
	KillTimer(1);
	Sleep(100);
	m_videoCapture.release();
}

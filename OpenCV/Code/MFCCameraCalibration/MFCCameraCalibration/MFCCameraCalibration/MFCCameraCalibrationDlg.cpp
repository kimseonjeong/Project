
// MFCCameraCalibrationDlg.cpp : ���� ����
//

#include "stdafx.h"
#include "MFCCameraCalibration.h"
#include "MFCCameraCalibrationDlg.h"
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


// CMFCCameraCalibrationDlg ��ȭ ����



CMFCCameraCalibrationDlg::CMFCCameraCalibrationDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CMFCCameraCalibrationDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);

	_camera = NULL;
	_calib = NULL;

	_rec_chessboard = false;
}

void CMFCCameraCalibrationDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CMFCCameraCalibrationDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_TIMER()
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDC_BUTTON_CHESSBOARD, &CMFCCameraCalibrationDlg::OnBnClickedButtonChessboard)
END_MESSAGE_MAP()


// CMFCCameraCalibrationDlg �޽��� ó����

BOOL CMFCCameraCalibrationDlg::OnInitDialog()
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
	_camera = new CamOpenCV("OpenCV_Camera", 0, 640, 480);
	_calib = new CamCalib(9, 6, 5, 0.314 / 9, 0.209 / 6);
	//���� : ü������ ���ι��� �ڳ� ��, ���ι��� �ڳ� ��, �ν��� ü���� ��

	SetTimer(1000, 30, 0);

	return TRUE;  // ��Ŀ���� ��Ʈ�ѿ� �������� ������ TRUE�� ��ȯ�մϴ�.
}

void CMFCCameraCalibrationDlg::OnSysCommand(UINT nID, LPARAM lParam)
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

void CMFCCameraCalibrationDlg::OnPaint()
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
HCURSOR CMFCCameraCalibrationDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMFCCameraCalibrationDlg::OnTimer(UINT_PTR nIDEvent)
{
	// TODO: ���⿡ �޽��� ó���� �ڵ带 �߰� ��/�Ǵ� �⺻���� ȣ���մϴ�.
	if (_camera && _camera->CaptureImage()) {
		IplImage* cvImage = _camera->GetCvImage();	// Do not release this image

		if (_calib->_success_count < _calib->_no_rec_boards) {
			// ó������ _success_count�� 0�̾��ٰ� FindChessboard�� �ڳ� �˻��� �����ϸ� 1�� �ٲ��.
			IplImage* calibImage = cvCreateImage(cvGetSize(cvImage), IPL_DEPTH_8U, 3);

			cvCopy(cvImage, calibImage, 0);

			if (_calib->FindChessboard(cvImage, calibImage)) {
				if (_rec_chessboard) {
					_calib->_success_count++;
					_rec_chessboard = false;
				}
			}

			// ��ȭ���ڿ� �̹��� ǥ��
			BITMAPINFO *bi = _camera->GetBitmapInfo();	// ī�޶� ��ġ�� ���� ������ ��������
			bi->bmiHeader.biHeight *= -1;

			CDC *pDC = GetDC();
			StretchDIBits(pDC->m_hDC,
				0, 0, calibImage->width, calibImage->height,
				0, 0, calibImage->width, calibImage->height,
				(const BYTE *)calibImage->imageData, bi, DIB_RGB_COLORS, SRCCOPY);

			char text[256];
			sprintf_s(text, "Finded Chessboard = %d/%d", _calib->_success_count, _calib->_no_rec_boards);
			pDC->TextOut(10, 10, text, strlen(text));
			ReleaseDC(pDC);

			cvReleaseImage(&calibImage);

			if (_calib->_success_count == _calib->_no_rec_boards) {
				// �ڳ� �˻��� �Ϸ�Ǿ��� �� Ķ���극�̼� ��Ʈ������ ����Ѵ�.
				_calib->CalibrateCamera(cvGetSize(cvImage));
				_calib->_success_count++;
			}
		}
		else {
			// �̹����� �����Ѵ�.
			IplImage* img_undistort = cvCreateImage(cvGetSize(cvImage), IPL_DEPTH_8U, 3);

			cvCopy(cvImage, img_undistort, 0);

			_calib->Undistort(cvImage, img_undistort);

			// ��ȭ���ڿ� �̹��� ǥ��
			BITMAPINFO *bi = _camera->GetBitmapInfo();	// ī�޶� ��ġ�� ���� ������ ��������
			bi->bmiHeader.biHeight *= -1;

			CDC *pDC = GetDC();
			StretchDIBits(pDC->m_hDC,
				0, 0, img_undistort->width, img_undistort->height,
				0, 0, img_undistort->width, img_undistort->height,
				(const BYTE *)img_undistort->imageData, bi, DIB_RGB_COLORS, SRCCOPY);

			const char *text = "Undistorted Image";
			pDC->TextOut(10, 10, text, strlen(text));
			ReleaseDC(pDC);

			cvReleaseImage(&img_undistort);
		}
	}
	CDialogEx::OnTimer(nIDEvent);
}


void CMFCCameraCalibrationDlg::OnDestroy()
{
	CDialogEx::OnDestroy();

	// TODO: ���⿡ �޽��� ó���� �ڵ带 �߰��մϴ�.
	delete _camera;
	delete _calib;
}


void CMFCCameraCalibrationDlg::OnBnClickedButtonChessboard()
{
	// TODO: ���⿡ ��Ʈ�� �˸� ó���� �ڵ带 �߰��մϴ�.
	_rec_chessboard = true;
}

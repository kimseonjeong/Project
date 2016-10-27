
// MFCCameraCalibrationDlg.cpp : 구현 파일
//

#include "stdafx.h"
#include "MFCCameraCalibration.h"
#include "MFCCameraCalibrationDlg.h"
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


// CMFCCameraCalibrationDlg 대화 상자



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


// CMFCCameraCalibrationDlg 메시지 처리기

BOOL CMFCCameraCalibrationDlg::OnInitDialog()
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
	_camera = new CamOpenCV("OpenCV_Camera", 0, 640, 480);
	_calib = new CamCalib(9, 6, 5, 0.314 / 9, 0.209 / 6);
	//인자 : 체스판의 가로방향 코너 수, 세로방향 코너 수, 인식할 체스판 수

	SetTimer(1000, 30, 0);

	return TRUE;  // 포커스를 컨트롤에 설정하지 않으면 TRUE를 반환합니다.
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

// 대화 상자에 최소화 단추를 추가할 경우 아이콘을 그리려면
//  아래 코드가 필요합니다.  문서/뷰 모델을 사용하는 MFC 응용 프로그램의 경우에는
//  프레임워크에서 이 작업을 자동으로 수행합니다.

void CMFCCameraCalibrationDlg::OnPaint()
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
HCURSOR CMFCCameraCalibrationDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}



void CMFCCameraCalibrationDlg::OnTimer(UINT_PTR nIDEvent)
{
	// TODO: 여기에 메시지 처리기 코드를 추가 및/또는 기본값을 호출합니다.
	if (_camera && _camera->CaptureImage()) {
		IplImage* cvImage = _camera->GetCvImage();	// Do not release this image

		if (_calib->_success_count < _calib->_no_rec_boards) {
			// 처음에는 _success_count가 0이었다가 FindChessboard로 코너 검색을 성공하면 1로 바뀐다.
			IplImage* calibImage = cvCreateImage(cvGetSize(cvImage), IPL_DEPTH_8U, 3);

			cvCopy(cvImage, calibImage, 0);

			if (_calib->FindChessboard(cvImage, calibImage)) {
				if (_rec_chessboard) {
					_calib->_success_count++;
					_rec_chessboard = false;
				}
			}

			// 대화상자에 이미지 표시
			BITMAPINFO *bi = _camera->GetBitmapInfo();	// 카메라 장치의 영상 정보를 가져오고
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
				// 코너 검색이 완료되었을 때 캘리브레이션 매트릭스를 계산한다.
				_calib->CalibrateCamera(cvGetSize(cvImage));
				_calib->_success_count++;
			}
		}
		else {
			// 이미지를 보정한다.
			IplImage* img_undistort = cvCreateImage(cvGetSize(cvImage), IPL_DEPTH_8U, 3);

			cvCopy(cvImage, img_undistort, 0);

			_calib->Undistort(cvImage, img_undistort);

			// 대화상자에 이미지 표시
			BITMAPINFO *bi = _camera->GetBitmapInfo();	// 카메라 장치의 영상 정보를 가져오고
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

	// TODO: 여기에 메시지 처리기 코드를 추가합니다.
	delete _camera;
	delete _calib;
}


void CMFCCameraCalibrationDlg::OnBnClickedButtonChessboard()
{
	// TODO: 여기에 컨트롤 알림 처리기 코드를 추가합니다.
	_rec_chessboard = true;
}

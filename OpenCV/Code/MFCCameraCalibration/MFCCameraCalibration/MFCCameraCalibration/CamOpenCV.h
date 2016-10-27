#pragma once

#include <opencv/cv.h>
#include <opencv/highgui.h>

// OpenCV 라이브러리에서 제공하는 기능을 사용하여 카메라의 영상을 읽어온다.
class CamOpenCV
{
public:
	CamOpenCV(const char *windowName, int cameraID, int width, int height);
	// 카메라 영상을 표시할 윈도우 이름, 연결하고 있는 카메라 id, 영상의 가로 해상도, 영상의 세로 해상도
	~CamOpenCV();

	// 카메라에서 영상을 읽어와 클래스 내부에 있는 버퍼에 저장한다.
	bool CaptureImage();

	// 카메라에서 읽은 이미지 정보를 알려준다.
	BITMAPINFO *GetBitmapInfo();

	// 카메라에서 읽은 이미지의 주소를 알려준다.
	const BYTE *GetImage();

	IplImage *GetCvImage() { return _cvImage; }

private:
	BITMAPINFO _bitmapInfo;		// 카메라의 정보를 저장
	IplImage *_cvImage;			// 영상 정보를 저장
	CvCapture *_capture;		// 카메라 장치를 설정
};


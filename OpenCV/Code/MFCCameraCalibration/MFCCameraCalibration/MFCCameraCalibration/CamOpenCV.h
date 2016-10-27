#pragma once

#include <opencv/cv.h>
#include <opencv/highgui.h>

// OpenCV ���̺귯������ �����ϴ� ����� ����Ͽ� ī�޶��� ������ �о�´�.
class CamOpenCV
{
public:
	CamOpenCV(const char *windowName, int cameraID, int width, int height);
	// ī�޶� ������ ǥ���� ������ �̸�, �����ϰ� �ִ� ī�޶� id, ������ ���� �ػ�, ������ ���� �ػ�
	~CamOpenCV();

	// ī�޶󿡼� ������ �о�� Ŭ���� ���ο� �ִ� ���ۿ� �����Ѵ�.
	bool CaptureImage();

	// ī�޶󿡼� ���� �̹��� ������ �˷��ش�.
	BITMAPINFO *GetBitmapInfo();

	// ī�޶󿡼� ���� �̹����� �ּҸ� �˷��ش�.
	const BYTE *GetImage();

	IplImage *GetCvImage() { return _cvImage; }

private:
	BITMAPINFO _bitmapInfo;		// ī�޶��� ������ ����
	IplImage *_cvImage;			// ���� ������ ����
	CvCapture *_capture;		// ī�޶� ��ġ�� ����
};


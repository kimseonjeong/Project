#include "stdafx.h"
#include "CamOpenCV.h"


CamOpenCV::CamOpenCV(const char *windowName, int cameraID, int width, int height) 
	: _cvImage(NULL), _capture(NULL)
{
	_capture = cvCaptureFromCAM(cameraID);

	if (_capture) {
		cvSetCaptureProperty(_capture, CV_CAP_PROP_FRAME_WIDTH, width);
		cvSetCaptureProperty(_capture, CV_CAP_PROP_FRAME_HEIGHT, height);
	}

	memset(&_bitmapInfo, 0, sizeof(BITMAPINFO));
}


CamOpenCV::~CamOpenCV()
{
	if (_capture) {
		cvReleaseCapture(&_capture);
	}
}

BITMAPINFO *CamOpenCV::GetBitmapInfo()
{
	if (_cvImage) {
		int pixelByte = _cvImage->widthStep / _cvImage->width;

		_bitmapInfo.bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
		_bitmapInfo.bmiHeader.biWidth = _cvImage->width;
		_bitmapInfo.bmiHeader.biHeight = _cvImage->height; // top-down bitmap, negative height
		_bitmapInfo.bmiHeader.biPlanes = 1;
		_bitmapInfo.bmiHeader.biBitCount = pixelByte * 8;
		_bitmapInfo.bmiHeader.biCompression = BI_RGB;
		_bitmapInfo.bmiHeader.biSizeImage = _cvImage->width*_cvImage->height*pixelByte;
		_bitmapInfo.bmiHeader.biXPelsPerMeter = 100;
		_bitmapInfo.bmiHeader.biYPelsPerMeter = 100;
		_bitmapInfo.bmiHeader.biClrUsed = 0;
		_bitmapInfo.bmiHeader.biClrImportant = 0;
	}
	return &_bitmapInfo;
}

bool CamOpenCV::CaptureImage()
{
	if (_capture) {
		if (cvGrabFrame(_capture)) {
			_cvImage = cvRetrieveFrame(_capture);

			return (_cvImage) ? true : false;
		}
	}
	return false;
}

const BYTE *CamOpenCV::GetImage()
{
	if (_cvImage) {
		return (const BYTE *)_cvImage->imageData;
	}
	else {
		return NULL;
	}
}

#include "stdafx.h"
#include "CamCalib.h"


CamCalib::CamCalib(int no_corners_width, int no_corners_height, int no_rec_boards, double grid_width, double grid_height)
	: _no_corners_width(no_corners_width), _no_corners_height(no_corners_height), 
	_no_rec_boards(no_rec_boards), _grid_width(grid_width), _grid_height(grid_height)
{
	_no_corners_total = _no_corners_width*_no_corners_height;

	// ü�������κ��� ã�� �ڳʸ� ������ ������� �Ҵ�
	_image_points = cvCreateMat(_no_rec_boards*_no_corners_total, 2, CV_32FC1);
	_object_points = cvCreateMat(_no_rec_boards*_no_corners_total, 3, CV_32FC1);
	_point_counts = cvCreateMat(_no_rec_boards, 1, CV_32SC1);

	//Intrinsic Matrix - 3x3			   Lens Distorstion Matrix - 4x1
	//	[fx 0 cx]							[k1 k2 p1 p2   k3(optional)]
	//	[0 fy cy]
	//	[0  0  1]

	_intrinsic_matrix = NULL;
	_distortion_coeffs = NULL;
	rotationVector = NULL;
	translationVector = NULL;

	_mapx = NULL;
	_mapy = NULL;

	_success_count = 0;
}


CamCalib::~CamCalib()
{
	cvReleaseMat(&_object_points);
	cvReleaseMat(&_image_points);
	cvReleaseMat(&_point_counts);

	if (_intrinsic_matrix)  cvReleaseMat(&_intrinsic_matrix);
	if (_distortion_coeffs) cvReleaseMat(&_distortion_coeffs);
	if (rotationVector) cvReleaseMat(&rotationVector);
	if (translationVector) cvReleaseMat(&translationVector);

	if (_mapx) cvReleaseImage(&_mapx);
	if (_mapy) cvReleaseImage(&_mapy);
}

void CamCalib::LoadCalibParams(CvSize &image_size)
{
	// ���Ϸ� ����� ������İ� �ְ� ����� �ҷ�����
	_intrinsic_matrix = (CvMat *)cvLoad("Intrinsics.xml");
	_distortion_coeffs = (CvMat *)cvLoad("Distortion.xml");
	rotationVector = (CvMat *)cvLoad("rotationVector.xml");
	translationVector = (CvMat *)cvLoad("translationVector.xml");

	if (_intrinsic_matrix && _distortion_coeffs) {
		// �ְ� ���Ÿ� ���� ������ ����
		_mapx = cvCreateImage(image_size, IPL_DEPTH_32F, 1);
		_mapy = cvCreateImage(image_size, IPL_DEPTH_32F, 1);

		// �ְ� ���Ÿ� ���� ������ ����
		cvInitUndistortMap(_intrinsic_matrix, _distortion_coeffs, _mapx, _mapy);

		_success_count = _no_rec_boards + 1;
	}
}

bool CamCalib::FindChessboard(IplImage *src, IplImage *dest)
{
	IplImage *gray = cvCreateImage(cvGetSize(src), IPL_DEPTH_8U, 1);

	cvCvtColor(src, gray, CV_BGR2GRAY);

	// ü���� �ڳ� ã��
	CvPoint2D32f* corners = new CvPoint2D32f[_no_corners_total];
	int corner_count = 0;
	int found = cvFindChessboardCorners(src, cvSize(_no_corners_width, _no_corners_height), corners, &corner_count, 
		CV_CALIB_CB_ADAPTIVE_THRESH | CV_CALIB_CB_FILTER_QUADS);

	// ����� �ڳʷκ��� �����ȼ� ��Ȯ���� �ڳ� ��ǥ�� ���Ѵ�.
	cvFindCornerSubPix(gray, corners, corner_count, cvSize(11, 11), cvSize(-1, -1), 
		cvTermCriteria(CV_TERMCRIT_EPS + CV_TERMCRIT_ITER, 30, 0.1));

	// �ڳʸ� dest �̹����� �׸���.
	cvDrawChessboardCorners(dest, cvSize(_no_corners_width, _no_corners_height), corners, corner_count, found);

	// �ڳʸ� ���������� ã�Ҵٸ�, �ڳ� �����͸� �����Ѵ�.
	bool ret = false;
	if (found && corner_count == _no_corners_total) {
		for (int i = _success_count*_no_corners_total, j = 0; j<_no_corners_total; ++i, ++j) {
			CV_MAT_ELEM(*_image_points, float, i, 0) = corners[j].x;
			CV_MAT_ELEM(*_image_points, float, i, 1) = corners[j].y;
			CV_MAT_ELEM(*_object_points, float, i, 0) = (float)((j%_no_corners_width)*_grid_width);
			CV_MAT_ELEM(*_object_points, float, i, 1) = (float)((_no_corners_height - j / _no_corners_width - 1)*_grid_height);
			CV_MAT_ELEM(*_object_points, float, i, 2) = 0.0f;
		}
		CV_MAT_ELEM(*_point_counts, int, _success_count, 0) = _no_corners_total;

		ret = true;
	}

	delete[] corners;
	cvReleaseImage(&gray);
	return ret;
}

void CamCalib::CalibrateCamera(CvSize &image_size)
{
	if (_intrinsic_matrix)  cvReleaseMat(&_intrinsic_matrix);
	if (_distortion_coeffs) cvReleaseMat(&_distortion_coeffs);
	if (rotationVector) cvReleaseMat(&rotationVector);
	if (translationVector) cvReleaseMat(&translationVector);

	if (_mapx) cvReleaseImage(&_mapx);
	if (_mapy) cvReleaseImage(&_mapy);

	_intrinsic_matrix = cvCreateMat(3, 3, CV_32FC1);
	_distortion_coeffs = cvCreateMat(4, 1, CV_32FC1);
	rotationVector = cvCreateMat(1, 3, CV_32FC1);
	translationVector = cvCreateMat(1, 3, CV_32FC1);

	// ���� �Ÿ� ������ 1.0���� �����Ͽ� ��������� �ʱ�ȭ
	CV_MAT_ELEM(*_intrinsic_matrix, float, 0, 0) = 1.0f;
	CV_MAT_ELEM(*_intrinsic_matrix, float, 1, 1) = 1.0f;

	// ���� ī�޶� �����Լ�
	cvCalibrateCamera2(_object_points, _image_points, _point_counts, image_size, 
		_intrinsic_matrix, _distortion_coeffs, NULL, NULL, 0);
	cvFindExtrinsicCameraParams2(_object_points, _image_points, 
		_intrinsic_matrix, _distortion_coeffs, rotationVector, translationVector, false);

	// ���� ��İ� �ְ� ����� ���Ϸ� ����
	cvSave("Intrinsics.xml", _intrinsic_matrix);
	cvSave("Distortion.xml", _distortion_coeffs);
	cvSave("rotationVector.xml", rotationVector);
	cvSave("translationVector.xml", translationVector);

	// �ְ� ���Ÿ� ���� ������ ����
	_mapx = cvCreateImage(image_size, IPL_DEPTH_32F, 1);
	_mapy = cvCreateImage(image_size, IPL_DEPTH_32F, 1);

	// �ְ� ���Ÿ� ���� ������ ����
	cvInitUndistortMap(_intrinsic_matrix, _distortion_coeffs, _mapx, _mapy);
}


void CamCalib::Undistort(IplImage *src, IplImage *dest)
{
	assert(_mapx);
	assert(_mapy);

	// ī�޶� �Է¿���(src)���� �ְ��� ������ ����(dest)�� �����.
	cvRemap(src, dest, _mapx, _mapy);			// Undistort image
}

#pragma once

#include <opencv/cv.h>
#include <opencv/highgui.h>

class CamCalib
{
public:
	CamCalib(int no_corners_width, int no_corners_height, int no_rec_boards, double grid_width, double grid_height);
	~CamCalib();

	void LoadCalibParams(CvSize &image_size);
	bool FindChessboard(IplImage *src, IplImage *dest);
	void Undistort(IplImage *src, IplImage *dest);
	void CalibrateCamera(CvSize &image_size);

	CvMat* _image_points;
	CvMat* _object_points;
	CvMat* _point_counts;

	CvMat* _intrinsic_matrix;
	CvMat* _distortion_coeffs;
	CvMat* rotationVector;
	CvMat* translationVector;

	IplImage* _mapx;
	IplImage* _mapy;

	double _grid_width;		// ü���ǿ��� �� ������ ���ι��� ����
	double _grid_height;	// ü���ǿ��� �� ������ ���ι��� ����

	int _no_rec_boards;		// �ν��� ü���� ���� �����Ѵ�.
	int _no_corners_width;	// ü������ ���ι��� �ڳ� ��
	int _no_corners_height;	// ü������ ���ι��� �ڳ� ��
	int _no_corners_total;	// ���� x ���� ������ �ڳ� ��

	int _success_count;		// ü���� �ν��� ������ ȸ�� ī��Ʈ
};


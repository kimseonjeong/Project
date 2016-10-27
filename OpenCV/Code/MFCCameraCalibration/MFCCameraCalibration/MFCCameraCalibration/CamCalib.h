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

	double _grid_width;		// 체스판에서 한 격자의 가로방향 넓이
	double _grid_height;	// 체스판에서 한 격자의 세로방향 넓이

	int _no_rec_boards;		// 인식할 체스판 수를 지정한다.
	int _no_corners_width;	// 체스판의 가로방향 코너 수
	int _no_corners_height;	// 체스판의 세로방향 코너 수
	int _no_corners_total;	// 가로 x 세로 방향의 코너 수

	int _success_count;		// 체스판 인식을 성공한 회수 카운트
};


package com.yedam.app.rvw.mapper;

import java.util.List;

import com.yedam.app.rvw.service.ReviewVO;

public interface ReviewMapper {
	
	//후기 전체조회
	public List<ReviewVO> reviewList();
	
	//후기 단건조회
	public ReviewVO reviewInfo(ReviewVO reviewVO);

	//후기 등록
	public int insertReview(ReviewVO reviewVO);
	
	//후기 수정
	public int updateReview(ReviewVO reviewVO);
	
	//후기 삭제
	public int deleteReview(int reviewNo);
}

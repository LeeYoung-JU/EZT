package com.yedam.app.tem.service;

import java.util.List;

import com.yedam.app.req.service.Criteria;

public interface TeamService {
	
	//팀신청 전체조회
	public List<TeamVO> teamList();
	
	//팀신청 단건조회
	public TeamVO teamInfo(TeamVO teamVO);
	
	//팀신청 등록
	public int insertTeam(TeamVO teamVO);
	
	//팀신청 수정
	public boolean updateTeam(TeamVO teamVO);
	
	//팀신청 삭제
	public int deleteTeam(int teamNO);
	
	//전체 데이터 갯수
	public int getTotal(Criteria cri);
	
	//전체 신청 팀원 수
	public int totalHeadCount(TeamWorkCategoryVO twcVO);
}

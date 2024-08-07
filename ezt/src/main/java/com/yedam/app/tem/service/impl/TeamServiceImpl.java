package com.yedam.app.tem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.req.service.Criteria;
import com.yedam.app.tem.mapper.TeamMapper;
import com.yedam.app.tem.service.MemberDenyVO;
import com.yedam.app.tem.service.MemberEnrollVO;
import com.yedam.app.tem.service.MemberVO;
import com.yedam.app.tem.service.TeamService;
import com.yedam.app.tem.service.TeamVO;
import com.yedam.app.tem.service.TeamWorkCategoryVO;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamMapper teamMapper;
	
	//팀신청 전체조회
	@Override
	public List<TeamVO> teamList(Criteria cri) {

		return teamMapper.teamList(cri);
	}

	//팀신청 단건조회
	@Override
	public TeamVO teamInfo(TeamVO teamVO) {

		return teamMapper.teamInfo(teamVO);
	}

	//팀신청 등록
	@Override
	public int insertTeam(TeamVO teamVO) {
		int result = teamMapper.insertTeam(teamVO);
		return result == 1 ? teamVO.getTeamNo() : -1;
	}

	//팀 수정
	@Override
	public boolean updateTeam(TeamVO teamVO) {
	
		return teamMapper.updateTeam(teamVO) == 1;
	}
	
	//팀신청 삭제
	@Override
	public int deleteTeam(int teamNo) {

		return teamMapper.deleteTeam(teamNo);
	}
	//의뢰 찾기
	@Override
	public TeamVO findRequest(TeamVO teamVO) {
		
		return teamMapper.findRequest(teamVO);
	}
	//전체 페이지 수
	@Override
	public int getTotal(Criteria cri) {

		return teamMapper.getTotalCount(cri);
	}

	//팀 상세 등록
	@Override
	public int insertCategory(TeamWorkCategoryVO twcVO) {
		int result = teamMapper.insertCategory(twcVO);
		return result == 1 ? twcVO.getTeamNo() : -1;
	}
	
	//팀 상세 수정
	@Override
	public boolean updateCategory(TeamWorkCategoryVO twcVO) {
		
		return teamMapper.updateCategory(twcVO) == 1;
	}
	
	//팀 상세 삭제
	@Override
	public int deleteCategory(TeamWorkCategoryVO twcVO) {
		
		return teamMapper.deleteCategory(twcVO);
	}
	
	//팀 모집 완료
	@Override
	public boolean completeTeam(TeamVO teamVO) {
		
		return teamMapper.completeTeam(teamVO) == 1;
	}
	//카테고리별 지원자
	@Override
	public List<MemberEnrollVO> volunteerList(MemberEnrollVO memberEnrollVO) {
		
		return teamMapper.volunteerList(memberEnrollVO);
	}

	//팀원 신청 반려(프로시저 이용)
	@Override
	public boolean updateMemberEnroll(MemberDenyVO memberDenyVO) {
		
		return teamMapper.updateMemberEnroll(memberDenyVO) == -1;
	}

	//팀원 신청하기
	@Override
	public int insertMember(MemberEnrollVO memberEnrollVO) {
		int result = teamMapper.insertMember(memberEnrollVO);
		return result == 1 ? memberEnrollVO.getTeamNo() : 1;
		
	}

	//신청자 승인하기
	@Override
	public boolean approveMember(MemberVO memberVO) {
		
		return teamMapper.approveMember(memberVO) == 1;
	}








}

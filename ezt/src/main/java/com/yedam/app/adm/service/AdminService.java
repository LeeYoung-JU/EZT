package com.yedam.app.adm.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.usr.service.UserVO;

public interface AdminService {
	// 일별 가입자 통계
	public List<Map<String, Object>> getJoinStatistics();
	// 의뢰 작업 분야별 통계
	public List<Map<String, Object>> getReqCategoryStatistics();
	// 의뢰 지역별 통계
	public List<Map<String, Object>> getReqRegionStatistics();

	
	// 회원 전체 조회
	public List<UserVO> getUsers();
	// 회원 단건 조회
	public UserVO getUser(int uno);
	// 회원 정지
	public int setUserPause(int uno);
	// 회원 정지 해제
	public int setUserActive(int uno);
	
	
	// 통일 계약서 전체 조회
	public List<UnityContractVO> getUnityContracts();
	// 통일 계약서 단건 조회
	public UnityContractVO getUnityContract(int no);
	// 통일 계약서 등록
	public int postUnityContract(UnityContractVO vo);
}

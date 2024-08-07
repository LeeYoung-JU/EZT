package com.yedam.app.adm.service;

import java.util.List;
import java.util.Map;

import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;

public interface AdminService {
	
	// dashboard main
	public Map<String, Object> getStatistic();

	
	// 의뢰 전체 조회
	public List<RequestVO> getRequests();
	// 의뢰 단건 조회
	public Map<String, Object> getRequest(int requestNo);
	// 의뢰 삭제
	public int deleteRequest();
	
	
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
	public UnityContractVO postUnityContract(UnityContractVO vo);
	// 통일 계약서 기본 게약서 업데이트
	public int putUnityContractBasicTf(int no);
	
	// 경력 인증 전체 조회
	public List<CareerVO> getCareers();
	// 경력 인증 단건 조회
	public CareerVO getCareer(int no);
	// 경력 인증 승인
	public int setCareerAccept(int no);
	// 경력 인증 반려
	public int setCareerDeny(int no);
	// 경력 인증 반려 사유 등록
	public int postCareerDeny(Map<String, String> map);
	
	// 결제 대금 관리 전체 조회
	public List<Map<String, Object>> getPayManages();
	
	// 결제 대금 관리 상세 조회
	public Map<String,Object> getPayManage(int payNo);
	
}

package com.yedam.app.adm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.adm.mapper.AdminMapper;
import com.yedam.app.adm.service.AdminService;
import com.yedam.app.doc.service.ContractService;
import com.yedam.app.doc.service.ContractVO;
import com.yedam.app.doc.service.ProposalService;
import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.doc.service.UnityContractVO;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;
import com.yedam.app.rpt.service.CttReportVO;
import com.yedam.app.tem.service.MemberVO;
import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.service.CareerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
	AdminMapper admMapper;
	@Autowired
	RequestService reqSvc;
	@Autowired
	ContractService conSvc;
//	@Autowired
//	ProposalService ppsSvc;
	// 통계관련
	@Override
	public Map<String, Object> getStatistic(){
		Map<String, Object> map = new HashMap<>();
		map.put("newJoin", admMapper.selectJoinStatistics()); // 일별 가입자 현황
		map.put("reqCategory", admMapper.selectReqCategoryStatistics()); // 의뢰 분야별 통계
		map.put("reqRegion", admMapper.selectReqRegionStatistics()); // 의뢰 지역별 통계
		map.put("crrWaitCnt", admMapper.selectCrrAcptWaitCnt());
		map.put("totalUsersCnt", admMapper.selectUsersCnt());
		map.put("totalReqCnt", admMapper.selectReqCnt());
		return map;
	}
	

	// 의뢰 관련
	@Override
	public List<RequestVO> getRequests() {
		return admMapper.selectRequests();
	}

	@Override
	public Map<String, Object> getRequest(int requestNo) {
		Map<String, Object> map = new HashMap<>();
		// 의뢰 단건
		RequestVO reqVO = new RequestVO();
		reqVO.setRequestNo(requestNo);
		map.put("reqInfo", reqSvc.requestInfo(reqVO));
		// 견적서 관련
		ProposalVO propVO = new ProposalVO();
		propVO.setRequestNo(requestNo);
		List<ProposalVO> propList = reqSvc.proposalList(propVO);
		if (propList.size() > 0) {
			map.put("propInfo", propList);
		}
		// 계약서 관련
		ContractVO contVO = new ContractVO();
		contVO.setRequestNo(requestNo);
		contVO = reqSvc.contractInfo(contVO);
		if (contVO != null) {
			contVO = conSvc.conInfo(contVO);
			UserVO worker = admMapper.selectUser(contVO.getWorkerInfo());
			UserVO requester = admMapper.selectUser(contVO.getRequesterInfo());
			Map<String, Object> m = new HashMap<>();
			m.put("contract", contVO);
			m.put("worker", worker);
			m.put("requester", requester);
			map.put("contInfo", m);
		}
		// 공사보고 관련
		List<CttReportVO> cttRptList = reqSvc.cttReportList(requestNo);
		if (cttRptList.size() > 0) {
			map.put("cttRptInfo", cttRptList);
		}
		// 팀원 관련
		List<MemberVO> memList = reqSvc.memberList(requestNo);
		if (memList.size() > 0) {
			map.put("memInfo", memList);
		}
		log.info(map.toString());
		return map;
	}

	@Override
	public int deleteRequest() {
		return 0;
	}
	
	
	// 유저관련
	@Override
	public List<UserVO> getUsers() {
		return admMapper.selectUsers();
	}

	@Override
	public UserVO getUser(int uno) {
		return admMapper.selectUser(uno);
	}

	@Override
	public int setUserPause(int uno) {
		return admMapper.updateUsersStatePause(uno);
	}

	@Override
	public int setUserActive(int uno) {
		return admMapper.updateUsersStateActive(uno);
	}

	
	// 통일 계약서 관련
	@Override
	public List<UnityContractVO> getUnityContracts() {
		return admMapper.selectUnityContracts();
	}
	@Override
	public UnityContractVO getUnityContract(int no) {
		return admMapper.selectUnityContract(no);
	}
	@Override
	@Transactional
	public UnityContractVO postUnityContract(UnityContractVO vo) {
		admMapper.insertUnityContract(vo);	
		return admMapper.selectUnityContract(vo.getUnityContractNo());
	}
	@Override
	@Transactional
	public int putUnityContractBasicTf(int no) {
		admMapper.updateUnityContractBasicTfN();
		return admMapper.updateUnityContractBasicTfY(no);
	}
	
	// 경력 인증 관련
	@Override
	public List<CareerVO> getCareers() {
		return admMapper.selectCareers();
	}
	
	@Override
	public CareerVO getCareer(int no) {
		return admMapper.selectCareer(no);
	}

	@Override
	@Transactional
	public int setCareerAccept(int no) {
		admMapper.deleteCareerDeny(no);
		return admMapper.updateCareerAccept(no);
	}

	@Override
	public int setCareerDeny(int no) {
		return admMapper.updateCareerDeny(no);
	}

	@Override
	@Transactional
	public int postCareerDeny(Map<String, String> map) {
		admMapper.deleteCareerDeny(Integer.parseInt(map.get("careerNo")));
		admMapper.updateCareerDeny(Integer.parseInt(map.get("careerNo")));
		return admMapper.insertCareerDeny(map);
	}

	@Override
	public List<Map<String, Object>> getPayManages() {
		return admMapper.selectPayManages();
	}


	@Override
	public Map<String, Object> getPayManage(int payNo) {
		Map<String, Object> map = new HashMap<>();
		map.put("info", admMapper.selectPayManage(payNo));
		map.put("details", admMapper.selectContractDetails(payNo));
		return map;
	}

	
}

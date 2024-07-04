package com.yedam.app.wkr.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.usr.service.UserVO;
import com.yedam.app.wkr.mapper.WorkerMapper;
import com.yedam.app.wkr.service.CareerVO;
import com.yedam.app.wkr.service.WorkerService;

@Service
public class WorkerServiceImpl implements WorkerService{
	@Autowired
	WorkerMapper workerMapper;
	
	//작업자 정보 조회
	@Override
	public List<Map<String, Object>> selectCategoryInfo(int id) {
		return workerMapper.selectCategoryInfo(id);
	}

	@Override
	public List<Map<String, Object>> selectRegionInfo(int id) {
		return workerMapper.selectRegionInfo(id);
	}

	//작업자 정보 수정
	@Transactional
	@Override
	public Map<String, Object> updateWorker(UserVO userVO) {
		Map<String, Object> map = new HashMap<>();
		boolean isSuccessed = false;
		
		int result = workerMapper.updateWorkerInfo(userVO);
		if(result == 1) {
			isSuccessed = true;
		}
		
		workerMapper.deleteWorkerCategory(userVO);
		workerMapper.deleteWorkerRegion(userVO);
		workerMapper.insertWorkerCode(userVO);
		
		map.put("result", isSuccessed);
		map.put("target", userVO);
		
		return map;
	}
	
	//작업자 비밀번호 변경
	@Override
	public boolean updateWorkerPw(UserVO userVO) {
		return workerMapper.updateWorkerPw(userVO) == 1;
	}

	//작업자 탈퇴(상태 수정)
	@Override
	public boolean workerStateUpdate(UserVO userVO) {
		return workerMapper.updateWorkerState(userVO) == 1;
	}
	

	@Override
	public List<CareerVO> selectCareerList(UserVO userVO) {
		return workerMapper.selectCareerList(userVO);
	}

	@Override
	public int insertCareer(CareerVO careerVO) {
		return workerMapper.insertCareer(careerVO);
	}

	

}

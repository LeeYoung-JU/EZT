package com.yedam.app.req.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.doc.service.ProposalVO;
import com.yedam.app.req.mapper.RequestMapper;
import com.yedam.app.req.service.RequestService;
import com.yedam.app.req.service.RequestVO;

@Service
public class RequestServiceImpl implements RequestService {

	@Autowired
	RequestMapper requestMapper;
	
	//의뢰 전체조회
	@Override
	public List<RequestVO> requestList() {
		
		return requestMapper.selectRequestAll();
	}
	
	//의뢰 단건조회
	@Override
	public RequestVO requestInfo(RequestVO requestVO) {
		
		return requestMapper.selectRequestInfo(requestVO);
	}
	
	//의뢰 수정
	@Override
	public boolean updateRequest(RequestVO requestVO) {
		
		return requestMapper.updateRequestInfo(requestVO) == 1;
	}

	//견적서 전체조회
	@Override
	public List<ProposalVO> proposalList(ProposalVO proposalVO) {
		
		return requestMapper.selectProposalAll(proposalVO);
	}
	


	
}

package com.yedam.app.sgu.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;


import com.yedam.app.usr.mapper.UserMapper;
import com.yedam.app.usr.service.UserVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpService {
	//회원가입시 저장시간 넣어줄 DateRime형
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
	Date time = new Date();
	String localTime = format.format(time);
	
	@Autowired
	UserMapper userMapper;
	
	@Transactional
	public void joinUser(@Valid UserVO userVO) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userVO.setUsersPw(passwordEncoder.encode(userVO.getUsersPw()));
		userVO.setUsersRole("ROLE_USER");
		userMapper.saveUser(userVO);
	}
	
	//회원가입시 유효성 체크
	@Transactional(readOnly = true)
	public Map<String, String> validateHandling(Errors errors){
		Map<String, String> validatorResult = new HashMap<>();
		
		//유효성 검사에 실패한 필드목록을 받음
		for(FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}
		
		
		return validatorResult;
	}

	
	
}

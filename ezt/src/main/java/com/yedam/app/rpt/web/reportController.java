package com.yedam.app.rpt.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.fie.service.FileService;


@Controller
public class reportController {

	@Autowired
	FileService fileService;
	
	@PostMapping("rptFileInsert")
	@ResponseBody
	public List<FileVO> rptFileInsert(MultipartFile[] uploadFile) {
		
		List<FileVO> list = fileService.uploadFiles(uploadFile);
		
		return list;
	}
	
	@PostMapping("rptFileDelete")
	@ResponseBody
	public ResponseEntity<String> rptFileDelete(@RequestBody List<FileVO> fileVO) throws UnsupportedEncodingException {

		fileService.deleteFile(fileVO);
		List<FileVO> sFileVO = new ArrayList<FileVO>();

		for(FileVO file : fileVO) {
			file.setSaveName("s_" + file.getSaveName());
			sFileVO.add(file);
		}
		fileService.deleteFile(sFileVO);
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		return fileService.getFile(fileName);
	}
}

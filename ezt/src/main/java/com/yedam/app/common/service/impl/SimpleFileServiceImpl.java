package com.yedam.app.common.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.tools.UnsupportedPointcutPrimitiveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.mapper.SimpleFileMapper;
import com.yedam.app.common.service.FileVO;
import com.yedam.app.common.service.SimpleFileService;
import com.yedam.app.fie.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SimpleFileServiceImpl implements SimpleFileService {
	@Value("${file.upload.path}")
	private String uploadFolder;
	
	@Autowired SimpleFileMapper sfm;
	@Autowired FileService fsvc;
	
	// 폴더 저장 경로
	private String getForder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	@Override
	public int uploadFiles(MultipartFile[] uploadFile) {
		FileVO vo = new FileVO();
		sfm.insertFileAttrInfo(vo);
		List<FileVO> list = uploadFile(uploadFile);
		log.info(list.toString());
		int i = 0;
//		list.forEach(e -> {
//			log.info(e.toString());
//			e.setFileId(vo.getFileId());
//			if (i == 0) {
//				e.setBossTf("Y");
//			} else {
//				e.setBossTf("N");
//			}
//			i += 1;
//			sfm.insertFileInfo(e);
//		});
		for (FileVO e : list) {
			log.info(e.toString());
			e.setFileId(vo.getFileId());
			if (i == 0) {
				e.setBossTf("Y");
			} else {
				e.setBossTf("N");
			}
			i += 1;
			sfm.insertFileInfo(e);
		}
		return vo.getFileId();
	}
	
	
	public List<FileVO> uploadFile(MultipartFile[] uploadFile) {
		// 폴더 경로
//		String uploadFolder = "C:\\temp";
		List<FileVO> list = new ArrayList<>();

		String uploadFolderPath = getForder();
		// 폴더 만들기
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {
//			if (!multipartFile.isEmpty()) {
				FileVO fileVO = new FileVO();
				// 콘솔 출력
				log.info("Upload File Name : " + multipartFile.getOriginalFilename());
				log.info("Upload File Size : " + multipartFile.getSize());
				log.info("Upload reName : " + multipartFile.getName());
				log.info("Upload ContentType : " + multipartFile.getContentType());
				// filVO에 값 넣기
				String uploadFileName = multipartFile.getOriginalFilename();
				int nameindex = uploadFileName.indexOf('.');
				fileVO.setOriginalFileName(uploadFileName.substring(0, nameindex));

				UUID uuid = UUID.randomUUID();
				uploadFileName = uuid.toString() + "_" + uploadFileName;

				int nameidx = uploadFileName.indexOf("_");
				fileVO.setSaveName(uploadFileName.substring(0, nameidx));

				fileVO.setSavePath(uploadFolderPath);
				fileVO.setFileSize((int) multipartFile.getSize());

				int index = uploadFileName.indexOf(".");
				fileVO.setExt(uploadFileName.substring(index + 1));

				try {
					File saveFile = new File(uploadPath, uploadFileName);

					multipartFile.transferTo(saveFile);
					list.add(fileVO);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
//			}
		}
		return list;
	}
	
	
	@Override
	public int deleteFile(List<FileVO> fileVO) throws UnsupportedEncodingException {
		
		for (FileVO list : fileVO) {

			// 콘솔 출력
			log.info("Upload File Name : " + list.getOriginalFileName());
			log.info("Upload File Size : " + list.getFileSize());
			log.info("Upload reName : " + list.getSaveName());
			log.info("Upload Path : " + list.getSavePath());
			
			String fileName = list.getSavePath().replace("\\", "/") + '/' + list.getSaveName() + "_" 
						+ list.getOriginalFileName() + "." + list.getExt();
			try {
				File file = new File("C:\\temp\\" + URLDecoder.decode(fileName, "UTF-8"));

				file.delete();

			} catch (UnsupportedPointcutPrimitiveException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
	}
	
	@Override
	public ResponseEntity<Resource> downlodeFile(String fileName) {
		log.info("download file: " + fileName);

		FileSystemResource resource = new FileSystemResource("C:\\temp\\" + fileName);

		log.info("resource : " + resource);

		String resourceName = resource.getFilename();

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		try {
			headers.add("Content-Disposition",
					"attachment; filename=" + new String(resourceName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
}

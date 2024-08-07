package com.yedam.app.fie.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.aspectj.weaver.tools.UnsupportedPointcutPrimitiveException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.common.service.FileVO;
import com.yedam.app.fie.service.FileService;

import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
	
	@Value("${file.upload.path}")
	String uploadFolder;
	
	// 폴더 저장 경로
	private String getForder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}

	// 이미지 체크
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());

			return contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<FileVO> uploadFiles(MultipartFile[] uploadFile) {
		// 폴더 경로

		List<FileVO> list = new ArrayList<>();

		String uploadFolderPath = getForder();
		// 폴더 만들기
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);

		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		for (MultipartFile multipartFile : uploadFile) {
			if (!multipartFile.isEmpty()) {
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
					
					
					if (checkImageType(saveFile)) {
						// 이미지 파일 섬네일 저장
						// transferTo가 먼저 일어나면 섬네일 이미지로 읽어야할 파일(업로드 되기 전 저장된)이 먼저 삭제됨
						// saveFile을 이용해 업로드된 파일을 읽음
						FileInputStream inputFile = new FileInputStream(saveFile);
						
						FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
	                    // createThumbnail(inputStream, outputStream, width, height)
	                    // InputStream과 File객체를 이용하여 파일을 생성한다
	                    Thumbnailator.createThumbnail(inputFile, thumbnail, 100, 100);
	                    
	                    thumbnail.close();
	                    inputFile.close();
	                    
					}
					
					list.add(fileVO);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	// 파일 삭제
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
				File file = new File(uploadFolder + URLDecoder.decode(fileName, "UTF-8"));
				log.info("File :" + file);
				file.delete();

			} catch (UnsupportedPointcutPrimitiveException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return 1;
	}
	
	// 파일 다운로드
	@Override
	public ResponseEntity<Resource> downlodeFile(String fileName) {
		log.info("download file: " + fileName);

		FileSystemResource resource = new FileSystemResource(uploadFolder + fileName);

		if(resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		log.info("resource : " + resource);

		//String resourceName = resource.getFilename();
		int no = fileName.indexOf("_") + 1;
		String fName = fileName.substring(no);
		
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		try {
			
			headers.add("Content-Disposition",
					"attachment; filename=" + new String(fName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	// 파일 호출
	@Override
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName: " + fileName);
		
		File file = new File(uploadFolder + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			org.springframework.http.HttpHeaders header = new org.springframework.http.HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
					
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}

package kr.msp.example.file;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class FileService {

	private final Path uploadLocation;

	public FileService(FileProperties fileProperties) {
		this.uploadLocation = Paths.get(fileProperties.getLocation()).toAbsolutePath().normalize();

	}

	/**
	 * 전달받은 파일을 특정 경로에 업로드 한뒤,
	 * 파일의 정보를 FileInfo 객체에 담아 리턴하는
	 * 파일 업로드 처리 메서드
	 * @param multipartFile 업로드할 파일을 담고있는 MultipartFile 객체
	 * @return 파일의 정보가 담긴 FileInfo 객체
	 */
	public FileInfo uploadFile(MultipartFile multipartFile) {
		//파일 경로 및 파일 정보 변수 초기화
		Path location = null;
		FileInfo fileInfo = new FileInfo();
		try {
			// MultipartFile에서 원본 파일 이름 추출
			String fileName = multipartFile.getOriginalFilename();
			// 파일 이름이 존재하는 경우 저장할 파일의 전체 경로 결정
			if (fileName != null) {
				//application.yml file.upload-location 경로
				location = uploadLocation.resolve(fileName);
			}
			//경로가 유효한 경우
			if (location != null) {
				//동일한 이름의 기존 파일이 있으면 덮어씀
				Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
				//FileInfo 객체에 관련 정보 채우기
				//Location: 저장된 파일의 절대 경로
				fileInfo.setLocation(location.toAbsolutePath().toString());
				//Size: 업로드된 파일의 크기
				fileInfo.setSize(multipartFile.getSize());
				//Ext: 파일의 콘텐츠 유형 (예: "image/jpeg", "text/plain")
				fileInfo.setExt(multipartFile.getContentType());
			}
			//파일 정보가 담긴 FileInfo 객체 반환
			return fileInfo;
		} catch (IOException | NullPointerException e) {
			//Exception에 대한 예외 처리
			return new FileInfo();
		}
	}

	/**
	 * 특정 파일 다운로드 처리 메서드
	 * @param fileName 다운받을 파일명
	 * @return 리소스 객체(파일이 존재하지 않으면 null)
	 * @throws MalformedURLException UrlResource 객체 생성 실패 시 발생
	 */
	public Resource downloadFile(String fileName) throws MalformedURLException {
		Resource resource = null;
		//다운로드 위치 경로와 파일 이름을 결합하여 전체 경로 생성
		Path filePath = uploadLocation.resolve(fileName).normalize();
		//파일 객체 생성
		File file = new File(filePath.toUri());
		//파일 존재 여부 확인
		if(file.isFile() || file.exists()){
			//파일이 존재할 경우 리소스 객체 생성
			resource = new UrlResource(filePath.toUri());
		}
		//리소스 객체 반환(파일이 존재하지 않으면 null)
		return resource;
	}
}

package edu.skku.EduQuizzify.src.domain.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class S3Uploader {

	private final AmazonS3 amazonS3;
	private final String bucket;

	public S3Uploader(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucket) {
		this.amazonS3 = amazonS3;
		this.bucket = bucket;
	}

	public String upload(MultipartFile multipartFile, String sectionId) throws IOException {
		// 파일 이름에서 공백을 제거한 새로운 파일 이름 생성
		String originalFileName = multipartFile.getOriginalFilename();

		// 파일명에 고유 섹션 ID 추가 및 공백 제거
		String uniqueFileName = sectionId + "_" + originalFileName.replaceAll("\\s", "_");

		log.info("fileName: " + uniqueFileName);

		// S3에 파일 업로드 (멀티파트 파일의 InputStream을 사용하여 업로드)
		String uploadImageUrl = putS3(multipartFile.getInputStream(), uniqueFileName, multipartFile.getSize());

		return uploadImageUrl;
	}

	private String putS3(InputStream inputStream, String fileName, long contentLength) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(contentLength);  // 파일 크기를 설정

		amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));
		return amazonS3.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("파일이 삭제되었습니다.");
		} else {
			log.info("파일이 삭제되지 못했습니다.");
		}
	}

	public void deleteFile(String fileName) {
		try {
			// URL 디코딩을 통해 원래의 파일 이름을 가져옵니다.
			String decodedFileName = URLDecoder.decode(fileName, "UTF-8");
			log.info("Deleting file from S3: " + decodedFileName);
			amazonS3.deleteObject(bucket, decodedFileName);
		} catch (UnsupportedEncodingException e) {
			log.error("Error while decoding the file name: {}", e.getMessage());
		}
	}
}
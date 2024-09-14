package edu.skku.EduQuizzify.src.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.skku.EduQuizzify.src.api.dto.EmbeddingRequestDto;

@Service
public class FileProcessingService {

	@Value("${fastapi.url}")
	private String fastapiUrl; // 환경 변수에서 FastAPI URL을 주입받음

	private final RestTemplate restTemplate;

	public FileProcessingService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	public void requestEmbeddingCreation(String s3FileUrl, String sectionId) {
		// EmbeddingRequestDto 생성
		EmbeddingRequestDto requestDto = new EmbeddingRequestDto(s3FileUrl, sectionId);

		// 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");

		// HTTP 요청 생성
		HttpEntity<EmbeddingRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);

		// FastAPI에 POST 요청을 보냄
		String apiUrl = fastapiUrl + "/create_embedding";

		try {
			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
				String.class);
			System.out.println("FastAPI 응답: " + response.getBody());
		} catch (Exception e) {
			System.err.println("FastAPI 임베딩 생성 요청 중 오류 발생: " + e.getMessage());
		}
	}
}

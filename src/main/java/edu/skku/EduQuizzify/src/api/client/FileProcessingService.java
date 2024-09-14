package edu.skku.EduQuizzify.src.api.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.skku.EduQuizzify.src.api.dto.QuizRequestDto;
import edu.skku.EduQuizzify.src.api.dto.QuizResponseDto;
import edu.skku.EduQuizzify.src.api.dto.SummarizeFileRequestDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileProcessingService {

	@Value("${fastapi.url}")
	private String fastapiUrl; // FastAPI URL

	private final RestTemplate restTemplate;

	public FileProcessingService(RestTemplateBuilder builder) {
		this.restTemplate = builder.build();
	}

	public Object processFileByType(String processType, String sectionId, String focusPrompt) {
		switch (processType) {
			case "summary":
				// 요약 요청
				return requestSummaryCreation(processType, sectionId, focusPrompt);

			case "quiz":
				// 퀴즈 유형을 설정하고 퀴즈 생성 요청
				String quizType = "multiple";  // 객관식으로 설정
				return requestQuizCreation(sectionId, quizType, focusPrompt);

			// 주관식 추가

			//
			// case "study_card":
			// 	// 학습 카드 생성 요청
			// 	fileProcessingService.requestStudyCardCreation(s3FileUrl, sectionId, focusPrompt);
			// 	break;

			default:
				log.warn("알 수 없는 processType: " + processType);
		}
		return null;
	}

	// 요약 생성 요청
	public String requestSummaryCreation(String processType, String sectionId, String focusPrompt) {
		SummarizeFileRequestDto requestDto = new SummarizeFileRequestDto(processType, sectionId, focusPrompt);
		return sendPostRequest("/files/summary", requestDto);
	}

	// 퀴즈 생성 요청
	// 퀴즈 생성 요청
	public QuizResponseDto requestQuizCreation(String sectionId, String quizType, String focusPrompt) {
		// QuizRequestDto 생성
		QuizRequestDto requestDto = new QuizRequestDto(sectionId, quizType, focusPrompt);

		// 서버에 POST 요청
		String jsonResponse = sendPostRequest("/files/quizes", requestDto);

		System.out.println("========================================");
		System.out.println(jsonResponse);
		System.out.println("========================================");

		// JSON 응답을 QuizResponse 객체로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(jsonResponse, QuizResponseDto.class);  // QuizResponse 객체 반환
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// JSON 응답 파싱 함수
	private String parseQuizResponse(String jsonResponse) {
		if (jsonResponse == null) {
			return "퀴즈 데이터를 불러오는 데 실패했습니다.";
		}

		try {
			// Jackson ObjectMapper를 사용하여 JSON을 파싱
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode quizNode = objectMapper.readTree(jsonResponse).get("quiz");

			// 퀴즈 정보 처리
			List<String> quizQuestions = new ArrayList<>();
			for (JsonNode questionNode : quizNode.get("questions")) {
				String question = questionNode.get("question").asText();
				quizQuestions.add(question);
			}

			// 여기서는 예시로 첫 번째 퀴즈 질문만 반환
			return quizQuestions.isEmpty() ? "퀴즈가 없습니다." : quizQuestions.get(0);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "퀴즈 응답 처리 중 오류 발생";
		}
	}

	// 공통으로 사용하는 POST 요청 함수 (응답을 반환)
	private String sendPostRequest(String endpoint, Object requestDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(requestDto, headers);

		String apiUrl = fastapiUrl + endpoint;
		try {
			// FastAPI에 POST 요청을 보낸 후 응답을 받음
			ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity,
				String.class);

			// 응답 처리
			if (response.getStatusCode().is2xxSuccessful()) {
				log.info("FastAPI 응답 성공: {}", response.getBody());
				// 응답 본문 반환 (요약 내용이 포함될 가능성 있음)
				return response.getBody();
			} else {
				log.error("FastAPI 응답 실패: 상태 코드 - {}", response.getStatusCode());
				return "요약 요청 실패";
			}
		} catch (Exception e) {
			log.error("FastAPI 요청 중 오류 발생: {}", e.getMessage());
			return "요약 요청 중 오류 발생";
		}
	}

}

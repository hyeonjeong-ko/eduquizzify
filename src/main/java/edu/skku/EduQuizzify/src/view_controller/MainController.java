package edu.skku.EduQuizzify.src.view_controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import edu.skku.EduQuizzify.src.api.client.FileEmbeddingService;
import edu.skku.EduQuizzify.src.api.client.FileProcessingService;
import edu.skku.EduQuizzify.src.domain.s3.S3Uploader;
import edu.skku.EduQuizzify.src.model.Dummy;
import edu.skku.EduQuizzify.util.SectionIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {

	private final S3Uploader s3Uploader;
	private final FileProcessingService fileProcessingService;
	private final FileEmbeddingService fileEmbeddingService;

	private static final String TMP_USER_ID = "testUser"; // 임시 사용자 ID

	// 메인 페이지 (최근 기록)
	@GetMapping("/")
	public String showMainPage(Model model) {
		List<Dummy> recentRecords = new ArrayList<>();
		recentRecords.add(new Dummy("PDF 자료 요약", "pdf-summary"));
		recentRecords.add(new Dummy("블로그 자료 퀴즈", "blog-quiz"));
		recentRecords.add(new Dummy("유튜브 영상 학습 카드", "youtube-card"));

		model.addAttribute("recentRecords", recentRecords);
		return "main";  // main.html 연결
	}

	// 자료 제출 처리
	@PostMapping("/submit")
	public String handleSubmission(
		String inputType,  // 자료 유형
		String siteXmlLink, String blogLink,
		MultipartFile fileUpload, String youtubeLink,
		String processType, String quizType,
		String summaryFocus, Model model) {

		log.info("자료 제출 처리 시작");
		log.info("processType = {}", processType);
		log.info("quizType = {}", quizType);

		// 섹션 아이디 생성
		String sectionId = SectionIdGenerator.generateSectionId(TMP_USER_ID);

		// 입력 유형에 따라 처리
		switch (inputType) {
			case "file":
				// 파일 업로드 및 임베딩 처리
				String s3FileUrl = handleFileUpload(fileUpload, sectionId);
				if (s3FileUrl != null) {
					// FastAPI에 임베딩 생성 요청
					fileEmbeddingService.requestEmbeddingCreation(s3FileUrl, sectionId);
				} else {
					return null;
				}

				// 요약일때-------------------
				// 파일 처리를 위한 비즈니스 로직 호출 (예: 요약 생성, 퀴즈 생성 등)
				Object result = fileProcessingService.processFileByType(processType, sectionId, summaryFocus);

				model.addAttribute("summary", result);
				return "summary";

			case "blog":
				// 블로그 링크 처리
				log.info("블로그 링크 처리: {}", blogLink);
				// 추가적인 처리 로직 구현 필요
				break;

			case "youtube":
				// 유튜브 링크 처리
				log.info("유튜브 링크 처리: {}", youtubeLink);
				// 추가적인 처리 로직 구현 필요
				break;

			case "siteXml":
				// XML 사이트 링크 처리
				log.info("사이트 XML 링크 처리: {}", siteXmlLink);
				// 추가적인 처리 로직 구현 필요
				break;

			default:
				log.warn("알 수 없는 입력 유형: {}", inputType);
		}

		// 처리 후 메인 페이지로 리다이렉트
		return "redirect:/";
	}

	// 파일 업로드를 처리하는 메소드
	public String handleFileUpload(MultipartFile fileUpload, String sectionId) {
		if (fileUpload != null && !fileUpload.isEmpty()) {
			try {
				String originalFileName = fileUpload.getOriginalFilename();
				log.info("업로드된 파일 이름: {}", originalFileName);

				// S3에 파일 업로드
				String uploadedFileUrl = s3Uploader.upload(fileUpload, sectionId);
				log.info("파일이 성공적으로 업로드되었습니다. S3 URL: {}", uploadedFileUrl);

				return uploadedFileUrl;

			} catch (IOException e) {
				log.error("파일 업로드 중 오류 발생: {}", e.getMessage());
			}
		} else {
			log.warn("업로드된 파일이 없습니다.");
		}
		return null;
	}
}


/*
* if (quizResponse != null && quizResponse.getQuiz() != null) {
    // Quiz의 questions 리스트를 가져옴
    List<QuizResponseDto.Question> questions = quizResponse.getQuiz().getQuestions();

    // 각 question에 대해 루프를 돌며 데이터를 추출
    for (QuizResponseDto.Question question : questions) {
        // 질문 추출
        String questionText = question.getQuestion();
        System.out.println("Question: " + questionText);

        // 답변 리스트 추출
        List<QuizResponseDto.Answer> answers = question.getAnswers();
        for (QuizResponseDto.Answer answer : answers) {
            System.out.println("Answer: " + answer.getAnswer() + " (Correct: " + answer.isCorrect() + ")");
        }

        // 설명 추출
        String explanation = question.getExplanation();
        System.out.println("Explanation: " + explanation);
    }
* */
package edu.skku.EduQuizzify.src.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import edu.skku.EduQuizzify.src.model.Dummy;

@Controller
public class MainController {

	// 최근 기록을 위한 더미 데이터
	@GetMapping("/")
	public String showMainPage(Model model) {
		List<Dummy> recentRecords = new ArrayList<>();
		// 실제 데이터로 대체 가능
		recentRecords.add(new Dummy("PDF 자료 요약", "pdf-summary"));
		recentRecords.add(new Dummy("블로그 자료 퀴즈", "blog-quiz"));
		recentRecords.add(new Dummy("유튜브 영상 학습 카드", "youtube-card"));

		model.addAttribute("recentRecords", recentRecords);
		return "main";  // main.html 로 연결
	}

	// 자료 제출 처리
	@PostMapping("/submit")
	public String handleSubmission(String siteXmlLink, String blogLink,
		MultipartFile fileUpload, String youtubeLink,
		String processType, String quizType,
		String summaryFocus, Model model) {
		// 자료 처리 로직 (요약 생성, 학습 카드 생성, 퀴즈 생성 등)
		// 처리 후 결과를 모델에 추가하거나, 다른 페이지로 리다이렉트
		System.out.println("나실행돼????!!!!!!!!!!!!");
		System.out.println("processType = " + processType);
		System.out.println("quizType = " + quizType);

		switch (processType) {
			case "summary":
				System.out.println("fileUpload = " + fileUpload);
				System.out.println("summaryFocus = " + summaryFocus);
		}

		// 간단한 예시 처리 후 메인 페이지로 리다이렉트
		return "redirect:/";
	}

	// @PostMapping("/processSelection")
	// public String processSelection(@RequestParam("inputType") String inputType, Model model) {
	// 	model.addAttribute("selectedOption", inputType);
	// 	return "myPage";  // myPage는 동적 페이지를 렌더링할 템플릿 파일
	// }
}
package edu.skku.EduQuizzify.src.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/test")
	public String showTestPage() {
		// test.html 파일을 반환
		return "test";
	}

}
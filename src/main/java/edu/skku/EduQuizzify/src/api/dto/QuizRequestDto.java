package edu.skku.EduQuizzify.src.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QuizRequestDto(
	@JsonProperty("section_id") String sectionId,
	@JsonProperty("quiz_type") String quizType,
	@JsonProperty("focus_prompt") String focusPrompt
) {
	// 추가 로직 작성 가능
}

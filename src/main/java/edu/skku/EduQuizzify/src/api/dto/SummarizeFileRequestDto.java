package edu.skku.EduQuizzify.src.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SummarizeFileRequestDto {

	private String sourceType;  // 요청 유형 (summary, quiz 등)
	private String sectionId;   // 섹션 ID
	private String focusPrompt; // 추가 요청 내용 (선택적)

	// 필드를 모두 초기화하는 생성자
	public SummarizeFileRequestDto(String sourceType, String sectionId, String focusPrompt) {
		this.sourceType = sourceType;
		this.sectionId = sectionId;
		this.focusPrompt = focusPrompt;
	}
}

package edu.skku.EduQuizzify.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class SectionIdGenerator {

	// 사용자 ID와 업로드 시간을 기반으로 고유한 섹션 ID 생성
	public static String generateSectionId(String userId) {
		// 현재 시간을 yyyyMMddHHmmss 형식으로 포맷팅
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = now.format(formatter);

		// UUID 생성
		String uniquePart = UUID.randomUUID().toString();

		// 사용자 ID + 시간 + UUID를 결합하여 고유 섹션 ID 생성
		return userId + "_" + formattedDateTime + "_" + uniquePart;
	}
}

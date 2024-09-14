package edu.skku.EduQuizzify.src.domain.section;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Section {
	private String sectionId;
	private String processType;

	// 생성자
	public Section(String sectionId, String processType) {
		this.sectionId = sectionId;
		this.processType = processType;
	}
}

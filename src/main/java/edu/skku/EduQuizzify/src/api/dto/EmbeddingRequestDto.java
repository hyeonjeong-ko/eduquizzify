package edu.skku.EduQuizzify.src.api.dto;

import lombok.Getter;

@Getter
public class EmbeddingRequestDto {
	private String s3_url;
	private String section_id;

	public EmbeddingRequestDto(String s3_url, String section_id) {
		this.s3_url = s3_url;
		this.section_id = section_id;
	}

	public void setS3_url(String s3_url) {
		this.s3_url = s3_url;
	}

	public void setSection_id(String section_id) {
		this.section_id = section_id;
	}
}

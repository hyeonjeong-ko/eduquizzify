package edu.skku.EduQuizzify.src.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizResponseDto {

	@JsonProperty("quiz")
	private Quiz quiz;

	public static class Quiz {
		@JsonProperty("questions")
		private List<Question> questions;

		public List<Question> getQuestions() {
			return questions;
		}

		public void setQuestions(List<Question> questions) {
			this.questions = questions;
		}

		@Override
		public String toString() {
			return "Quiz{" +
				"questions=" + questions +
				'}';
		}
	}

	public static class Question {
		@JsonProperty("question")
		private String question;

		@JsonProperty("answers")
		private List<Answer> answers;

		@JsonProperty("explanation")
		private String explanation;

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public List<Answer> getAnswers() {
			return answers;
		}

		public void setAnswers(List<Answer> answers) {
			this.answers = answers;
		}

		public String getExplanation() {
			return explanation;
		}

		public void setExplanation(String explanation) {
			this.explanation = explanation;
		}

		@Override
		public String toString() {
			return "Question{" +
				"question='" + question + '\'' +
				", answers=" + answers +
				", explanation='" + explanation + '\'' +
				'}';
		}
	}

	public static class Answer {
		@JsonProperty("answer")
		private String answer;

		@JsonProperty("correct")
		private boolean correct;

		public String getAnswer() {
			return answer;
		}

		public void setAnswer(String answer) {
			this.answer = answer;
		}

		public boolean isCorrect() {
			return correct;
		}

		public void setCorrect(boolean correct) {
			this.correct = correct;
		}

		@Override
		public String toString() {
			return "Answer{" +
				"answer='" + answer + '\'' +
				", correct=" + correct +
				'}';
		}
	}

	// Getters and Setters for QuizResponseDto
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public String toString() {
		return "QuizResponseDto{" +
			"quiz=" + quiz +
			'}';
	}
}

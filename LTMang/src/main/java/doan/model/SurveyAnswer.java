package doan.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAnswer {
	private int id;
	private Reviews reviews;
	private SurveyQuestion question;
	private String answer;
	private Timestamp createAt;

	public SurveyAnswer(Reviews reviews, SurveyQuestion question, String answer) {
		super();
		this.reviews = reviews;
		this.question = question;
		this.answer = answer;
	}

}

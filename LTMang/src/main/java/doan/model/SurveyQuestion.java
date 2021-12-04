package doan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestion {
	private int id;
	private String question;
	private int object; // đối tượng khảo sát (2: khảo sát người cần giúp đỡ, 3: khảo sát người giúp đỡ)

	public SurveyQuestion(int id) {
		super();
		this.id = id;
	}

}

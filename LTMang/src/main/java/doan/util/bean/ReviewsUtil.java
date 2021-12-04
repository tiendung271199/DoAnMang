package doan.util.bean;

import java.sql.Timestamp;
import java.util.List;

import doan.model.Post;
import doan.model.SurveyAnswer;
import doan.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsUtil {
	private int id;
	private User userReviews;
	private User userDuocReviews;
	private Post post;
	private String other;
	private Timestamp createAt;
	List<SurveyAnswer> list;

}

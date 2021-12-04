package doan.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {
	private int id;
	private User userReviews;
	private User userDuocReviews;
	private Post post;
	private String other; // ý kiến khác trong khảo sát đánh giá
	private Timestamp createAt;

	public Reviews(User userReviews, User userDuocReviews, Post post, String other) {
		super();
		this.userReviews = userReviews;
		this.userDuocReviews = userDuocReviews;
		this.post = post;
		this.other = other;
	}

	public Reviews(int id) {
		super();
		this.id = id;
	}

}

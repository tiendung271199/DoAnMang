package doan.model;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	private int id;

	@NotEmpty
	private String title;

	@NotEmpty
	private String description;

	@NotEmpty
	private String detail;

	private int quantity; // số lượng nhà hảo tâm cần (1: Một nhà hảo tâm, 2: Nhiều nhà hảo tâm)
	private User user;
	private int views;
	private String picture;
	private int censored;
	private int status;
	private Timestamp createAt;
	private Timestamp updateAt;

	public Post(int id, String title, User user, String picture) {
		super();
		this.id = id;
		this.title = title;
		this.user = user;
		this.picture = picture;
	}

	public Post(int id, String title, String picture) {
		super();
		this.id = id;
		this.title = title;
		this.picture = picture;
	}

	public Post(int id) {
		super();
		this.id = id;
	}

}

package doan.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCharity {
	private int id;
	private User user;
	private Post post;
	private int confirm; // xác nhận từ bên cần giúp đỡ
	private int status; // trạng thái đăng ký (khi huỷ đăng ký sẽ thay status = 0)
	private Timestamp createAt;
	private Timestamp updateAt;

	public RegisterCharity(User user, Post post) {
		super();
		this.user = user;
		this.post = post;
	}

}

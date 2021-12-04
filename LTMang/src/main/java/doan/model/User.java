package doan.model;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private int id;

	@NotBlank
	private String username;

	private String password;

	@NotBlank
	private String fullname;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String phone;

	private Address address;
	private Role role;
	private int enabled;
	private Timestamp createAt;
	private Timestamp updateAt;

	public User(int id, String username, String fullname) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
	}

	public User(String username, String email, String phone) {
		super();
		this.username = username;
		this.email = email;
		this.phone = phone;
	}

	public User(int id) {
		super();
		this.id = id;
	}

	public User(int id, String username, String fullname, String email, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
	}

}

package doan.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	private int id;

	@NotBlank
	private String detail;

	private Province province;
	private District district;
	private Ward ward;

	public Address(int id) {
		super();
		this.id = id;
	}

}

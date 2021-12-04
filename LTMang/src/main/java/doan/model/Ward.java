package doan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ward {
	private int wardId;
	private String wardName;
	private int districtId;

	public Ward(int wardId, String wardName) {
		super();
		this.wardId = wardId;
		this.wardName = wardName;
	}

}

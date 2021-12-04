package doan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {
	private int districtId;
	private String districtName;
	private int provinceId;

	public District(int districtId, String districtName) {
		super();
		this.districtId = districtId;
		this.districtName = districtName;
	}

}

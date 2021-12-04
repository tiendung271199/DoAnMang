package doan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import doan.model.Address;
import doan.model.District;
import doan.model.Province;
import doan.model.Ward;

@Repository
public class AddressDAO extends AbstractDAO<Address> {

	@Override
	public int save(Address address) {
		String sql = "INSERT INTO address(provinceId,districtId,wardId,detail) VALUES (?,?,?,?)";
		return jdbcTemplate.update(sql, address.getProvince().getProvinceId(), address.getDistrict().getDistrictId(),
				address.getWard().getWardId(), address.getDetail());
	}

	@Override
	public int update(Address address) {
		String sql = "UPDATE address SET provinceId = ?, districtId = ?, wardId = ?, detail = ? WHERE id = ?";
		return jdbcTemplate.update(sql, address.getProvince().getProvinceId(), address.getDistrict().getDistrictId(),
				address.getWard().getWardId(), address.getDetail(), address.getId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM address WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	// Lấy địa chỉ mới nhất (thêm user)
	public Address getNewestAddress() {
		try {
			String sql = "SELECT * FROM address a INNER JOIN location_province p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward w ON a.wardId = w.wardId ORDER BY id DESC LIMIT 1";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Address>() {

				@Override
				public Address extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Address(rs.getInt("id"), rs.getString("detail"),
								new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
								new District(rs.getInt("d.districtId"), rs.getString("districtName")),
								new Ward(rs.getInt("w.wardId"), rs.getString("wardName")));
					}
					return null;
				}
			});
		} catch (Exception e) {
			System.out.println("Newest Address: No data");
		}
		return null;
	}

}

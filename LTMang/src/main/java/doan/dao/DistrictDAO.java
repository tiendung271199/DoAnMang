package doan.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import doan.model.District;

@Repository
public class DistrictDAO extends AbstractDAO<District> {

	@Override
	public List<District> getAll() {
		String sql = "SELECT * FROM location_district";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(District.class));
	}
	
	@Override
	public District findById(int id) {
		try {
			String sql = "SELECT * FROM location_district WHERE districtId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(District.class), id);
		} catch (Exception e) {
			System.out.println("FindById District: No data");
		}
		return null;
	}
	
	public List<District> findByProvinceId(int provinceId) {
		String sql = "SELECT * FROM location_district WHERE provinceId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(District.class), provinceId);
	}
	
}

package doan.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import doan.model.Ward;

@Repository
public class WardDAO extends AbstractDAO<Ward> {

	@Override
	public List<Ward> getAll() {
		String sql = "SELECT * FROM location_ward";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ward.class));
	}

	@Override
	public Ward findById(int id) {
		try {
			String sql = "SELECT * FROM location_ward WHERE wardId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Ward.class), id);
		} catch (Exception e) {
			System.out.println("FindById Ward: No data");
		}
		return null;
	}

	public List<Ward> findByDistrictId(int districtId) {
		String sql = "SELECT * FROM location_ward WHERE districtId = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ward.class), districtId);
	}
}

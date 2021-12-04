package doan.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import doan.model.Province;

@Repository
public class ProvinceDAO extends AbstractDAO<Province> {

	@Override
	public List<Province> getAll() {
		String sql = "SELECT * FROM location_province";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Province.class));
	}

	@Override
	public Province findById(int id) {
		try {
			String sql = "SELECT * FROM location_province WHERE provinceId = ?";
			return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Province.class), id);
		} catch (Exception e) {
			System.out.println("FindById Province: No data");
		}
		return null;
	}

}

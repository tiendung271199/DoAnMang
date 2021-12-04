package doan.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import doan.constant.DatabaseConstant;
import doan.model.Role;

@Repository
public class RoleDAO extends AbstractDAO<Role> {

	@Override
	public List<Role> getAll() {
		String sql = "SELECT * FROM roles";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class));
	}

	public List<Role> getNonAdminRole() {
		String sql = "SELECT * FROM roles WHERE id != ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class), DatabaseConstant.ROLE_ADMIN);
	}

	public List<Role> getUserRole() {
		String sql = "SELECT * FROM roles WHERE id = ? OR id = ?";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class), DatabaseConstant.ROLE_USER_1,
				DatabaseConstant.ROLE_USER_2);
	}

}

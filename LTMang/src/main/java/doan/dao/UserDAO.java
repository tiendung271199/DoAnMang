package doan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import doan.constant.GlobalConstant;
import doan.model.Address;
import doan.model.District;
import doan.model.Province;
import doan.model.Role;
import doan.model.User;
import doan.model.Ward;

@Repository
public class UserDAO extends AbstractDAO<User> {

	@Override
	public List<User> getAll() {
		String sql = "SELECT * FROM users u INNER JOIN address a ON u.addressId = a.id"
				+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward w ON a.wardId = w.wardId"
				+ " INNER JOIN roles r ON u.roleId = r.id ORDER BY u.id DESC";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			List<User> list = new ArrayList<User>();

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("password"),
							rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
							new Address(rs.getInt("a.id"), rs.getString("detail"),
									new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
									new District(rs.getInt("d.districtId"), rs.getString("districtName")),
									new Ward(rs.getInt("w.wardId"), rs.getString("wardName"))),
							new Role(rs.getInt("r.id"), rs.getString("name"), rs.getString("description")),
							rs.getInt("enabled"), rs.getTimestamp("u.createAt"), rs.getTimestamp("u.updateAt")));
				}
				return list;
			}
		});
	}

	@Override
	public List<User> getList(int offset, int rowCount) {
		String sql = "SELECT * FROM users u INNER JOIN address a ON u.addressId = a.id"
				+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward w ON a.wardId = w.wardId"
				+ " INNER JOIN roles r ON u.roleId = r.id ORDER BY u.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			List<User> list = new ArrayList<User>();

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("password"),
							rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
							new Address(rs.getInt("a.id"), rs.getString("detail"),
									new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
									new District(rs.getInt("d.districtId"), rs.getString("districtName")),
									new Ward(rs.getInt("w.wardId"), rs.getString("wardName"))),
							new Role(rs.getInt("r.id"), rs.getString("name"), rs.getString("description")),
							rs.getInt("enabled"), rs.getTimestamp("u.createAt"), rs.getTimestamp("u.updateAt")));
				}
				return list;
			}
		}, offset, rowCount);
	}

	@Override
	public int totalRow() {
		String sql = "SELECT COUNT(*) FROM users u INNER JOIN address a ON u.addressId = a.id"
				+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward w ON a.wardId = w.wardId INNER JOIN roles r ON u.roleId = r.id";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public User findById(int id) {
		try {
			String sql = "SELECT * FROM users u INNER JOIN address a ON u.addressId = a.id"
					+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward w ON a.wardId = w.wardId"
					+ " INNER JOIN roles r ON u.roleId = r.id WHERE u.id = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("password"),
								rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
								new Address(rs.getInt("a.id"), rs.getString("detail"),
										new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
										new District(rs.getInt("d.districtId"), rs.getString("districtName")),
										new Ward(rs.getInt("w.wardId"), rs.getString("wardName"))),
								new Role(rs.getInt("r.id"), rs.getString("name"), rs.getString("description")),
								rs.getInt("enabled"), rs.getTimestamp("u.createAt"), rs.getTimestamp("u.updateAt"));
					}
					return null;
				}

			}, id);
		} catch (Exception e) {
			System.out.println("User By Id " + id + ": No data");
		}
		return null;
	}

	@Override
	public int save(User user) {
		String sql = "INSERT INTO users(username,password,fullname,email,phone,addressId,roleId) VALUES (?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getFullname(), user.getEmail(),
				user.getPhone(), user.getAddress().getId(), user.getRole().getId());
	}

	@Override
	public int update(User user) {
		String sql = "UPDATE users SET username = ?, fullname = ?, email = ?, phone = ?, roleId = ? WHERE id = ?";
		return jdbcTemplate.update(sql, user.getUsername(), user.getFullname(), user.getEmail(), user.getPhone(),
				user.getRole().getId(), user.getId());
	}

	public int updatePassword(User user) {
		String sql = "UPDATE users SET password = ? WHERE id = ?";
		return jdbcTemplate.update(sql, user.getPassword(), user.getId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	// check user trùng
	public User findUserDuplicate(User user, User oldUser, String properties) {
		try {
			String sql = "SELECT * FROM users u INNER JOIN address a ON u.addressId = a.id"
					+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward w ON a.wardId = w.wardId INNER JOIN roles r ON u.roleId = r.id";
			Object[] arr = new Object[2];
			if (properties.equals("username")) {
				sql += " WHERE BINARY username = ? AND BINARY username != ?";
				arr[0] = user.getUsername();
				arr[1] = oldUser.getUsername();
			}
			if (properties.equals("email")) {
				sql += " WHERE BINARY email = ? AND BINARY email != ?";
				arr[0] = user.getEmail();
				arr[1] = oldUser.getEmail();
			}
			if (properties.equals("phone")) {
				sql += " WHERE phone = ? AND phone != ?";
				arr[0] = user.getPhone();
				arr[1] = oldUser.getPhone();
			}
			return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("password"),
								rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
								new Address(rs.getInt("a.id"), rs.getString("detail"),
										new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
										new District(rs.getInt("d.districtId"), rs.getString("districtName")),
										new Ward(rs.getInt("w.wardId"), rs.getString("wardName"))),
								new Role(rs.getInt("r.id"), rs.getString("name"), rs.getString("description")),
								rs.getInt("enabled"), rs.getTimestamp("u.createAt"), rs.getTimestamp("u.updateAt"));
					}
					return null;
				}

			}, arr);
		} catch (Exception e) {
			System.out.println("User duplicate: No data");
		}
		return null;
	}

	// login
	public User findByUsername(String username) {
		try {
			String sql = "SELECT * FROM users u INNER JOIN address a ON u.addressId = a.id"
					+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
					+ " INNER JOIN location_district d ON a.districtId = d.districtId"
					+ " INNER JOIN location_ward w ON a.wardId = w.wardId INNER JOIN roles r ON u.roleId = r.id"
					+ " WHERE BINARY username = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<User>() {

				@Override
				public User extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("password"),
								rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
								new Address(rs.getInt("a.id"), rs.getString("detail"),
										new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
										new District(rs.getInt("d.districtId"), rs.getString("districtName")),
										new Ward(rs.getInt("w.wardId"), rs.getString("wardName"))),
								new Role(rs.getInt("r.id"), rs.getString("name"), rs.getString("description")),
								rs.getInt("enabled"), rs.getTimestamp("u.createAt"), rs.getTimestamp("u.updateAt"));
					}
					return null;
				}

			}, username);
		} catch (Exception e) {
			System.out.println("Find By Username '" + username + "': No data");
		}
		return null;
	}

	// search
	public List<User> search(String username, int roleId, int offset, int rowCount) {
		String sql = "SELECT * FROM users u INNER JOIN address a ON u.addressId = a.id"
				+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward w ON a.wardId = w.wardId"
				+ " INNER JOIN roles r ON u.roleId = r.id WHERE 1";
		List<Object> list = new ArrayList<Object>();
		if (!username.equals(GlobalConstant.EMPTY)) {
			sql += " AND u.username LIKE ?";
			list.add("%" + username + "%");
		}
		if (roleId != 0) {
			sql += " AND u.roleId = ?";
			list.add(roleId);
		}
		sql += " ORDER BY u.id DESC LIMIT ?,?";
		list.add(offset);
		list.add(rowCount);
		Object[] arrObj = list.stream().toArray(Object[]::new);
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<User>>() {
			List<User> list = new ArrayList<User>();

			@Override
			public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("password"),
							rs.getString("fullname"), rs.getString("email"), rs.getString("phone"),
							new Address(rs.getInt("a.id"), rs.getString("detail"),
									new Province(rs.getInt("p.provinceId"), rs.getString("provinceName")),
									new District(rs.getInt("d.districtId"), rs.getString("districtName")),
									new Ward(rs.getInt("w.wardId"), rs.getString("wardName"))),
							new Role(rs.getInt("r.id"), rs.getString("name"), rs.getString("description")),
							rs.getInt("enabled"), rs.getTimestamp("u.createAt"), rs.getTimestamp("u.updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

	public int totalRowSearch(String username, int roleId) {
		String sql = "SELECT COUNT(*) FROM users u INNER JOIN address a ON u.addressId = a.id"
				+ " INNER JOIN location_province p ON a.provinceId = p.provinceId"
				+ " INNER JOIN location_district d ON a.districtId = d.districtId"
				+ " INNER JOIN location_ward w ON a.wardId = w.wardId"
				+ " INNER JOIN roles r ON u.roleId = r.id WHERE 1";
		List<Object> list = new ArrayList<Object>();
		if (!username.equals(GlobalConstant.EMPTY)) {
			sql += " AND u.username LIKE ?";
			list.add("%" + username + "%");
		}
		if (roleId != 0) {
			sql += " AND u.roleId = ?";
			list.add(roleId);
		}
		Object[] arrObj = list.stream().toArray(Object[]::new);
		return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
	}

}

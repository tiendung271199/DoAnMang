package doan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import doan.model.Post;
import doan.model.RegisterCharity;
import doan.model.User;

@Repository
public class RegisterCharityDAO extends AbstractDAO<RegisterCharity> {

	@Override
	public List<RegisterCharity> getAll() {
		String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id WHERE rc.status = 1 ORDER BY rc.id DESC";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<RegisterCharity>>() {
			List<RegisterCharity> list = new ArrayList<RegisterCharity>();

			@Override
			public List<RegisterCharity> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new RegisterCharity(rs.getInt("rc.id"),
							new User(rs.getInt("u.id"), rs.getString("u.username"), rs.getString("u.fullname")),
							new Post(rs.getInt("p.id"), rs.getString("title"),
									new User(rs.getInt("up.id"), rs.getString("up.username"),
											rs.getString("up.fullname")),
									rs.getString("picture")),
							rs.getInt("confirm"), rs.getInt("rc.status"), rs.getTimestamp("rc.createAt"),
							rs.getTimestamp("rc.updateAt")));
				}
				return list;
			}
		});
	}

	@Override
	public List<RegisterCharity> getList(int offset, int rowCount) {
		String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id WHERE rc.status = 1 ORDER BY rc.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<RegisterCharity>>() {
			List<RegisterCharity> list = new ArrayList<RegisterCharity>();

			@Override
			public List<RegisterCharity> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new RegisterCharity(rs.getInt("rc.id"),
							new User(rs.getInt("u.id"), rs.getString("u.username"), rs.getString("u.fullname")),
							new Post(rs.getInt("p.id"), rs.getString("title"),
									new User(rs.getInt("up.id"), rs.getString("up.username"),
											rs.getString("up.fullname")),
									rs.getString("picture")),
							rs.getInt("confirm"), rs.getInt("rc.status"), rs.getTimestamp("rc.createAt"),
							rs.getTimestamp("rc.updateAt")));
				}
				return list;
			}
		}, offset, rowCount);
	}

	@Override
	public int totalRow() {
		String sql = "SELECT COUNT(*) FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id WHERE rc.status = 1";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public RegisterCharity findById(int id) {
		try {
			String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
					+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
					+ " WHERE rc.id = ? AND rc.status = 1";
			return jdbcTemplate.query(sql, new ResultSetExtractor<RegisterCharity>() {

				@Override
				public RegisterCharity extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new RegisterCharity(rs.getInt("rc.id"),
								new User(rs.getInt("u.id"), rs.getString("u.username"), rs.getString("u.fullname")),
								new Post(rs.getInt("p.id"), rs.getString("title"),
										new User(rs.getInt("up.id"), rs.getString("up.username"),
												rs.getString("up.fullname")),
										rs.getString("picture")),
								rs.getInt("confirm"), rs.getInt("rc.status"), rs.getTimestamp("rc.createAt"),
								rs.getTimestamp("rc.updateAt"));
					}
					return null;
				}
			}, id);
		} catch (Exception e) {
			System.out.println("RegisterCharity by ID " + id + ": No data");
		}
		return null;
	}

	@Override
	public int save(RegisterCharity registerCharity) {
		String sql = "INSERT INTO register_charity(userId,postId) VALUES (?,?)";
		return jdbcTemplate.update(sql, registerCharity.getUser().getId(), registerCharity.getPost().getId());
	}

	public int updateConfirm(RegisterCharity registerCharity) {
		String sql = "UPDATE register_charity SET confirm = ? WHERE id = ?";
		return jdbcTemplate.update(sql, registerCharity.getConfirm(), registerCharity.getId());
	}

	public int updateStatus(RegisterCharity registerCharity) {
		String sql = "UPDATE register_charity SET status = ? WHERE id = ?";
		return jdbcTemplate.update(sql, registerCharity.getStatus(), registerCharity.getId());
	}

	public List<RegisterCharity> getListByPostId(int postId) {
		String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
				+ " WHERE rc.postId = ? AND rc.status = 1 ORDER BY rc.id DESC";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<RegisterCharity>>() {
			List<RegisterCharity> list = new ArrayList<RegisterCharity>();

			@Override
			public List<RegisterCharity> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new RegisterCharity(rs.getInt("rc.id"),
							new User(rs.getInt("u.id"), rs.getString("u.username"), rs.getString("u.fullname"),
									rs.getString("u.email"), rs.getString("u.phone")),
							new Post(rs.getInt("p.id"), rs.getString("title"),
									new User(rs.getInt("up.id"), rs.getString("up.username"),
											rs.getString("up.fullname")),
									rs.getString("picture")),
							rs.getInt("confirm"), rs.getInt("rc.status"), rs.getTimestamp("rc.createAt"),
							rs.getTimestamp("rc.updateAt")));
				}
				return list;
			}
		}, postId);
	}

	public RegisterCharity findByPostIdAndUserId(int postId, int userId) {
		try {
			String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
					+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
					+ " WHERE rc.postId = ? AND rc.userId = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<RegisterCharity>() {

				@Override
				public RegisterCharity extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new RegisterCharity(rs.getInt("rc.id"),
								new User(rs.getInt("u.id"), rs.getString("u.username"), rs.getString("u.fullname")),
								new Post(rs.getInt("p.id"), rs.getString("title"),
										new User(rs.getInt("up.id"), rs.getString("up.username"),
												rs.getString("up.fullname")),
										rs.getString("picture")),
								rs.getInt("confirm"), rs.getInt("rc.status"), rs.getTimestamp("rc.createAt"),
								rs.getTimestamp("rc.updateAt"));
					}
					return null;
				}
			}, postId, userId);
		} catch (Exception e) {
			System.out.println("RegisterCharity by post ID " + postId + " and user ID " + userId + ": No data");
		}
		return null;
	}

	// đếm post có bao nhiêu lượt đăng ký giúp đỡ => limit 20 mỗi post
	public int totalRowByPostId(int postId) {
		String sql = "SELECT COUNT(*) FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
				+ " WHERE rc.postId = ? AND rc.status = 1";
		return jdbcTemplate.queryForObject(sql, Integer.class, postId);
	}

	// đếm post có bao nhiêu lượt xác nhận giúp đỡ => check TH bài viết chỉ cần 1
	// nhà hảo tâm
	public int totalRowConfirmByPostId(int postId) {
		String sql = "SELECT COUNT(*) FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
				+ " WHERE rc.postId = ? AND rc.confirm = 1";
		return jdbcTemplate.queryForObject(sql, Integer.class, postId);
	}

	public List<RegisterCharity> getListByUserId(int userId, int offset, int rowCount) {
		String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
				+ " WHERE rc.userId = ? AND rc.status = 1 ORDER BY rc.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<RegisterCharity>>() {
			List<RegisterCharity> list = new ArrayList<RegisterCharity>();

			@Override
			public List<RegisterCharity> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new RegisterCharity(rs.getInt("rc.id"),
							new User(rs.getInt("u.id"), rs.getString("u.username"), rs.getString("u.fullname"),
									rs.getString("u.email"), rs.getString("u.phone")),
							new Post(rs.getInt("p.id"), rs.getString("title"),
									new User(rs.getInt("up.id"), rs.getString("up.username"),
											rs.getString("up.fullname")),
									rs.getString("picture")),
							rs.getInt("confirm"), rs.getInt("rc.status"), rs.getTimestamp("rc.createAt"),
							rs.getTimestamp("rc.updateAt")));
				}
				return list;
			}
		}, userId, offset, rowCount);
	}

	public int totalRowByUserId(int userId) {
		String sql = "SELECT COUNT(*) FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
				+ " WHERE rc.userId = ? AND rc.status = 1";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

	public int totalRowConfirmByUserId(int userId) {
		String sql = "SELECT COUNT(*) FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
				+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
				+ " WHERE rc.userId = ? AND rc.confirm = 1";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

	public int delByUserId(int userId) {
		String sql = "DELETE FROM register_charity WHERE userId = ?";
		return jdbcTemplate.update(sql, userId);
	}

	public int delByPostId(int postId) {
		String sql = "DELETE FROM register_charity WHERE postId = ?";
		return jdbcTemplate.update(sql, postId);
	}

	// check 2 user có confirm từ thiện không => có => đánh giá
	public boolean checkCharity(int userId, int postId) {
		try {
			String sql = "SELECT * FROM register_charity rc INNER JOIN users u ON rc.userId = u.id"
					+ " INNER JOIN posts p ON rc.postId = p.id INNER JOIN users up ON p.userId = up.id"
					+ " WHERE rc.userId = ? AND rc.postId = ? AND rc.confirm = 1";
			RegisterCharity obj = jdbcTemplate.query(sql, new ResultSetExtractor<RegisterCharity>() {

				@Override
				public RegisterCharity extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new RegisterCharity();
					}
					return null;
				}
			}, userId, postId);
			if (obj != null) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

}

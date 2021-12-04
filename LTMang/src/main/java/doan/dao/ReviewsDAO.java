package doan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import doan.model.Post;
import doan.model.Reviews;
import doan.model.User;

@Repository
public class ReviewsDAO extends AbstractDAO<Reviews> {

	@Override
	public List<Reviews> getList(int offset, int rowCount) {
		String sql = "SELECT * FROM reviews r INNER JOIN users ur ON r.userReviews = ur.id"
				+ " INNER JOIN users udr ON r.userDuocReviews = udr.id"
				+ " INNER JOIN posts p ON r.postId = p.id ORDER BY r.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Reviews>>() {
			List<Reviews> list = new ArrayList<Reviews>();

			@Override
			public List<Reviews> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Reviews(rs.getInt("r.id"),
							new User(rs.getInt("ur.id"), rs.getString("ur.username"), rs.getString("ur.fullname")),
							new User(rs.getInt("udr.id"), rs.getString("udr.username"), rs.getString("udr.fullname")),
							new Post(rs.getInt("p.id"), rs.getString("p.title"), rs.getString("p.picture")),
							rs.getString("r.other"), rs.getTimestamp("r.createAt")));
				}
				return list;
			}
		}, offset, rowCount);
	}

	@Override
	public int totalRow() {
		String sql = "SELECT COUNT(*) FROM reviews r INNER JOIN users ur ON r.userReviews = ur.id"
				+ " INNER JOIN users udr ON r.userDuocReviews = udr.id INNER JOIN posts p ON r.postId = p.id";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public Reviews findById(int id) {
		try {
			String sql = "SELECT * FROM reviews r INNER JOIN users ur ON r.userReviews = ur.id"
					+ " INNER JOIN users udr ON r.userDuocReviews = udr.id"
					+ " INNER JOIN posts p ON r.postId = p.id WHERE r.id = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Reviews>() {

				@Override
				public Reviews extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Reviews(rs.getInt("r.id"),
								new User(rs.getInt("ur.id"), rs.getString("ur.username"), rs.getString("ur.fullname")),
								new User(rs.getInt("udr.id"), rs.getString("udr.username"),
										rs.getString("udr.fullname")),
								new Post(rs.getInt("p.id"), rs.getString("p.title"), rs.getString("p.picture")),
								rs.getString("r.other"), rs.getTimestamp("r.createAt"));
					}
					return null;
				}
			}, id);
		} catch (Exception e) {
			System.out.println("Reviews by ID " + id + ": No data");
		}
		return null;
	}

	@Override
	public int save(Reviews reviews) {
		String sql = "INSERT INTO reviews(userReviews,userDuocReviews,postId,other) VALUES (?,?,?,?)";
		return jdbcTemplate.update(sql, reviews.getUserReviews().getId(), reviews.getUserDuocReviews().getId(),
				reviews.getPost().getId(), reviews.getOther());
	}

	// lấy record mới nhất
	public Reviews getNewReviews() {
		String sql = "SELECT * FROM reviews r INNER JOIN users ur ON r.userReviews = ur.id"
				+ " INNER JOIN users udr ON r.userDuocReviews = udr.id"
				+ " INNER JOIN posts p ON r.postId = p.id ORDER BY r.id DESC LIMIT 1";
		return jdbcTemplate.query(sql, new ResultSetExtractor<Reviews>() {

			@Override
			public Reviews extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return new Reviews(rs.getInt("r.id"),
							new User(rs.getInt("ur.id"), rs.getString("ur.username"), rs.getString("ur.fullname")),
							new User(rs.getInt("udr.id"), rs.getString("udr.username"), rs.getString("udr.fullname")),
							new Post(rs.getInt("p.id"), rs.getString("p.title"), rs.getString("p.picture")),
							rs.getString("r.other"), rs.getTimestamp("r.createAt"));
				}
				return null;
			}
		});
	}

	// hiển thị đánh giá người dùng
	public List<Reviews> findByUserDuocReviews(int userId, int offset, int rowCount) {
		String sql = "SELECT * FROM reviews r INNER JOIN users ur ON r.userReviews = ur.id"
				+ " INNER JOIN users udr ON r.userDuocReviews = udr.id"
				+ " INNER JOIN posts p ON r.postId = p.id WHERE r.userDuocReviews = ? ORDER BY r.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Reviews>>() {
			List<Reviews> list = new ArrayList<Reviews>();

			@Override
			public List<Reviews> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Reviews(rs.getInt("r.id"),
							new User(rs.getInt("ur.id"), rs.getString("ur.username"), rs.getString("ur.fullname")),
							new User(rs.getInt("udr.id"), rs.getString("udr.username"), rs.getString("udr.fullname")),
							new Post(rs.getInt("p.id"), rs.getString("p.title"), rs.getString("p.picture")),
							rs.getString("r.other"), rs.getTimestamp("r.createAt")));
				}
				return list;
			}
		}, userId, offset, rowCount);
	}

	public int totalRowByUserDuocReviews(int userId) {
		String sql = "SELECT COUNT(*) FROM reviews r INNER JOIN users ur ON r.userReviews = ur.id"
				+ " INNER JOIN users udr ON r.userDuocReviews = udr.id"
				+ " INNER JOIN posts p ON r.postId = p.id WHERE r.userDuocReviews = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

}

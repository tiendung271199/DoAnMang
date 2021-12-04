package doan.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import doan.constant.DatabaseConstant;
import doan.constant.GlobalConstant;
import doan.model.Post;
import doan.model.User;

@Repository
public class PostDAO extends AbstractDAO<Post> {

	@Override
	public List<Post> getAll() {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id ORDER BY p.id DESC";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		});
	}

	@Override
	public List<Post> getList(int offset, int rowCount) {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id ORDER BY p.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, offset, rowCount);
	}

	@Override
	public int totalRow() {
		String sql = "SELECT COUNT(*) FROM posts p INNER JOIN users u ON p.userId = u.id";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public Post findById(int id) {
		try {
			String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.id = ?";
			return jdbcTemplate.query(sql, new ResultSetExtractor<Post>() {

				@Override
				public Post extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
								rs.getString("detail"), rs.getInt("quantity"),
								new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
								rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
								rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt"));
					}
					return null;
				}
			}, id);
		} catch (Exception e) {
			System.out.println("Post By Id " + id + ": No data");
		}
		return null;
	}

	@Override
	public int save(Post post) {
		String sql = "INSERT INTO posts(title,description,detail,quantity,userId,picture) VALUES (?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, post.getTitle(), post.getDescription(), post.getDetail(), post.getQuantity(),
				post.getUser().getId(), post.getPicture());
	}

	@Override
	public int update(Post post) {
		String sql = "UPDATE posts SET title = ?, description = ?, detail = ?, quantity = ?, picture = ? WHERE id = ?";
		return jdbcTemplate.update(sql, post.getTitle(), post.getDescription(), post.getDetail(), post.getQuantity(),
				post.getPicture(), post.getId());
	}

	public int updateViews(int id) {
		String sql = "UPDATE posts SET views = views + 1 WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	public int updateCensored(Post post) {
		String sql = "UPDATE posts SET censored = ? WHERE id = ?";
		return jdbcTemplate.update(sql, post.getCensored(), post.getId());
	}

	public int updateStatus(Post post) {
		String sql = "UPDATE posts SET status = ? WHERE id = ?";
		return jdbcTemplate.update(sql, post.getStatus(), post.getId());
	}

	@Override
	public int del(int id) {
		String sql = "DELETE FROM posts WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

	// hiển thị public => lấy post đã được kiểm duyệt
	public List<Post> getListByCensored(int offset, int rowCount) {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE censored = ? ORDER BY p.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, DatabaseConstant.POST_CENSORED, offset, rowCount);
	}

	public int totalRowByCensored() {
		String sql = "SELECT COUNT(*) FROM posts p INNER JOIN users u ON p.userId = u.id WHERE censored = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, DatabaseConstant.POST_CENSORED);
	}

	// lấy bài viết khác (random trong vòng 1 tuần)
	public List<Post> getListByDate(int id, String datePresent, String datePast) {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id"
				+ " WHERE (p.createAt >= ? AND p.createAt <= ?) AND p.id != ? AND p.censored = ? ORDER BY RAND() LIMIT 4";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, datePast, datePresent, id, DatabaseConstant.POST_CENSORED);
	}

	// lấy danh sách bài viết mới nhất
	public List<Post> getListNew() {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.censored = ? ORDER BY p.id DESC LIMIT 5";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, DatabaseConstant.POST_CENSORED);
	}

	// lấy danh sách bài viết nổi bật (theo lượt đọc)
	public List<Post> getListHighlight() {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.censored = ? ORDER BY p.views DESC LIMIT 5";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, DatabaseConstant.POST_CENSORED);
	}

	// user quản lý bài viết
	public List<Post> findByUserId(int userId, int offset, int rowCount) {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.userId = ? ORDER BY p.id DESC LIMIT ?,?";
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, userId, offset, rowCount);
	}

	public int totalRowByUserId(int userId) {
		String sql = "SELECT COUNT(*) FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.userId = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId);
	}

	// hiển thị trong trang info
	public int totalRowByUserIdAndCensored(int userId) {
		String sql = "SELECT COUNT(*) FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.userId = ? AND censored = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, userId, DatabaseConstant.POST_CENSORED);
	}

	// tìm kiếm
	public List<Post> search(String title, String username, int censored, int offset, int rowCount) {
		String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE 1";
		List<Object> list = new ArrayList<Object>();
		if (!title.equals(GlobalConstant.EMPTY)) {
			sql += " AND title LIKE ?";
			list.add("%" + title + "%");
		}
		if (!username.equals(GlobalConstant.EMPTY)) {
			sql += " AND u.username LIKE ?";
			list.add("%" + username + "%");
		}
		if (censored == 0 || censored == 1) {
			sql += " AND censored = ?";
			list.add(censored);
		}
		sql += " ORDER BY p.id DESC LIMIT ?,?";
		list.add(offset);
		list.add(rowCount);
		Object[] arrObj = list.stream().toArray(Object[]::new);
		return jdbcTemplate.query(sql, new ResultSetExtractor<List<Post>>() {
			List<Post> list = new ArrayList<Post>();

			@Override
			public List<Post> extractData(ResultSet rs) throws SQLException, DataAccessException {
				while (rs.next()) {
					list.add(new Post(rs.getInt("p.id"), rs.getString("title"), rs.getString("description"),
							rs.getString("detail"), rs.getInt("quantity"),
							new User(rs.getInt("u.id"), rs.getString("username"), rs.getString("fullname")),
							rs.getInt("views"), rs.getString("picture"), rs.getInt("censored"), rs.getInt("status"),
							rs.getTimestamp("p.createAt"), rs.getTimestamp("p.updateAt")));
				}
				return list;
			}
		}, arrObj);
	}

	public int totalRowSearch(String title, String username, int censored) {
		String sql = "SELECT COUNT(*) FROM posts p INNER JOIN users u ON p.userId = u.id WHERE 1";
		List<Object> list = new ArrayList<Object>();
		if (!title.equals(GlobalConstant.EMPTY)) {
			sql += " AND title LIKE ?";
			list.add("%" + title + "%");
		}
		if (!username.equals(GlobalConstant.EMPTY)) {
			sql += " AND u.username LIKE ?";
			list.add("%" + username + "%");
		}
		if (censored == 0 || censored == 1) {
			sql += " AND censored = ?";
			list.add(censored);
		}
		Object[] arrObj = list.stream().toArray(Object[]::new);
		return jdbcTemplate.queryForObject(sql, Integer.class, arrObj);
	}

	// check bài viết có được đăng bởi user hay không
	public boolean checkPostByUser(int userId, int postId) {
		try {
			String sql = "SELECT * FROM posts p INNER JOIN users u ON p.userId = u.id WHERE p.id = ? AND p.userId = ?";
			Post post = jdbcTemplate.query(sql, new ResultSetExtractor<Post>() {

				@Override
				public Post extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						return new Post();
					}
					return null;
				}
			}, postId, userId);
			if (post != null) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

}

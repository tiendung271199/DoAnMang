package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.PostDAO;
import doan.model.Post;

@Service
public class PostService implements ICRUDService<Post> {

	@Autowired
	private PostDAO postDAO;

	@Override
	public List<Post> getAll() {
		return postDAO.getAll();
	}

	@Override
	public int save(Post post) {
		return postDAO.save(post);
	}

	@Override
	public int update(Post post) {
		return postDAO.update(post);
	}

	public int updateViews(int id) {
		return postDAO.updateViews(id);
	}

	public int updateCensored(Post post) {
		return postDAO.updateCensored(post);
	}

	public int updateStatus(Post post) {
		return postDAO.updateStatus(post);
	}

	@Override
	public int del(int id) {
		return postDAO.del(id);
	}

	@Override
	public Post findById(int id) {
		return postDAO.findById(id);
	}

	@Override
	public List<Post> getList(int offset, int rowCount) {
		return postDAO.getList(offset, rowCount);
	}

	@Override
	public int totalRow() {
		return postDAO.totalRow();
	}

	public List<Post> getListByCensored(int offset, int rowCount) {
		return postDAO.getListByCensored(offset, rowCount);
	}

	public int totalRowByCensored() {
		return postDAO.totalRowByCensored();
	}

	public List<Post> getListByDate(int id, String datePresent, String datePast) {
		return postDAO.getListByDate(id, datePresent, datePast);
	}

	public List<Post> getListNew() {
		return postDAO.getListNew();
	}

	public List<Post> getListHighlight() {
		return postDAO.getListHighlight();
	}

	public List<Post> findByUserId(int userId, int offset, int rowCount) {
		return postDAO.findByUserId(userId, offset, rowCount);
	}

	public int totalRowByUserId(int userId) {
		return postDAO.totalRowByUserId(userId);
	}

	public int totalRowByUserIdAndCensored(int userId) {
		return postDAO.totalRowByUserIdAndCensored(userId);
	}

	public List<Post> search(String title, String username, int censored, int offset, int rowCount) {
		return postDAO.search(title, username, censored, offset, rowCount);
	}

	public int totalRowSearch(String title, String username, int censored) {
		return postDAO.totalRowSearch(title, username, censored);
	}

	public boolean checkPostByUser(int userId, int postId) {
		return postDAO.checkPostByUser(userId, postId);
	}

}

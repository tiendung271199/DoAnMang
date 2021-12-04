package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.ReviewsDAO;
import doan.model.Reviews;

@Service
public class ReviewsService implements ICRUDService<Reviews> {

	@Autowired
	private ReviewsDAO reviewsDAO;

	@Override
	public List<Reviews> getAll() {
		return null;
	}

	@Override
	public int save(Reviews reviews) {
		return reviewsDAO.save(reviews);
	}

	@Override
	public int update(Reviews reviews) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public Reviews findById(int id) {
		return reviewsDAO.findById(id);
	}

	@Override
	public List<Reviews> getList(int offset, int rowCount) {
		return reviewsDAO.getList(offset, rowCount);
	}

	@Override
	public int totalRow() {
		return reviewsDAO.totalRow();
	}

	public Reviews getNewReviews() {
		return reviewsDAO.getNewReviews();
	}

	public List<Reviews> findByUserDuocReviews(int userId, int offset, int rowCount) {
		return reviewsDAO.findByUserDuocReviews(userId, offset, rowCount);
	}

	public int totalRowByUserDuocReviews(int userId) {
		return reviewsDAO.totalRowByUserDuocReviews(userId);
	}

}

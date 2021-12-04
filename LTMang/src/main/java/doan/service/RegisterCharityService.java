package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.RegisterCharityDAO;
import doan.model.RegisterCharity;

@Service
public class RegisterCharityService implements ICRUDService<RegisterCharity> {

	@Autowired
	private RegisterCharityDAO registerCharityDAO;

	@Override
	public List<RegisterCharity> getAll() {
		return registerCharityDAO.getAll();
	}

	@Override
	public int save(RegisterCharity registerCharity) {
		return registerCharityDAO.save(registerCharity);
	}

	@Override
	public int update(RegisterCharity t) {
		return 0;
	}

	public int updateConfirm(RegisterCharity registerCharity) {
		return registerCharityDAO.updateConfirm(registerCharity);
	}

	public int updateStatus(RegisterCharity registerCharity) {
		return registerCharityDAO.updateStatus(registerCharity);
	}

	@Override
	public int del(int id) {
		return 0;
	}

	public int delByUserId(int userId) {
		return registerCharityDAO.delByUserId(userId);
	}

	public int delByPostId(int postId) {
		return registerCharityDAO.delByPostId(postId);
	}

	@Override
	public RegisterCharity findById(int id) {
		return registerCharityDAO.findById(id);
	}

	@Override
	public List<RegisterCharity> getList(int offset, int rowCount) {
		return registerCharityDAO.getList(offset, rowCount);
	}

	@Override
	public int totalRow() {
		return registerCharityDAO.totalRow();
	}

	public List<RegisterCharity> getListByPostId(int postId) {
		return registerCharityDAO.getListByPostId(postId);
	}

	public int totalRowByPostId(int postId) {
		return registerCharityDAO.totalRowByPostId(postId);
	}

	public int totalRowConfirmByPostId(int postId) {
		return registerCharityDAO.totalRowConfirmByPostId(postId);
	}

	public RegisterCharity findByPostIdAndUserId(int postId, int userId) {
		return registerCharityDAO.findByPostIdAndUserId(postId, userId);
	}

	public List<RegisterCharity> getListByUserId(int userId, int offset, int rowCount) {
		return registerCharityDAO.getListByUserId(userId, offset, rowCount);
	}

	public int totalRowByUserId(int userId) {
		return registerCharityDAO.totalRowByUserId(userId);
	}

	public int totalRowConfirmByUserId(int userId) {
		return registerCharityDAO.totalRowConfirmByUserId(userId);
	}

	public boolean checkCharity(int userId, int postId) {
		return registerCharityDAO.checkCharity(userId, postId);
	}

}

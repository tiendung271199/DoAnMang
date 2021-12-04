package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.UserDAO;
import doan.model.User;

@Service
public class UserService implements ICRUDService<User> {

	@Autowired
	private UserDAO userDAO;

	@Override
	public List<User> getAll() {
		return userDAO.getAll();
	}

	@Override
	public int save(User user) {
		return userDAO.save(user);
	}

	@Override
	public int update(User user) {
		return userDAO.update(user);
	}

	public int updatePassword(User user) {
		return userDAO.updatePassword(user);
	}

	@Override
	public int del(int id) {
		return userDAO.del(id);
	}

	@Override
	public User findById(int id) {
		return userDAO.findById(id);
	}

	@Override
	public List<User> getList(int offset, int rowCount) {
		return userDAO.getList(offset, rowCount);
	}

	@Override
	public int totalRow() {
		return userDAO.totalRow();
	}

	public User findUserDuplicate(User user, User oldUser, String properties) {
		return userDAO.findUserDuplicate(user, oldUser, properties);
	}

	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	public List<User> search(String username, int roleId, int offset, int rowCount) {
		return userDAO.search(username, roleId, offset, rowCount);
	}

	public int totalRowSearch(String username, int roleId) {
		return userDAO.totalRowSearch(username, roleId);
	}

}

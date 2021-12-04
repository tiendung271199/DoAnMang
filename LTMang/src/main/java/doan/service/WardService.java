package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.WardDAO;
import doan.model.Ward;

@Service
public class WardService implements ICRUDService<Ward> {

	@Autowired
	private WardDAO wardDAO;

	@Override
	public List<Ward> getAll() {
		return wardDAO.getAll();
	}

	@Override
	public int save(Ward t) {
		return 0;
	}

	@Override
	public int update(Ward t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public Ward findById(int id) {
		return wardDAO.findById(id);
	}

	@Override
	public List<Ward> getList(int offset, int rowCount) {
		return null;
	}

	@Override
	public int totalRow() {
		return 0;
	}

	public List<Ward> findByDistrictId(int districtId) {
		return wardDAO.findByDistrictId(districtId);
	}

}

package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.ProvinceDAO;
import doan.model.Province;

@Service
public class ProvinceService implements ICRUDService<Province> {

	@Autowired
	private ProvinceDAO provinceDAO;
	
	@Override
	public List<Province> getAll() {
		return provinceDAO.getAll();
	}

	@Override
	public int save(Province t) {
		return 0;
	}

	@Override
	public int update(Province t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public Province findById(int id) {
		return provinceDAO.findById(id);
	}

	@Override
	public List<Province> getList(int offset, int rowCount) {
		return null;
	}

	@Override
	public int totalRow() {
		return 0;
	}

}

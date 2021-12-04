package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.DistrictDAO;
import doan.model.District;

@Service
public class DistrictService implements ICRUDService<District> {

	@Autowired
	private DistrictDAO districtDAO;

	@Override
	public List<District> getAll() {
		return districtDAO.getAll();
	}

	@Override
	public int save(District t) {
		return 0;
	}

	@Override
	public int update(District t) {
		return 0;
	}

	@Override
	public int del(int id) {
		return 0;
	}

	@Override
	public District findById(int id) {
		return districtDAO.findById(id);
	}

	@Override
	public List<District> getList(int offset, int rowCount) {
		return null;
	}

	@Override
	public int totalRow() {
		return 0;
	}

	public List<District> findByProvinceId(int provinceId) {
		return districtDAO.findByProvinceId(provinceId);
	}

}

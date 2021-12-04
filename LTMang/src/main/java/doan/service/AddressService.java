package doan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doan.dao.AddressDAO;
import doan.model.Address;

@Service
public class AddressService implements ICRUDService<Address> {

	@Autowired
	private AddressDAO addressDAO;

	@Override
	public List<Address> getAll() {
		return null;
	}

	@Override
	public int save(Address address) {
		return addressDAO.save(address);
	}

	@Override
	public int update(Address address) {
		return addressDAO.update(address);
	}

	@Override
	public int del(int id) {
		return addressDAO.del(id);
	}

	@Override
	public Address findById(int id) {
		return null;
	}

	@Override
	public List<Address> getList(int offset, int rowCount) {
		return null;
	}

	@Override
	public int totalRow() {
		return 0;
	}

	public Address getNewestAddress() {
		return addressDAO.getNewestAddress();
	}

}

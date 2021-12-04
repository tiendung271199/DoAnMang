package doan.service;

import java.util.List;

public interface ICRUDService<T> {

	List<T> getAll();

	int save(T t);
	
	int update(T t);

	int del(int id);

	T findById(int id);
	
	List<T> getList(int offset, int rowCount);
	
	int totalRow();

}

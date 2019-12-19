package com.nx.service;

import java.util.List;

public interface IService<T>{
	
	T save(T entity);
	
	List<T> saveAll(Iterable<T> entities);

	T findById(Long id);

	Long deleteById(Long id);
}

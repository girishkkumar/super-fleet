package com.supertrans.service;

import java.util.List;

public interface IService<T> {
	List<T> findAll();

	T findById(Long id);

	T saveOrUpdate(T t);

	String deleteById(Long id);

}

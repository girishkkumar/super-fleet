package com.supertrans.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPageService<T> {

	Page<T> findAll(Pageable pageable);

}

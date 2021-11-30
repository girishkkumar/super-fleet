package com.supertrans.service;

import com.supertrans.entity.User;

public interface IUserService<T> extends IService<T> {

	User createOrUpdateUser(User userObj);

	User findByEmail(String email);

	void clearTokenOnLogout(String userId);

	User findByToken(String authToken);
}

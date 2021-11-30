package com.supertrans.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supertrans.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.email= ?1")
	User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email= ?1 and u.password= ?2")
	User findUser(String email, String password);

	@Query("SELECT u FROM User u WHERE u.token= ?1")
	User findByToken(String token);

	@Query("DELETE FROM User u WHERE u.email= ?1")
	@Transactional
	@Modifying
	int deleteByEmail(String email);

}

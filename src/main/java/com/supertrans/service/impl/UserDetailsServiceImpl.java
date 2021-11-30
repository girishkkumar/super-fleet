package com.supertrans.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.supertrans.entity.User;
import com.supertrans.exception.InvalidTokenException;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.repository.UserRepository;
import com.supertrans.service.IUserService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService, IUserService<User> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("Email " + email + " not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getGrantedAuthority(user));
	}

	private Collection<GrantedAuthority> getGrantedAuthority(User user) {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (user.getRole().getName().equalsIgnoreCase("admin")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isPresent()) {
			User user = userOpt.get();
			return user;
		}
		return null;
	}

	@Override
	public User saveOrUpdate(User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			userRepository.deleteById(id);
			jsonObject.put("message", "User deleted successfully");
		} catch (JSONException e) {
			log.error("Exception: {}", e);
		}
		return jsonObject.toString();
	}

	@Override
	public User createOrUpdateUser(User userObj) {
		User savedUser = userRepository.saveAndFlush(userObj);
		return savedUser;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void clearTokenOnLogout(String userId) {
		User user = userRepository.findById(Long.parseLong(userId))
				.orElseThrow(() -> new InvalidUserException(" User not found."));
		user.setToken(null);
		userRepository.save(user);
	}

	@Override
	public User findByToken(String authToken) {
		User user = userRepository.findByToken(authToken);
		if (user != null) {
			return user;
		} else {
			throw new InvalidTokenException("User token is not valid.");
		}
	}

}
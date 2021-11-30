package com.supertrans.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supertrans.config.JwtTokenProvider;
import com.supertrans.dto.Response;
import com.supertrans.dto.UserDTO;
import com.supertrans.entity.Role;
import com.supertrans.entity.RoleType;
import com.supertrans.entity.User;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.service.IRoleService;
import com.supertrans.service.IUserService;
import com.supertrans.util.SuperFleetUtil;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	private IUserService<User> userService;

	@Autowired
	private IRoleService<Role> roleService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> register(@RequestBody User user) {
		log.info("UserResourceImpl : register");
		Response response = Response.builder().build();
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			Role role = roleService.findByName(RoleType.USER.toString());
			user.setActive(true);
			user.setRole(role);
			user.setNamespace(UUID.randomUUID().toString());
			User savedUser = userService.createOrUpdateUser(user);
			response.setStatusCode("S-001");
			response.setMessage(savedUser.getFirstName() + " saved succesfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatusCode("JSE-001");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> authenticate(@RequestBody User user) {
		log.info("UserResourceImpl : authenticate");
		Response response = Response.builder().build();
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			if (authentication.isAuthenticated()) {
				String email = user.getEmail();
				User userAcc = userService.findByEmail(email);
				String token = tokenProvider.createToken(email, userAcc.getRole());
				userAcc.setToken(token);
				userService.createOrUpdateUser(userAcc);
				UserDTO userDTO = SuperFleetUtil.UserEntityToDTO.apply(userAcc);
				response.setStatusCode("S-001");
				response.setMessage("User logged in successfully");
				response.setData(userDTO);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			response.setStatusCode("JSE-001");
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		return null;
	}

	@GetMapping("/logout")
	public ResponseEntity<?> clearTokenOnLogout(@RequestHeader(name = "userId") String userId) {
		log.debug("Admin/User logout invoked");
		Response response = Response.builder().build();
		try {
			userService.clearTokenOnLogout(userId);
			response.setStatusCode("S001");
			response.setMessage("User logged Out successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (InvalidUserException ex) {
			response.setStatusCode("IUE-001");
			response.setMessage(ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
	}
}

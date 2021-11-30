package com.supertrans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.supertrans.entity.Role;
import com.supertrans.service.IRoleService;
import com.supertrans.util.SuperFleetConstants;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SuperFleetManagerApplication implements CommandLineRunner {

	@Autowired
	private IRoleService<Role> roleService;

	public static void main(String[] args) {
		SpringApplication.run(SuperFleetManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (roleService.findAll().isEmpty()) {
			roleService.saveOrUpdate(new Role(SuperFleetConstants.ADMIN.toString()));
			roleService.saveOrUpdate(new Role(SuperFleetConstants.USER.toString()));
		}
	}

}

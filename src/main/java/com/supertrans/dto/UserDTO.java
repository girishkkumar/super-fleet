package com.supertrans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDTO {

	private Long id;

	private String email;

	private String firstName;

	private String lastName;

	private String mobile;

	private String companyName;

	private String role;

	private boolean isActive;

	private String namespace;

	private String token;

	private String createdDate;

	private String updatedDate;

}

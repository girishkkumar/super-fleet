package com.supertrans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GarageDTO {

	protected Long id;

	private GarageDTO nearestGarage;

	private String name;

	private String latitude;

	private String longitude;

	private String email;

	private String address;

	private String contactNo;

	private String createdDate;

	private String updatedDate;

	private UserDTO createdBy;
	
	private String distance;

}

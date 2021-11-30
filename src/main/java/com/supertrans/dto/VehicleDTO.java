package com.supertrans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class VehicleDTO {

	protected Long id;

	private String vehicleNo;

	private Integer year;

	private Integer noOfWheels;

	private Integer odometerReading;

	private Boolean airConditioned;

	private Integer capacity;

	private String status;

	private String createdDate;

	private String updatedDate;

	private String vehicleImage;

	private String thumbnail;

	private UserDTO createdBy;

	private String nextMaintenanceDate;

	private Double resaleValue;
	
	private Integer avgOdometerReading;
	
	private Integer vehiclesCount;

}

package com.supertrans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MaintenanceRecordDTO {

	protected Long id;

	private VehicleDTO vehicle;

	private String problemDescription;

	private String createdDate;

	private String updatedDate;
	
	private String scheduledOn;
	
	private String executedOn;

}

package com.supertrans.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	protected Long id;

	@NonNull
	@Column(name = "vehicleNo", nullable = false)
	private String vehicleNo;

	@NonNull
	@Column(name = "year", nullable = false)
	private Integer year;

	@NonNull
	@Column(name = "noOfWheels", nullable = false)
	private Integer noOfWheels;

	@NonNull
	@Column(name = "odometerReading", nullable = false)
	private Integer odometerReading;

	@Column(name = "airConditioned", nullable = false)
	private Boolean airConditioned;

	@NonNull
	@Column(name = "capacity", nullable = false)
	private Integer capacity;

	@NonNull
	@Column(name = "status", nullable = false)
	private VehicleStatus status;

	@CreationTimestamp
	@Column(name = "createdDate", nullable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "updatedDate", nullable = false)
	@JsonIgnore
	private LocalDateTime updatedDate;

	@Column(name = "vehicleImage", nullable = false)
	private String vehicleImage;

	@Column(name = "thumbnail", nullable = false)
	private String thumbnail;

	@OneToOne(fetch = FetchType.EAGER)
	private User createdBy;

	@Column(name = "nextMaintenanceDate", nullable = false)
	private LocalDate nextMaintenanceDate;

	@Transient
	private Double resaleValue;

}

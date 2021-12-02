package com.supertrans.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
public class Garage {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	protected Long id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "nearest_gargage_id")
	private Garage nearestGarage;

	@NonNull
	@Column(name = "name", nullable = false)
	private String name;

	@NonNull
	@Column(name = "latitude", nullable = false)
	private String latitude;

	@NonNull
	@Column(name = "longitude", nullable = false)
	private String longitude;

	@NonNull
	@Column(name = "email", nullable = false)
	private String email;

	@NonNull
	@Column(name = "address", nullable = false)
	private String address;

	@NonNull
	@Column(name = "contactNo", nullable = false)
	private String contactNo;

	@CreationTimestamp
	@Column(name = "createdDate", nullable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "updatedDate", nullable = false)
	@JsonIgnore
	private LocalDateTime updatedDate;

	@OneToOne(fetch = FetchType.EAGER)
	private User createdBy;

}

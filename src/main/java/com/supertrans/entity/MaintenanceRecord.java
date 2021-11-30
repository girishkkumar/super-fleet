package com.supertrans.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class MaintenanceRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	protected Long id;

	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;

	@Column(name = "problemDescription", nullable = false)
	private String problemDescription;

	@CreationTimestamp
	@Column(name = "createdDate", nullable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "updatedDate", nullable = false)
	@JsonIgnore
	private LocalDateTime updatedDate;

	@Column(name = "scheduledOn", nullable = false)
	private LocalDate scheduledOn;

	@Column(name = "executedOn", nullable = false)
	private LocalDate executedOn;

}

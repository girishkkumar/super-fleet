package com.supertrans.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
public class ResaleProperties {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	protected Long id;

	@OneToOne(fetch = FetchType.EAGER)
	private User createdBy;

	@CreationTimestamp
	@Column(name = "createdDate", nullable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "updatedDate", nullable = false)
	@JsonIgnore
	private LocalDateTime updatedDate;

	private Double acPercent;

	private Double historicPercent;

	private Integer historicYear;

	private Double sellingPrice24;

	private Double sellingPrice36;

	private Integer odometerReading;

	private Double reduceByMilePercent;

}

package com.supertrans.entity;

import java.io.Serializable;
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
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false, unique = true)
	private Long id;

	@NonNull
	@Column(name = "email", nullable = false)
	private String email;

	@NonNull
	@Column(name = "firstName", nullable = false)
	private String firstName;

	@NonNull
	@Column(name = "lastName", nullable = false)
	private String lastName;

	@NonNull
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "mobile", nullable = false)
	private String mobile;

	@Column(name = "companyName", nullable = true)
	private String companyName;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

	@Column(name = "isActive")
	private boolean isActive;

	@Column(name = "namespace", nullable = false)
	private String namespace;

	@Column(name = "token")
	private String token;

	@CreationTimestamp
	@Column(name = "createdDate", nullable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@Column(name = "updatedDate", nullable = false)
	@JsonIgnore
	private LocalDateTime updatedDate;

}

package com.supertrans.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;

import com.supertrans.dto.GarageDTO;
import com.supertrans.dto.MaintenanceRecordDTO;
import com.supertrans.dto.UserDTO;
import com.supertrans.dto.VehicleDTO;
import com.supertrans.entity.Garage;
import com.supertrans.entity.MaintenanceRecord;
import com.supertrans.entity.User;
import com.supertrans.entity.Vehicle;
import com.supertrans.entity.VehicleStatus;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SuperFleetUtil {

	public static Function<User, UserDTO> UserEntityToDTO = new Function<User, UserDTO>() {

		@Override
		public UserDTO apply(User user) {
			log.info("Inside UserEntityToDTO Function");
			UserDTO dto = new UserDTO();
			BeanUtils.copyProperties(user, dto);
			dto.setRole(user.getRole().getName());
			if (user.getCreatedDate() != null) {
				dto.setCreatedDate(
						user.getCreatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (user.getUpdatedDate() != null) {
				dto.setUpdatedDate(
						user.getUpdatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}

			return dto;
		}
	};

	public static Function<Vehicle, VehicleDTO> VehicleEntityToDTO = new Function<Vehicle, VehicleDTO>() {

		@Override
		public VehicleDTO apply(Vehicle vehicle) {
			log.info("Inside VehicleEntityToDTO Function");
			VehicleDTO dto = new VehicleDTO();
			BeanUtils.copyProperties(vehicle, dto);
			dto.setStatus(vehicle.getStatus().toString());
			if (vehicle.getCreatedDate() != null) {
				dto.setCreatedDate(
						vehicle.getCreatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (vehicle.getUpdatedDate() != null) {
				dto.setUpdatedDate(
						vehicle.getUpdatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (vehicle.getNextMaintenanceDate() != null) {
				dto.setNextMaintenanceDate(
						vehicle.getNextMaintenanceDate().format(SuperFleetConstants.LOCALDATE_FORMATTER).toString());
			}
			if (vehicle.getCreatedBy() != null) {
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(vehicle.getCreatedBy(), userDTO);
				userDTO.setRole(vehicle.getCreatedBy().getRole().getName());
				if (vehicle.getCreatedBy().getCreatedDate() != null) {
					userDTO.setCreatedDate(vehicle.getCreatedBy().getCreatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (vehicle.getCreatedBy().getUpdatedDate() != null) {
					userDTO.setUpdatedDate(vehicle.getCreatedBy().getUpdatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				dto.setCreatedBy(userDTO);
			}

			return dto;
		}
	};

	public static Function<MaintenanceRecord, MaintenanceRecordDTO> MaintenanceRecordEntityToDTO = new Function<MaintenanceRecord, MaintenanceRecordDTO>() {

		@Override
		public MaintenanceRecordDTO apply(MaintenanceRecord maintenanceRecord) {
			log.info("Inside MaintenanceRecordEntityToDTO Function");
			MaintenanceRecordDTO dto = new MaintenanceRecordDTO();
			BeanUtils.copyProperties(maintenanceRecord, dto);
			if (maintenanceRecord.getCreatedDate() != null) {
				dto.setCreatedDate(maintenanceRecord.getCreatedDate()
						.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (maintenanceRecord.getUpdatedDate() != null) {
				dto.setUpdatedDate(maintenanceRecord.getUpdatedDate()
						.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (maintenanceRecord.getScheduledOn() != null) {
				dto.setScheduledOn(
						maintenanceRecord.getScheduledOn().format(SuperFleetConstants.LOCALDATE_FORMATTER).toString());
			}
			if (maintenanceRecord.getExecutedOn() != null) {
				dto.setExecutedOn(
						maintenanceRecord.getExecutedOn().format(SuperFleetConstants.LOCALDATE_FORMATTER).toString());
			}

			return dto;
		}
	};

	public static Function<Garage, GarageDTO> GarageEntityToDTO = new Function<Garage, GarageDTO>() {

		@Override
		public GarageDTO apply(Garage garage) {
			log.info("Inside GarageEntityToDTO Function");
			GarageDTO dto = new GarageDTO();
			BeanUtils.copyProperties(garage, dto);
			if (garage.getCreatedDate() != null) {
				dto.setCreatedDate(
						garage.getCreatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (garage.getUpdatedDate() != null) {
				dto.setUpdatedDate(
						garage.getUpdatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
			}
			if (garage.getCreatedBy() != null) {
				UserDTO userDTO = new UserDTO();
				BeanUtils.copyProperties(garage.getCreatedBy(), userDTO);
				userDTO.setRole(garage.getCreatedBy().getRole().getName());
				if (garage.getCreatedBy().getCreatedDate() != null) {
					userDTO.setCreatedDate(garage.getCreatedBy().getCreatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (garage.getCreatedBy().getUpdatedDate() != null) {
					userDTO.setUpdatedDate(garage.getCreatedBy().getUpdatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				dto.setCreatedBy(userDTO);
			}
			if (garage.getNearestGarage() != null) {
				GarageDTO nearestGarageDTO = new GarageDTO();
				BeanUtils.copyProperties(garage, nearestGarageDTO);
				if (garage.getNearestGarage().getCreatedDate() != null) {
					nearestGarageDTO.setCreatedDate(garage.getNearestGarage().getCreatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (garage.getNearestGarage().getUpdatedDate() != null) {
					nearestGarageDTO.setUpdatedDate(garage.getNearestGarage().getUpdatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				dto.setNearestGarage(nearestGarageDTO);
			}

			return dto;
		}
	};

	public static Function<VehicleDTO, Vehicle> VehicleDTOToEntity = new Function<VehicleDTO, Vehicle>() {

		@Override
		public Vehicle apply(VehicleDTO vehicleDTO) {
			log.info("Inside VehicleDTOToEntity Function");
			Vehicle vehicle = new Vehicle();
			BeanUtils.copyProperties(vehicleDTO, vehicle);
			vehicle.setStatus(VehicleStatus.valueOf(vehicleDTO.getStatus()));
			if (vehicleDTO.getCreatedDate() != null) {
				vehicle.setCreatedDate(convertStringToLocalDateTime(vehicleDTO.getCreatedDate()));
			}
			if (vehicleDTO.getUpdatedDate() != null) {
				vehicle.setUpdatedDate(convertStringToLocalDateTime(vehicleDTO.getUpdatedDate()));
			}
			if (vehicleDTO.getNextMaintenanceDate() != null) {
				LocalDate maintenanceDate = LocalDate.parse(vehicleDTO.getNextMaintenanceDate(),
						SuperFleetConstants.LOCALDATE_FORMATTER);
				vehicle.setNextMaintenanceDate(maintenanceDate);
			}
			return vehicle;
		}
	};

	public static Function<MaintenanceRecordDTO, MaintenanceRecord> MaintenanceRecordDTOToEntity = new Function<MaintenanceRecordDTO, MaintenanceRecord>() {

		@Override
		public MaintenanceRecord apply(MaintenanceRecordDTO maintenanceRecordDTO) {
			log.info("Inside MaintenanceRecordDTOToEntity Function");
			MaintenanceRecord maintenanceRecord = new MaintenanceRecord();
			BeanUtils.copyProperties(maintenanceRecordDTO, maintenanceRecord);
			if (maintenanceRecordDTO.getCreatedDate() != null) {
				maintenanceRecord.setCreatedDate(convertStringToLocalDateTime(maintenanceRecordDTO.getCreatedDate()));
			}
			if (maintenanceRecordDTO.getUpdatedDate() != null) {
				maintenanceRecord.setUpdatedDate(convertStringToLocalDateTime(maintenanceRecordDTO.getUpdatedDate()));
			}
			if (maintenanceRecordDTO.getScheduledOn() != null) {
				LocalDate scheduledOn = LocalDate.parse(maintenanceRecordDTO.getScheduledOn(),
						SuperFleetConstants.LOCALDATE_FORMATTER);
				maintenanceRecord.setScheduledOn(scheduledOn);
			}
			if (maintenanceRecordDTO.getExecutedOn() != null) {
				LocalDate executedOn = LocalDate.parse(maintenanceRecordDTO.getExecutedOn(),
						SuperFleetConstants.LOCALDATE_FORMATTER);
				maintenanceRecord.setExecutedOn(executedOn);
			}
			return maintenanceRecord;
		}
	};

	public static Function<GarageDTO, Garage> GarageDTOToEntity = new Function<GarageDTO, Garage>() {

		@Override
		public Garage apply(GarageDTO garageDTO) {
			log.info("Inside VehicleDTOToEntity Function");
			Garage garage = new Garage();
			BeanUtils.copyProperties(garageDTO, garage);
			if (garageDTO.getCreatedDate() != null) {
				garage.setCreatedDate(convertStringToLocalDateTime(garageDTO.getCreatedDate()));
			}
			if (garageDTO.getUpdatedDate() != null) {
				garage.setUpdatedDate(convertStringToLocalDateTime(garageDTO.getUpdatedDate()));
			}
			if (garageDTO.getNearestGarage() != null) {
				Garage nearestGarage = new Garage();
				BeanUtils.copyProperties(garageDTO.getNearestGarage(), nearestGarage);
				if (garageDTO.getCreatedDate() != null) {
					nearestGarage.setCreatedDate(
							convertStringToLocalDateTime(garageDTO.getNearestGarage().getCreatedDate()));
				}
				if (garageDTO.getUpdatedDate() != null) {
					nearestGarage.setUpdatedDate(
							convertStringToLocalDateTime(garageDTO.getNearestGarage().getUpdatedDate()));
				}
				garage.setNearestGarage(nearestGarage);
			}
			return garage;
		}
	};

	public static Function<List<Vehicle>, List<VehicleDTO>> VehicleEntitiesToDTO = new Function<List<Vehicle>, List<VehicleDTO>>() {

		@Override
		public List<VehicleDTO> apply(List<Vehicle> vehicleEntities) {
			log.info("Converting vehicle Entities to DTO List");
			List<VehicleDTO> vehiclesDTOList = new ArrayList<VehicleDTO>();
			for (Vehicle vehicle : vehicleEntities) {
				VehicleDTO dto = new VehicleDTO();
				BeanUtils.copyProperties(vehicle, dto);
				dto.setStatus(vehicle.getStatus().toString());
				if (vehicle.getCreatedDate() != null) {
					dto.setCreatedDate(
							vehicle.getCreatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (vehicle.getUpdatedDate() != null) {
					dto.setUpdatedDate(
							vehicle.getUpdatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (vehicle.getNextMaintenanceDate() != null) {
					dto.setNextMaintenanceDate(vehicle.getNextMaintenanceDate()
							.format(SuperFleetConstants.LOCALDATE_FORMATTER).toString());
				}
				if (vehicle.getCreatedBy() != null) {
					UserDTO userDTO = new UserDTO();
					BeanUtils.copyProperties(vehicle.getCreatedBy(), userDTO);
					userDTO.setRole(vehicle.getCreatedBy().getRole().getName());
					if (vehicle.getCreatedBy().getCreatedDate() != null) {
						userDTO.setCreatedDate(vehicle.getCreatedBy().getCreatedDate()
								.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
					}
					if (vehicle.getCreatedBy().getUpdatedDate() != null) {
						userDTO.setUpdatedDate(vehicle.getCreatedBy().getUpdatedDate()
								.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
					}
					dto.setCreatedBy(userDTO);
				}
				vehiclesDTOList.add(dto);
			}
			return vehiclesDTOList;
		}
	};

	public static Function<List<MaintenanceRecord>, List<MaintenanceRecordDTO>> MaintenanceRecordEntitiesToDTO = new Function<List<MaintenanceRecord>, List<MaintenanceRecordDTO>>() {

		@Override
		public List<MaintenanceRecordDTO> apply(List<MaintenanceRecord> maintenanceRecordEntities) {
			log.info("Converting Maintenance Record Entities to DTO List");
			List<MaintenanceRecordDTO> maintenanceRecordsDTOList = new ArrayList<MaintenanceRecordDTO>();
			for (MaintenanceRecord maintenanceRecord : maintenanceRecordEntities) {
				MaintenanceRecordDTO dto = new MaintenanceRecordDTO();
				BeanUtils.copyProperties(maintenanceRecord, dto);
				if (maintenanceRecord.getCreatedDate() != null) {
					dto.setCreatedDate(maintenanceRecord.getCreatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (maintenanceRecord.getUpdatedDate() != null) {
					dto.setUpdatedDate(maintenanceRecord.getUpdatedDate()
							.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (maintenanceRecord.getScheduledOn() != null) {
					dto.setScheduledOn(maintenanceRecord.getScheduledOn()
							.format(SuperFleetConstants.LOCALDATE_FORMATTER).toString());
				}
				if (maintenanceRecord.getExecutedOn() != null) {
					dto.setExecutedOn(maintenanceRecord.getExecutedOn().format(SuperFleetConstants.LOCALDATE_FORMATTER)
							.toString());
				}
				maintenanceRecordsDTOList.add(dto);
			}
			return maintenanceRecordsDTOList;
		}
	};

	public static Function<List<Garage>, List<GarageDTO>> GarageEntitiesToDTO = new Function<List<Garage>, List<GarageDTO>>() {

		@Override
		public List<GarageDTO> apply(List<Garage> garageEntities) {
			log.info("Converting Garage Entities to DTO List");
			List<GarageDTO> garagesDTOList = new ArrayList<GarageDTO>();

			for (Garage garage : garageEntities) {

				GarageDTO dto = new GarageDTO();
				BeanUtils.copyProperties(garage, dto);
				if (garage.getCreatedDate() != null) {
					dto.setCreatedDate(
							garage.getCreatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (garage.getUpdatedDate() != null) {
					dto.setUpdatedDate(
							garage.getUpdatedDate().format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
				}
				if (garage.getCreatedBy() != null) {
					UserDTO userDTO = new UserDTO();
					BeanUtils.copyProperties(garage.getCreatedBy(), userDTO);
					userDTO.setRole(garage.getCreatedBy().getRole().getName());
					if (garage.getCreatedBy().getCreatedDate() != null) {
						userDTO.setCreatedDate(garage.getCreatedBy().getCreatedDate()
								.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
					}
					if (garage.getCreatedBy().getUpdatedDate() != null) {
						userDTO.setUpdatedDate(garage.getCreatedBy().getUpdatedDate()
								.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
					}
					dto.setCreatedBy(userDTO);
				}
				if (garage.getNearestGarage() != null) {
					GarageDTO nearestGarageDTO = new GarageDTO();
					BeanUtils.copyProperties(garage, nearestGarageDTO);
					if (garage.getNearestGarage().getCreatedDate() != null) {
						nearestGarageDTO.setCreatedDate(garage.getNearestGarage().getCreatedDate()
								.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
					}
					if (garage.getNearestGarage().getUpdatedDate() != null) {
						nearestGarageDTO.setUpdatedDate(garage.getNearestGarage().getUpdatedDate()
								.format(SuperFleetConstants.LOCALDATETIME_FORMATTER).toString());
					}
					dto.setNearestGarage(nearestGarageDTO);
				}
				garagesDTOList.add(dto);
			}
			return garagesDTOList;
		}
	};

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static LocalDateTime convertStringToLocalDateTime(String date) {
		LocalDateTime dateTime = null;
		try {
			dateTime = LocalDateTime.parse(date, SuperFleetConstants.format);

		} catch (Exception e) {
			try {
				dateTime = LocalDateTime.parse(date, SuperFleetConstants.formatSingleMS);
			} catch (Exception e2) {
				dateTime = LocalDateTime.parse(date, SuperFleetConstants.formatWithoutMS);
			}
		}
		return dateTime;
	}

	public static LocalDate convertStringToLocalDate(String dateStr) {
		LocalDate date = null;
		try {
			date = LocalDate.parse(dateStr, SuperFleetConstants.LOCALDATE_FORMATTER);
		} catch (Exception e) {
			log.error("Unable to parse local date: {}", e);
		}
		return date;
	}

}

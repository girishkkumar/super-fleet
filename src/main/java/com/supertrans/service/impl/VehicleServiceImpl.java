package com.supertrans.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.supertrans.dto.VehicleDTO;
import com.supertrans.entity.User;
import com.supertrans.entity.Vehicle;
import com.supertrans.entity.VehicleStatus;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.exception.InvalidVehicleException;
import com.supertrans.exception.NoRecordsFoundException;
import com.supertrans.repository.UserRepository;
import com.supertrans.repository.VehicleRepository;
import com.supertrans.service.IPageService;
import com.supertrans.service.IVehicleService;
import com.supertrans.util.SuperFleetConstants;
import com.supertrans.util.SuperFleetUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class VehicleServiceImpl implements IVehicleService<VehicleDTO>, IPageService<Vehicle> {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("${resale.ac.percent}")
	private Integer resaleACPercent;

	@Value("${resale.historic.percent}")
	private Integer resaleHistoricPercent;

	@Value("${resale.historic.year}")
	private Integer resaleHistoricYear;

	@Value("${resale.selling.price.24}")
	private Integer resaleSellingPrice24;

	@Value("${resale.selling.price.36}")
	private Integer resaleSellingPrice36;

	@Value("${resale.odometer.reading}")
	private Integer resaleOdoReading;

	@Value("${resale.odometer.reduce.priceby.mile}")
	private Double resaleOdoReducePricing;

	private Double getResaleValue(Vehicle vehicle) {
		Double sellingPrice = startingSellingPrice(vehicle.getCapacity());
		if (vehicle.getStatus().equals(VehicleStatus.READY_FOR_USE)) {
			if (vehicle.getYear() <= resaleHistoricYear && vehicle.getAirConditioned()) {
				sellingPrice = calculateSellingPrice(sellingPrice, (resaleHistoricPercent + resaleACPercent));
				return sellingPrice;
			} else if (vehicle.getYear() <= resaleHistoricYear) {
				sellingPrice = calculateSellingPrice(sellingPrice, resaleHistoricPercent);
				return sellingPrice;
			} else if (vehicle.getAirConditioned()) {
				sellingPrice = calculateSellingPrice(sellingPrice, resaleACPercent);
			}
			sellingPrice = calculateBasedOnOdometerReading(vehicle.getOdometerReading(), sellingPrice);

			return sellingPrice;
		}
		return null;
	}

	private Double calculateSellingPrice(Double sellingPrice, int percentageIncrease) {
		Double percentValue = (double) (sellingPrice * (percentageIncrease / 100.0f));
		Double calculatedPrice = (sellingPrice + percentValue);
		return SuperFleetUtil.round(calculatedPrice, 2);
	}

	private Double calculateBasedOnOdometerReading(Integer odometerReading, Double sellingPrice) {
		if (odometerReading > resaleOdoReading) {
			Double milesDiff = (double) (odometerReading - resaleOdoReading);
			Double reducablePrice = (milesDiff * resaleOdoReducePricing);
			return SuperFleetUtil.round((sellingPrice - reducablePrice), 2);
		}
		return sellingPrice;
	}

	private Double startingSellingPrice(Integer capacity) {
		if (capacity == SuperFleetConstants.BUS_CAPACITY_24) {
			return resaleSellingPrice24.doubleValue();
		}
		if (capacity == SuperFleetConstants.BUS_CAPACITY_36) {
			return resaleSellingPrice36.doubleValue();
		}
		return null;

	}

	@Override
	public List<VehicleDTO> findAll() {
		Collection<Vehicle> vehicles = (Collection<Vehicle>) vehicleRepository.findAll();
		List<Vehicle> vehiclesList = vehicles.stream().collect(toList());
		List<VehicleDTO> vehicleDTOList = SuperFleetUtil.VehicleEntitiesToDTO.apply(vehiclesList);
		Collection<VehicleDTO> vehiclesCollection = vehicleDTOList;
		return vehiclesCollection.stream().collect(toList());
	}

	@Override
	public VehicleDTO findById(Long id) {
		Optional<Vehicle> vehicleOpt = vehicleRepository.findById(id);
		if (vehicleOpt.isPresent()) {
			Vehicle vehicle = vehicleOpt.get();
			Double resaleValue = getResaleValue(vehicle);
			vehicle.setResaleValue(resaleValue);
			VehicleDTO vehicleDTO = SuperFleetUtil.VehicleEntityToDTO.apply(vehicle);
			return vehicleDTO;
		} else {
			throw new InvalidVehicleException("Vehicle doesn't exist");
		}
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			vehicleRepository.deleteById(id);
			jsonObject.put("message", "Vehicle deleted successfully");
		} catch (JSONException e) {
			log.error("Exception: {}", e);
		}
		return jsonObject.toString();
	}

	@Override
	public Page<Vehicle> findAll(Pageable pageable) {
		Page<Vehicle> vehiclesList = vehicleRepository.findAll(pageable);
//		List<Vehicle> vehicles = vehiclesList.toList();
//		List<VehicleDTO> vehicleDTOList = SuperFleetUtil.VehicleEntitiesToDTO.apply(vehicles);
//
//		final int start = (int) pageable.getOffset();
//		final int end = Math.min((start + pageable.getPageSize()), vehicleDTOList.size());
//		final Page<VehicleDTO> page = new PageImpl<>(vehicleDTOList.subList(start, end), pageable,
//				vehicleDTOList.size());
		return vehiclesList;
	}

	@Override
	public VehicleDTO saveOrUpdate(VehicleDTO vehicleDTO) {
		Vehicle vehicle = null;
		if (vehicleDTO.getId() != null) {
			Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleDTO.getId());
			if (vehicleOpt.isPresent()) {
				vehicle = vehicleOpt.get();
				vehicle.setAirConditioned(vehicleDTO.getAirConditioned());
				vehicle.setCapacity(vehicleDTO.getCapacity());
				vehicle.setNextMaintenanceDate(
						SuperFleetUtil.convertStringToLocalDate(vehicleDTO.getNextMaintenanceDate()));
				vehicle.setNoOfWheels(vehicleDTO.getNoOfWheels());
				vehicle.setOdometerReading(vehicleDTO.getOdometerReading());
				vehicle.setStatus(VehicleStatus.valueOf(vehicleDTO.getStatus()));
				vehicle.setVehicleNo(vehicleDTO.getVehicleNo());
				vehicle.setYear(vehicleDTO.getYear());
				vehicle.setThumbnail(SuperFleetConstants.THUMBNAIL);
				vehicle.setVehicleImage(SuperFleetConstants.ACTUAL_IMAGE);

			}
		} else {
			vehicle = SuperFleetUtil.VehicleDTOToEntity.apply(vehicleDTO);
			vehicle.setThumbnail(SuperFleetConstants.THUMBNAIL);
			vehicle.setVehicleImage(SuperFleetConstants.ACTUAL_IMAGE);
			User createdBy = userRepository.findByEmail(vehicleDTO.getCreatedBy().getEmail());
			if (createdBy != null) {
				vehicle.setCreatedBy(createdBy);
			} else {
				throw new InvalidUserException("User doesnt exist");
			}
		}
		vehicle = vehicleRepository.save(vehicle);
		vehicleDTO = SuperFleetUtil.VehicleEntityToDTO.apply(vehicle);
		return vehicleDTO;
	}

	@Override
	public List<VehicleDTO> findAllByUser(User user) {
		try {
			List<Vehicle> vehicles = vehicleRepository.findByCreatedBy(user);
			List<VehicleDTO> vehicleDTOList = SuperFleetUtil.VehicleEntitiesToDTO.apply(vehicles);
			return vehicleDTOList;
		} catch (Exception e) {
			log.error("Exception: {}", e);
		}
		return null;
	}

	@Override
	public Page<Vehicle> findAll(User user, Pageable pageable) {
		Page<Vehicle> vehiclesList = vehicleRepository.findByCreatedBy(user, pageable);
//		List<Vehicle> vehicles = vehiclesList.toList();
//		List<VehicleDTO> vehicleDTOList = SuperFleetUtil.VehicleEntitiesToDTO.apply(vehicles);
//
//		final int start = (int) pageable.getOffset();
//		final int end = Math.min((start + pageable.getPageSize()), vehicleDTOList.size());
//		final Page<VehicleDTO> page = new PageImpl<>(vehicleDTOList.subList(start, end), pageable,
//				vehicleDTOList.size());
		return vehiclesList;
	}

	public List<VehicleDTO> fetchAvgOdometerReadingByYear(User user) {
		try {
			List<VehicleDTO> vehiclesDTOList = new ArrayList<VehicleDTO>();
			List<Object[]> odoReadingYearMapList = vehicleRepository.findAvgOdometerReadingGroupByYear(user);
			if (odoReadingYearMapList != null) {
				for (Object[] objArr : odoReadingYearMapList) {
					VehicleDTO vehicleDTO = new VehicleDTO();
					Double avgOdometerReading = Double.parseDouble(String.valueOf(objArr[0]));
					vehicleDTO.setAvgOdometerReading(avgOdometerReading.intValue());
					Integer year = Integer.parseInt(String.valueOf(objArr[1]));
					vehicleDTO.setYear(year);
					vehiclesDTOList.add(vehicleDTO);
				}
				return vehiclesDTOList;
			} else {
				throw new NoRecordsFoundException("No matching records found for the user");
			}

		} catch (Exception e) {
			throw new NoRecordsFoundException("No matching records found for the user");
		}
	}

	@Override
	public List<VehicleDTO> fetchAllVehiclesOfCustomersByYear(Pageable pageable) {
		try {
			List<VehicleDTO> vehiclesDTOList = new ArrayList<VehicleDTO>();
			List<Object[]> aggVehiclesofCustomerByYearList = vehicleRepository
					.findAllVehiclesOfCustomersByYear(pageable);
			if (aggVehiclesofCustomerByYearList != null) {
				for (Object[] objArr : aggVehiclesofCustomerByYearList) {
					VehicleDTO vehicleDTO = new VehicleDTO();
					Integer busesCount = Integer.parseInt(String.valueOf(objArr[0]));
					vehicleDTO.setVehiclesCount(busesCount);
					Integer year = Integer.parseInt(String.valueOf(objArr[1]));
					vehicleDTO.setYear(year);
					vehiclesDTOList.add(vehicleDTO);
				}
				return vehiclesDTOList;
			} else {
				throw new NoRecordsFoundException("No matching records found for the user");
			}
		} catch (Exception e) {
			throw new NoRecordsFoundException("No matching records found for the user");
		}
	}

}

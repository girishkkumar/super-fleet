package com.supertrans.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.supertrans.dto.VehicleDTO;
import com.supertrans.entity.User;
import com.supertrans.entity.Vehicle;

public interface IVehicleService<T> extends IService<T> {

	List<VehicleDTO> findAllByUser(User user);

	Page<Vehicle> findAll(User user, Pageable pageable);

	List<VehicleDTO> fetchAvgOdometerReadingByYear(User user);

	List<VehicleDTO> fetchAllVehiclesOfCustomersByYear(Pageable pageable);


}

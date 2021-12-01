package com.supertrans.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supertrans.entity.User;
import com.supertrans.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	List<Vehicle> findByCreatedBy(User user);

	Page<Vehicle> findByCreatedBy(User user, Pageable pageable);

	@Query(value = "select AVG(odometerReading) as avgOdometerReading, year as manufactureYear from Vehicle where createdBy = :userId group by manufactureYear")
	List<Object[]> findAvgOdometerReadingGroupByYear(User userId);

	@Query(value = "select count(id) as busesCount, year as manufactureYear from Vehicle group by manufactureYear")
	List<Object[]> findAllVehiclesOfCustomersByYear(Pageable pageable);

}

package com.supertrans.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supertrans.entity.MaintenanceRecord;
import com.supertrans.entity.Vehicle;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord, Long> {

	List<MaintenanceRecord> findByVehicle(Vehicle vehicle);

}

package com.supertrans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supertrans.entity.GarageVehicles;

@Repository
public interface GarageVehiclesRepository extends JpaRepository<GarageVehicles, Long> {

}

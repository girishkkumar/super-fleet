package com.supertrans.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.supertrans.entity.Garage;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

	@Query(nativeQuery = true, value = "SELECT id,name,latitude, longitude, address, email, contact_no, ( 3959 * acos( cos( radians(:latitude) ) * cos( radians( latitude ) ) * cos( radians( longitude ) - "
			+ "radians(:longitude) ) + sin( radians(:latitude) ) * sin( radians( latitude ) ) ) ) AS distance FROM garage HAVING distance < 100 ORDER BY distance LIMIT 1")
	List<Object[]> findNearestGarageByLatAndLong(Double latitude, Double longitude);

}

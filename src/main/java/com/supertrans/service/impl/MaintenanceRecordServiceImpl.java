package com.supertrans.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.supertrans.dto.MaintenanceRecordDTO;
import com.supertrans.entity.MaintenanceRecord;
import com.supertrans.entity.Vehicle;
import com.supertrans.exception.InvalidMaintenanceRecordException;
import com.supertrans.exception.InvalidVehicleException;
import com.supertrans.repository.MaintenanceRecordRepository;
import com.supertrans.repository.VehicleRepository;
import com.supertrans.service.IMaintenanceRecordService;
import com.supertrans.service.IPageService;
import com.supertrans.util.SuperFleetUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MaintenanceRecordServiceImpl
		implements IMaintenanceRecordService<MaintenanceRecordDTO>, IPageService<MaintenanceRecordDTO> {

	@Autowired
	private MaintenanceRecordRepository maintenanceRecordRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Override
	public List<MaintenanceRecordDTO> findAll() {
		Collection<MaintenanceRecord> maintenanceRecords = (Collection<MaintenanceRecord>) maintenanceRecordRepository
				.findAll();
		List<MaintenanceRecord> maintenanceRecordsList = maintenanceRecords.stream().collect(toList());
		List<MaintenanceRecordDTO> maintenanceRecordDTOList = SuperFleetUtil.MaintenanceRecordEntitiesToDTO
				.apply(maintenanceRecordsList);
		Collection<MaintenanceRecordDTO> garagesCollection = maintenanceRecordDTOList;
		return garagesCollection.stream().collect(toList());
	}

	@Override
	public MaintenanceRecordDTO findById(Long id) {
		Optional<MaintenanceRecord> maintenanceRecordOpt = maintenanceRecordRepository.findById(id);
		if (maintenanceRecordOpt.isPresent()) {
			MaintenanceRecord maintenanceRecord = maintenanceRecordOpt.get();
			MaintenanceRecordDTO maintenanceRecordDTO = SuperFleetUtil.MaintenanceRecordEntityToDTO
					.apply(maintenanceRecord);
			return maintenanceRecordDTO;
		} else {
			throw new InvalidMaintenanceRecordException("Maintenance Record doesn't exist");
		}
	}

	@Override
	public MaintenanceRecordDTO saveOrUpdate(MaintenanceRecordDTO maintenanceRecordDTO) {
		MaintenanceRecord maintenanceRecord = null;
		if (maintenanceRecordDTO.getId() != null) {
			Optional<MaintenanceRecord> maintenanceRecordOpt = maintenanceRecordRepository
					.findById(maintenanceRecordDTO.getId());
			if (maintenanceRecordOpt.isPresent()) {
				maintenanceRecord = maintenanceRecordOpt.get();
				maintenanceRecord
						.setExecutedOn(SuperFleetUtil.convertStringToLocalDate(maintenanceRecordDTO.getExecutedOn()));
				maintenanceRecord
						.setScheduledOn(SuperFleetUtil.convertStringToLocalDate(maintenanceRecordDTO.getScheduledOn()));
				maintenanceRecord.setProblemDescription(maintenanceRecordDTO.getProblemDescription());
			}
		} else {
			maintenanceRecord = SuperFleetUtil.MaintenanceRecordDTOToEntity.apply(maintenanceRecordDTO);
			try {
				Vehicle vehicle = vehicleRepository.findById(maintenanceRecordDTO.getVehicle().getId()).get();
				if (vehicle != null) {
					maintenanceRecord.setVehicle(vehicle);
				} else {
					throw new InvalidVehicleException("Vehicle doesnt exist");
				}

			} catch (Exception e) {
				throw new InvalidVehicleException("Vehicle doesnt exist");
			}
		}
		maintenanceRecord = maintenanceRecordRepository.save(maintenanceRecord);
		maintenanceRecordDTO = SuperFleetUtil.MaintenanceRecordEntityToDTO.apply(maintenanceRecord);
		return maintenanceRecordDTO;
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			maintenanceRecordRepository.deleteById(id);
			jsonObject.put("message", "Maintenance record deleted successfully");
		} catch (JSONException e) {
			log.error("Exception: {}", e);
		}
		return jsonObject.toString();
	}

	@Override
	public Page<MaintenanceRecordDTO> findAll(Pageable pageable) {
		Page<MaintenanceRecord> maintenanceRecords = maintenanceRecordRepository.findAll(pageable);
		List<MaintenanceRecord> maintenanceRecordsList = maintenanceRecords.toList();
		List<MaintenanceRecordDTO> maintenanceRecordDTOList = SuperFleetUtil.MaintenanceRecordEntitiesToDTO
				.apply(maintenanceRecordsList);

		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), maintenanceRecordDTOList.size());
		final Page<MaintenanceRecordDTO> page = new PageImpl<>(maintenanceRecordDTOList.subList(start, end), pageable,
				maintenanceRecordDTOList.size());
		return page;
	}

	@Override
	public List<MaintenanceRecordDTO> findAllByVehicleId(Long vehicleId) {
		try {
			Optional<Vehicle> vehicleOpt = vehicleRepository.findById(vehicleId);
			if (vehicleOpt.isPresent()) {
				Vehicle vehicle = vehicleOpt.get();
				List<MaintenanceRecord> maintenanceRecords = maintenanceRecordRepository.findByVehicle(vehicle);
				List<MaintenanceRecordDTO> maintenanceRecordDTOList = SuperFleetUtil.MaintenanceRecordEntitiesToDTO
						.apply(maintenanceRecords);
				return maintenanceRecordDTOList;
			} else {
				throw new InvalidVehicleException("Vehicle doesnt exist");
			}
		} catch (Exception e) {
			throw new InvalidMaintenanceRecordException("Maintenance Records doesnt exist");
		}

	}

}

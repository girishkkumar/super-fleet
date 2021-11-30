package com.supertrans.service;

import java.util.List;

import com.supertrans.dto.MaintenanceRecordDTO;

public interface IMaintenanceRecordService<T> extends IService<T> {

	List<MaintenanceRecordDTO> findAllByVehicleId(Long vehicleId);

}

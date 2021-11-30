package com.supertrans.service;

import com.supertrans.dto.GarageDTO;

public interface IGarageService<T> extends IService<T> {

	GarageDTO findNearestGarage(Double latitude, Double longitude);

}

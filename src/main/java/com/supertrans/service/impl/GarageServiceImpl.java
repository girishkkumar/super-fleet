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

import com.supertrans.dto.GarageDTO;
import com.supertrans.entity.Garage;
import com.supertrans.entity.User;
import com.supertrans.exception.InvalidGarageException;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.repository.GarageRepository;
import com.supertrans.repository.UserRepository;
import com.supertrans.service.IGarageService;
import com.supertrans.service.IPageService;
import com.supertrans.util.SuperFleetUtil;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class GarageServiceImpl implements IGarageService<GarageDTO>, IPageService<Garage> {

	@Autowired
	private GarageRepository garageRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<Garage> findAll(Pageable pageable) {
		Page<Garage> garagesList = garageRepository.findAll(pageable);
		return garagesList;
	}

	@Override
	public List<GarageDTO> findAll() {
		Collection<Garage> garages = (Collection<Garage>) garageRepository.findAll();
		List<Garage> garagesList = garages.stream().collect(toList());
		List<GarageDTO> garageDTOList = SuperFleetUtil.GarageEntitiesToDTO.apply(garagesList);
		Collection<GarageDTO> garagesCollection = garageDTOList;
		return garagesCollection.stream().collect(toList());
	}

	@Override
	public GarageDTO findById(Long id) {
		Optional<Garage> garageOpt = garageRepository.findById(id);
		if (garageOpt.isPresent()) {
			Garage garage = garageOpt.get();
			GarageDTO garageDTO = SuperFleetUtil.GarageEntityToDTO.apply(garage);
			return garageDTO;
		} else {
			throw new InvalidGarageException("Garage doesn't exist");
		}
	}

	@Override
	public GarageDTO saveOrUpdate(GarageDTO garageDTO) {
		Garage garage = null;
		if (garageDTO.getId() != null) {
			Optional<Garage> garageOpt = garageRepository.findById(garageDTO.getId());
			if (garageOpt.isPresent()) {
				garage = garageOpt.get();
				garage.setAddress(garageDTO.getAddress());
				garage.setContactNo(garageDTO.getContactNo());
				garage.setEmail(garageDTO.getEmail());
				garage.setLatitude(garageDTO.getLatitude());
				garage.setLongitude(garageDTO.getLongitude());
				garage.setName(garageDTO.getName());

			}
		} else {
			garage = SuperFleetUtil.GarageDTOToEntity.apply(garageDTO);
			User createdBy = userRepository.findByEmail(garageDTO.getCreatedBy().getEmail());
			if (createdBy != null) {
				garage.setCreatedBy(createdBy);
			} else {
				throw new InvalidUserException("User doesnt exist");
			}
		}
		garage = garageRepository.save(garage);
		garageDTO = SuperFleetUtil.GarageEntityToDTO.apply(garage);
		return garageDTO;
	}

	@Override
	public String deleteById(Long id) {
		JSONObject jsonObject = new JSONObject();
		try {
			garageRepository.deleteById(id);
			jsonObject.put("message", "Garage deleted successfully");
		} catch (JSONException e) {
			log.error("Exception: {}", e);
		}
		return jsonObject.toString();
	}

	@Override
	public GarageDTO findNearestGarage(Double latitude, Double longitude) {

		List<Object[]> objList = garageRepository.findNearestGarageByLatAndLong(latitude, longitude);
		if (objList != null) {
			GarageDTO garageDTO = new GarageDTO();
			for (Object[] objArr : objList) {
				Long id = Long.parseLong(String.valueOf(objArr[0]));
				garageDTO.setId(id);
				String name = (String) objArr[1];
				garageDTO.setName(name);
				Double lat = Double.parseDouble(String.valueOf(objArr[2]));
				garageDTO.setLatitude(lat.toString());
				Double lng = Double.parseDouble(String.valueOf(objArr[3]));
				garageDTO.setLongitude(lng.toString());
				String address = (String) objArr[4];
				garageDTO.setAddress(address);
				String email = (String) objArr[5];
				garageDTO.setEmail(email);
				String contactNo = (String) objArr[6];
				garageDTO.setContactNo(contactNo);
				Double distance = Double.parseDouble(String.valueOf(objArr[7]));
				log.info("id: " + id + ", distance : " + distance + ", name: " + name);
				return garageDTO;
			}

		} else {
			throw new InvalidGarageException("Nearby garage doesnt exist within the range of 100 miles");
		}
		return null;
	}

}

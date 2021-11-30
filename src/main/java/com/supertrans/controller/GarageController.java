package com.supertrans.controller;

import java.util.NoSuchElementException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supertrans.dto.GarageDTO;
import com.supertrans.dto.Response;
import com.supertrans.exception.InvalidGarageException;
import com.supertrans.exception.InvalidTokenException;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.service.IGarageService;
import com.supertrans.service.IPageService;

import lombok.extern.log4j.Log4j2;

@RestController
//@RequestMapping("/garage")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class GarageController {

	@Autowired
	private IGarageService<GarageDTO> garageService;

	@Autowired
	private IPageService<GarageDTO> garagePageService;

	@PostMapping(value = "/garage", consumes = "application/json")
	public Response createGarage(@RequestBody GarageDTO garageDTO) {
		log.info("Create Garage API invoked: " + garageDTO.toString());
		Response response = Response.builder().build();
		try {
			garageDTO = garageService.saveOrUpdate(garageDTO);
			response.setData(garageDTO);
			response.setMessage("Success");
			response.setStatusCode("S-001");

		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
		}
		return response;
	}

	@PutMapping(value = "/garage", consumes = "application/json")
	public Response updateGarage(@RequestBody GarageDTO garageDTO) {
		log.info("Update Garage API invoked: " + garageDTO.toString());
		Response response = Response.builder().build();
		try {
			garageDTO = garageService.saveOrUpdate(garageDTO);
			response.setData(garageDTO);
			response.setMessage("Success");
			response.setStatusCode("S-001");

		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
		}
		return response;
	}

	@GetMapping(value = "/garage/{garageId}", consumes = "application/json")
	public Response fetchGarage(@PathVariable String garageId) {
		long starttime = System.currentTimeMillis();
		log.info("Get Garage by id: " + garageId + ", start time: " + starttime);
		Response response = Response.builder().build();
		try {
			GarageDTO garage = garageService.findById(Long.parseLong(garageId));
			response.setData(garage);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidGarageException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IVE-001");
		} catch (NoSuchElementException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("NSE-001");
		}
		long endtime = System.currentTimeMillis();
		long timeTaken = endtime - starttime;
		log.info("Time taken in milliseconds: " + timeTaken);
		long seconds = (timeTaken / 1000) % 60;
		log.info("Get Garage by id: " + garageId + ", took: " + seconds + " seconds");
		return response;
	}

	@DeleteMapping(value = "/garage/{garageId}")
	public Response deleteGarage(@PathVariable String garageId) {
		log.info("Delete garage by garageId: " + garageId);
		Response response = Response.builder().build();

		try {
			String jsonMsg = garageService.deleteById(Long.parseLong(garageId));
			JSONObject jsonObj = null;
			try {
				jsonObj = new JSONObject(jsonMsg);
				response.setData(jsonObj.get("message"));
				response.setMessage("Success");
				response.setStatusCode("S-001");
			} catch (JSONException e) {
				response.setMessage(e.getMessage());
				response.setStatusCode("JSE-001");
			}
		} catch (NoSuchElementException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("NSE-001");
		}

		return response;
	}

	@GetMapping(value = "/garages")
	public Response fetchAllGarages(@RequestParam Integer pageNumber, @RequestParam Integer pageSize,
			@RequestParam String sortBy, @RequestParam String sortDir) {
		Response response = Response.builder().build();
		try {
			Page<GarageDTO> garagesList = garagePageService.findAll(PageRequest.of(pageNumber, pageSize,
					sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()));
			response.setData(garagesList);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidGarageException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IVE-001");
		} catch (NoSuchElementException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("NSE-001");
		} catch (InvalidTokenException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("ITE-001");
		}
		return response;
	}

	@GetMapping(value = "/garage/nearby")
	public Response fetchNearestGarage(@RequestParam Double latitude, @RequestParam Double longitude) {
		log.info("Get nearest Garage by lat: " + latitude + ", long: " + longitude);
		Response response = Response.builder().build();
		try {
			GarageDTO garage = garageService.findNearestGarage(latitude, longitude);
			response.setData(garage);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidGarageException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IVE-001");
		} catch (NoSuchElementException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("NSE-001");
		}
		return response;
	}

}

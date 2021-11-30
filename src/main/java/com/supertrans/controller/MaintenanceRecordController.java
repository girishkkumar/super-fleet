package com.supertrans.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supertrans.dto.MaintenanceRecordDTO;
import com.supertrans.dto.Response;
import com.supertrans.exception.InvalidMaintenanceRecordException;
import com.supertrans.exception.InvalidTokenException;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.service.IMaintenanceRecordService;
import com.supertrans.util.SuperFleetConstants;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/maintenance")
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class MaintenanceRecordController {

	@Autowired
	private IMaintenanceRecordService<MaintenanceRecordDTO> maintenanceRecordService;

	@PostMapping(consumes = "application/json")
	public Response createMaintenanceRecord(@RequestBody MaintenanceRecordDTO maintenanceRecordDTO) {
		log.info("Create MaintenanceRecord API invoked: " + maintenanceRecordDTO.toString());
		Response response = Response.builder().build();
		try {
			maintenanceRecordDTO = maintenanceRecordService.saveOrUpdate(maintenanceRecordDTO);
			response.setData(maintenanceRecordDTO);
			response.setMessage("Success");
			response.setStatusCode("S-001");

		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
		}
		return response;
	}

	@PutMapping(consumes = "application/json")
	public Response updateMaintenanceRecord(@RequestBody MaintenanceRecordDTO maintenanceRecordDTO) {
		log.info("Update MaintenanceRecord API invoked: " + maintenanceRecordDTO.toString());
		Response response = Response.builder().build();
		try {
			maintenanceRecordDTO = maintenanceRecordService.saveOrUpdate(maintenanceRecordDTO);
			response.setData(maintenanceRecordDTO);
			response.setMessage("Success");
			response.setStatusCode("S-001");

		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
		}
		return response;
	}

	@GetMapping(value = "/{mrId}")
	public Response fetchMaintenanceRecord(@PathVariable String mrId) {
		log.info("Get MaintenanceRecord by id: " + mrId);
		Response response = Response.builder().build();
		try {
			MaintenanceRecordDTO record = maintenanceRecordService.findById(Long.parseLong(mrId));
			response.setData(record);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidMaintenanceRecordException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IVE-001");
		} catch (NoSuchElementException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("NSE-001");
		}
		return response;
	}

	@DeleteMapping(value = "/{mrId}")
	public Response deleteMaintenanceRecord(@PathVariable String mrId) {
		log.info("Delete MaintenanceRecord by Id: " + mrId);
		Response response = Response.builder().build();

		try {
			String jsonMsg = maintenanceRecordService.deleteById(Long.parseLong(mrId));
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

	@GetMapping(value = "/vehicle/{vehicleId}")
	public Response fetchAllMaintenanceRecordsByVehicleId(@PathVariable String vehicleId,
			@RequestHeader(name = SuperFleetConstants.AUTH) String AuthToken) {
		Response response = Response.builder().build();
		try {
			List<MaintenanceRecordDTO> recordsList = maintenanceRecordService
					.findAllByVehicleId(Long.parseLong(vehicleId));
			response.setData(recordsList);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidMaintenanceRecordException ex) {
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

}

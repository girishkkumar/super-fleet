package com.supertrans.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supertrans.dto.Response;
import com.supertrans.dto.VehicleDTO;
import com.supertrans.entity.User;
import com.supertrans.entity.Vehicle;
import com.supertrans.exception.InvalidTokenException;
import com.supertrans.exception.InvalidUserException;
import com.supertrans.exception.InvalidVehicleException;
import com.supertrans.service.IUserService;
import com.supertrans.service.IVehicleService;
import com.supertrans.util.SuperFleetConstants;

import lombok.extern.log4j.Log4j2;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@Log4j2
public class VehicleController {

	@Autowired
	private IVehicleService<VehicleDTO> vehicleService;

	@Autowired
	private IUserService<User> userService;

	@PostMapping(value = "/vehicle")
	public Response createVehicle(@RequestBody VehicleDTO vehicleDTO) {
		log.info("Create Vehicle API invoked: " + vehicleDTO.toString());
		Response response = Response.builder().build();
		try {
			vehicleDTO = vehicleService.saveOrUpdate(vehicleDTO);
			response.setData(vehicleDTO);
			response.setMessage("Success");
			response.setStatusCode("S-001");

		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
		}
		return response;
	}

	@PutMapping(value = "/vehicle")
	public Response updateVehicle(@RequestBody VehicleDTO vehicleDTO) {
		log.info("Update Vehicle API invoked: " + vehicleDTO.toString());
		Response response = Response.builder().build();
		try {
			vehicleDTO = vehicleService.saveOrUpdate(vehicleDTO);
			response.setData(vehicleDTO);
			response.setMessage("Success");
			response.setStatusCode("S-001");

		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
		}
		return response;
	}

	@GetMapping(value = "/vehicle/{vehicleId}")
	public Response fetchVehicle(@PathVariable String vehicleId) {
		log.info("Get Vehicle by id: " + vehicleId);
		Response response = Response.builder().build();
		try {
			VehicleDTO vehicle = vehicleService.findById(Long.parseLong(vehicleId));
			response.setData(vehicle);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidVehicleException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IVE-001");
		} catch (NoSuchElementException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("NSE-001");
		}
		return response;
	}

	@DeleteMapping(value = "/vehicle/{vehicleId}")
	public Response deleteVehicle(@PathVariable String vehicleId) {
		log.info("Delete vehicle by vehicleId: " + vehicleId);
		Response response = Response.builder().build();

		try {
			String jsonMsg = vehicleService.deleteById(Long.parseLong(vehicleId));
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

//	@GetMapping(value = "/vehicles", consumes = "application/json")
//	public Response fetchAllVehicles(@RequestHeader(name = SuperFleetConstants.AUTH) String AuthToken) {
//		Response response = Response.builder().build();
//		try {
//			User user = userService.findByToken(AuthToken);
//			List<VehicleDTO> vehiclesList = vehicleService.findAllByUser(user);
//			response.setData(vehiclesList);
//			response.setMessage("Success");
//			response.setStatusCode("S-001");
//		} catch (InvalidVehicleException ex) {
//			response.setMessage(ex.getMessage());
//			response.setStatusCode("IVE-001");
//		} catch (NoSuchElementException ex) {
//			response.setMessage(ex.getMessage());
//			response.setStatusCode("NSE-001");
//		} catch (InvalidTokenException ex) {
//			response.setMessage(ex.getMessage());
//			response.setStatusCode("ITE-001");
//		}
//		return response;
//	}

	@GetMapping(value = "/vehicles")
	public Response fetchAllVehicles(@RequestHeader(name = SuperFleetConstants.AUTH) String AuthToken,
			@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String sortBy,
			@RequestParam String sortDir) {
		Response response = Response.builder().build();
		try {
			User user = userService.findByToken(AuthToken);
			Page<Vehicle> vehiclesList = vehicleService.findAll(user, PageRequest.of(pageNumber, pageSize,
					sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()));
			response.setData(vehiclesList);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidVehicleException ex) {
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

	@GetMapping(value = "/vehicles/odometer")
	public Response fetchAllVehiclesByOdoReading(@RequestHeader(name = SuperFleetConstants.AUTH) String AuthToken) {
		Response response = Response.builder().build();
		try {
			User user = userService.findByToken(AuthToken);
			List<VehicleDTO> vehiclesList = vehicleService.fetchAvgOdometerReadingByYear(user);
			response.setData(vehiclesList);
			response.setMessage("Success");
			response.setStatusCode("S-001");
		} catch (InvalidVehicleException ex) {
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

	@GetMapping(value = "/vehicles/aggregate")
	public Response fetchAllVehiclesOfCustomersByYear(@RequestHeader(name = SuperFleetConstants.AUTH) String AuthToken,
			@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String sortBy,
			@RequestParam String sortDir) {
		Response response = Response.builder().build();
		try {
			User user = userService.findByToken(AuthToken);
			String role = user.getRole().getName();
			if (role.equalsIgnoreCase(SuperFleetConstants.USER)) {
				response.setMessage("Access Denied");
				response.setStatusCode("AD-001");
			} else if (role.equalsIgnoreCase(SuperFleetConstants.ADMIN)) {
				List<VehicleDTO> vehiclesList = vehicleService.fetchAllVehiclesOfCustomersByYear(PageRequest.of(pageNumber, pageSize,
						sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()));
				response.setData(vehiclesList);
				response.setMessage("Success");
				response.setStatusCode("S-001");
			}
		} catch (InvalidUserException ex) {
			response.setMessage(ex.getMessage());
			response.setStatusCode("IUE-001");
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

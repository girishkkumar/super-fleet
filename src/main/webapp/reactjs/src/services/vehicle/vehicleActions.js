import * as VT from "./vehicleTypes";
import axios from "axios";

export const saveVehicle = (vehicle) => {
  return (dispatch) => {
    dispatch({
      type: VT.SAVE_BUS_REQUEST,
    });
    axios
      .post("http://localhost:8085/fleet-api/vehicle", vehicle)
      .then((response) => {
        dispatch(vehicleSuccess(response.data));
      })
      .catch((error) => {
        dispatch(vehicleFailure(error));
      });
  };
};

export const fetchVehicle = (vehicleId) => {
  return (dispatch) => {
    dispatch({
      type: VT.FETCH_BUS_REQUEST,
    });
    axios
      .get("http://localhost:8085/fleet-api/vehicle/" + vehicleId)
      .then((response) => {
        dispatch(vehicleSuccess(response.data));
      })
      .catch((error) => {
        dispatch(vehicleFailure(error));
      });
  };
};

export const updateVehicle = (vehicle) => {
  return (dispatch) => {
    dispatch({
      type: VT.UPDATE_BUS_REQUEST,
    });
    axios
      .put("http://localhost:8085/fleet-api/vehicle", vehicle)
      .then((response) => {
        dispatch(vehicleSuccess(response.data));
      })
      .catch((error) => {
        dispatch(vehicleFailure(error));
      });
  };
};

export const deleteVehicle = (vehicleId) => {
  return (dispatch) => {
    dispatch({
      type: VT.DELETE_BUS_REQUEST,
    });
    axios
      .delete("http://localhost:8085/fleet-api/vehicle/" + vehicleId)
      .then((response) => {
        dispatch(vehicleSuccess(response.data));
      })
      .catch((error) => {
        dispatch(vehicleFailure(error));
      });
  };
};

const vehicleSuccess = (vehicle) => {
  return {
    type: VT.BUS_SUCCESS,
    payload: vehicle,
  };
};

const vehicleFailure = (error) => {
  return {
    type: VT.BUS_FAILURE,
    payload: error,
  };
};

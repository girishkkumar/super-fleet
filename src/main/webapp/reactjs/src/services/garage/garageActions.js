import * as GT from "./garageTypes";
import axios from "axios";

export const saveGarage = (garage) => {
  return (dispatch) => {
    dispatch({
      type: GT.SAVE_GARAGE_REQUEST,
    });
    axios
      .post("http://localhost:8085/fleet-api/garage", garage)
      .then((response) => {
        dispatch(garageSuccess(response.data));
      })
      .catch((error) => {
        dispatch(garageFailure(error));
      });
  };
};

export const fetchGarage = (garageId) => {
  return (dispatch) => {
    dispatch({
      type: GT.FETCH_GARAGE_REQUEST,
    });
    axios
      .get("http://localhost:8085/fleet-api/garage/" + garageId)
      .then((response) => {
        dispatch(garageSuccess(response.data));
      })
      .catch((error) => {
        dispatch(garageFailure(error));
      });
  };
};

export const updateGarage = (garage) => {
  return (dispatch) => {
    dispatch({
      type: GT.UPDATE_GARAGE_REQUEST,
    });
    axios
      .put("http://localhost:8085/fleet-api/garage", garage)
      .then((response) => {
        dispatch(garageSuccess(response.data));
      })
      .catch((error) => {
        dispatch(garageFailure(error));
      });
  };
};

export const deleteGarage = (garageId) => {
  return (dispatch) => {
    dispatch({
      type: GT.DELETE_GARAGE_REQUEST,
    });
    axios
      .delete("http://localhost:8085/fleet-api/garage/" + garageId)
      .then((response) => {
        dispatch(garageSuccess(response.data));
      })
      .catch((error) => {
        dispatch(garageFailure(error));
      });
  };
};

const garageSuccess = (garage) => {
  return {
    type: GT.GARAGE_SUCCESS,
    payload: garage,
  };
};

const garageFailure = (error) => {
  return {
    type: GT.GARAGE_FAILURE,
    payload: error,
  };
};

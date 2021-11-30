import { combineReducers } from "redux";
import userReducer from "./user/userReducer";
import authReducer from "./user/auth/authReducer";
import vehicleReducer from "./vehicle/vehicleReducer";
import garageReducer from "./garage/garageReducer";

const rootReducer = combineReducers({
  user: userReducer,
  vehicle: vehicleReducer,
  garage: garageReducer,
  auth: authReducer,
});

export default rootReducer;
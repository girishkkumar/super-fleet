import * as VT from "./vehicleTypes";

const initialState = {
  vehicle: "",
  error: "",
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case VT.SAVE_BUS_REQUEST:
    case VT.FETCH_BUS_REQUEST:
    case VT.UPDATE_BUS_REQUEST:
    case VT.DELETE_BUS_REQUEST:
      return {
        ...state,
      };
    case VT.BUS_SUCCESS:
      return {
        vehicle: action.payload,
        error: "",
      };
    case VT.BUS_FAILURE:
      return {
        vehicle: "",
        error: action.payload,
      };
    default:
      return state;
  }
};

export default reducer;

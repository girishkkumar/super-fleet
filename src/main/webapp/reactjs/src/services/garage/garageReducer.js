import * as GT from "./garageTypes";

const initialState = {
  garage: "",
  error: "",
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case GT.SAVE_GARAGE_REQUEST:
    case GT.FETCH_GARAGE_REQUEST:
    case GT.UPDATE_GARAGE_REQUEST:
    case GT.DELETE_GARAGE_REQUEST:
      return {
        ...state,
      };
    case GT.GARAGE_SUCCESS:
      return {
        garage: action.payload,
        error: "",
      };
    case GT.GARAGE_FAILURE:
      return {
        garage: "",
        error: action.payload,
      };
    default:
      return state;
  }
};

export default reducer;
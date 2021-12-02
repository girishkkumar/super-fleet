import React, { Component } from "react";

import { connect } from "react-redux";
import {
  saveVehicle,
  fetchVehicle,
  updateVehicle,
} from "../../services/index";

import { Card, Form, Button, Col, InputGroup, Row } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSave,
  faPlusSquare,
  faUndo,
  faEdit,
} from "@fortawesome/free-solid-svg-icons";
import MyToast from "../MyToast";

class Vehicle extends Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.state = {
      show: false,
    };
  }

  initialState = {id:"", vehicleNo:"", year:"", noOfWheels:"",airConditioned:"",odometerReading:"",capacity:"", status:"", nextMaintenanceDate:""};

  componentDidMount() {
    const vehicleId = +this.props.match.params.id;
    if (vehicleId) {
      this.findVehicleById(vehicleId);
    }
  }

  findVehicleById = (vehicleId) => {
    this.props.fetchVehicle(vehicleId);
    setTimeout(() => {
      let vehicle = this.props.vehicleObject.vehicle.data;
      if (vehicle != null) {
        this.setState({
          id: vehicle.id,
          vehicleNo:vehicle.vehicleNo, 
          year:vehicle.year, 
          noOfWheels:vehicle.noOfWheels,
          airConditioned:vehicle.airConditioned,
          odometerReading:vehicle.odometerReading,
          capacity:vehicle.capacity, 
          status:vehicle.status, 
          nextMaintenanceDate:vehicle.nextMaintenanceDate
          
        });
      }
    }, 1000);
  };

  resetVehicle = () => {
    this.setState(() => this.initialState);
  };

  submitVehicle = (event) => {
    event.preventDefault();

    const currentUser = {
        email: localStorage.getItem("email")
    };

    const vehicle = {
    		vehicleNo:this.state.vehicleNo, 
            year:this.state.year, 
            noOfWheels:this.state.noOfWheels,
            airConditioned:this.state.airConditioned,
            odometerReading:this.state.odometerReading,
            capacity:this.state.capacity, 
            status:this.state.status, 
            nextMaintenanceDate:this.state.nextMaintenanceDate,
            createdBy: currentUser,
    };

    this.props.saveVehicle(vehicle);
    setTimeout(() => {
      if (this.props.vehicleObject.vehicle != null) {
        this.setState({ show: true, method: "post" });
        setTimeout(() => this.setState({ show: false }), 3000);
      } else {
        this.setState({ show: false });
      }
    }, 2000);
    this.setState(this.initialState);
  };

  updateVehicle = (event) => {
    event.preventDefault();

    const vehicle = {
      id: this.state.id,
      vehicleNo:this.state.vehicleNo, 
      year:this.state.year, 
      noOfWheels:this.state.noOfWheels,
      airConditioned:this.state.airConditioned,
      odometerReading:this.state.odometerReading,
      capacity:this.state.capacity, 
      status:this.state.status, 
      nextMaintenanceDate:this.state.nextMaintenanceDate,
    };
    this.props.updateVehicle(vehicle);
    setTimeout(() => {
      if (this.props.vehicleObject.vehicle != null) {
        this.setState({ show: true, method: "put" });
        setTimeout(() => this.setState({ show: false }), 3000);
      } else {
        this.setState({ show: false });
      }
    }, 2000);
    this.setState(this.initialState);
  };

  vehicleChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  onCapacityChange(e){
    this.setState({ capacity: e.target.value });
  }

  onStatusChange(e){
    this.setState({ status: e.target.value });
  }

  onACChange(e){
    this.setState({ airConditioned: e.target.value });
  }

  vehicleList = () => {
    return this.props.history.push("/vehicles");
  };

  render() {
    const { vehicleNo, year, noOfWheels,airConditioned,odometerReading,capacity, status, nextMaintenanceDate} =
      this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={
              this.state.method === "put"
                ? "Bus Updated Successfully."
                : "Bus Saved Successfully."
            }
            type={"success"}
          />
        </div>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <FontAwesomeIcon icon={this.state.id ? faEdit : faPlusSquare} />{" "}
            {this.state.id ? "Update Bus" : "Add New Bus"}
          </Card.Header>
          <Form
            onReset={this.resetVehicle}
            onSubmit={this.state.id ? this.updateVehicle : this.submitVehicle}
            id="vehicleFormId"
          >
            <Card.Body>
              <Row className="show-grid">
                <Form.Group as={Col} controlId="formGridVehicleNo">
                  <Form.Label>Vehicle No</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="vehicleNo"
                    value={vehicleNo}
                    onChange={this.vehicleChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Vehicle No"
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="formGridYear">
                  <Form.Label>Year</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="year"
                    value={year}
                    onChange={this.vehicleChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Year"
                  />
                </Form.Group>
              </Row>
              <Row className="show-grid">
                <Form.Group as={Col} controlId="formGridNoOfWheels">
                  <Form.Label>No of Wheels</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      autoComplete="off"
                      type="text"
                      name="noOfWheels"
                      value={noOfWheels}
                      onChange={this.vehicleChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter No of wheels"
                    />
                  </InputGroup>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridAirConditioned ">
                    <Form.Label>Air Conditioned</Form.Label>
                    <Form.Control
                    as="select"
                    value={airConditioned}
                    onChange={this.onACChange.bind(this)}
                    className="bg-dark text-white">
                    <option>SELECT</option>
                    <option value="true">Yes</option>
                    <option value="false">No</option>
                    </Form.Control>
                </Form.Group>
	
              </Row>
              <Row className="show-grid">
                <Form.Group as={Col} controlId="formGridOdometerReading">
                  <Form.Label>Odometer Reading</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="odometerReading"
                    value={odometerReading}
                    onChange={this.vehicleChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Odometer Reading"
                  />
                </Form.Group>

                <Form.Group as={Col} controlId="formGridCapacity ">
                    <Form.Label>Capacity</Form.Label>
                    <Form.Control
                    as="select"
                    value={capacity}
                    onChange={this.onCapacityChange.bind(this)}
                    className="bg-dark text-white">
                    <option>SELECT</option>
                    <option value="24">24</option>
                    <option value="36">36</option>
                    </Form.Control>
                </Form.Group>
              </Row>
              <Row className="show-grid">
              
              <Form.Group as={Col} controlId="formGridMaintenanceDate">
                  <Form.Label>Next Maintenance On</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="nextMaintenanceDate"
                    value={nextMaintenanceDate}
                    onChange={this.vehicleChange}
                    className={"bg-dark text-white"}
                    placeholder="Date Pattern - YYYY-MM-DD"
                  />
                </Form.Group>

              <Form.Group as={Col} controlId="formGridStatus">
                    <Form.Label>Status</Form.Label>
                    <Form.Control
                    as="select"
                    value={status}
                    onChange={this.onStatusChange.bind(this)}
                    className="bg-dark text-white">
                    <option>SELECT</option>
                    <option value="SCHEDULED_FOR_MAINTENANCE">Scheduled for maintenance</option>
                    <option value="UNDERGOING_REPAIRS">Undergoing repairs</option>
					<option value="READY_FOR_USE">Ready for use</option>
                    </Form.Control>
                </Form.Group>
		          
		          
		          
              </Row>
            </Card.Body>
            <Card.Footer style={{ textAlign: "right" }}>
              <Button size="sm" variant="success" type="submit">
                <FontAwesomeIcon icon={faSave} />{" "}
                {this.state.id ? "Update" : "Save"}
              </Button>{" "}
              <Button size="sm" variant="info" type="reset">
                <FontAwesomeIcon icon={faUndo} /> Reset
              </Button>{" "}
              <Button
                size="sm"
                variant="info"
                type="button"
                onClick={() => this.vehicleList()}
              >Bus List</Button>
            </Card.Footer>
          </Form>
        </Card>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    vehicleObject: state.vehicle,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
	  saveVehicle: (vehicle) => dispatch(saveVehicle(vehicle)),
	  fetchVehicle: (vehicleId) => dispatch(fetchVehicle(vehicleId)),
	  updateVehicle: (vehicle) => dispatch(updateVehicle(vehicle)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Vehicle);

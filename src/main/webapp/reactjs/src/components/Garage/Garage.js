import React, { Component } from "react";

import { connect } from "react-redux";
import {
  saveGarage,
  fetchGarage,
  updateGarage,
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

class Garage extends Component {
  constructor(props) {
    super(props);
    this.state = this.initialState;
    this.state = {
      show: false,
    };
  }

  initialState = {id:"", name:"", latitude:"", longitude:"",email:"",address:"",contactNo:"", nearestGarage:""};

  componentDidMount() {
    const garageId = +this.props.match.params.id;
    if (garageId) {
      this.findGarageById(garageId);
    }
  }

  findGarageById = (garageId) => {
    this.props.fetchGarage(garageId);
    setTimeout(() => {
      let garage = this.props.garageObject.garage;
      if (garage != null) {
        this.setState({
            id:garage.id, 
            name:garage.name, 
            latitude:garage.latitude, 
            longitude:garage.longitude,
            email:garage.email,
            address:garage.address,
            contactNo: garage.contactNo, 
            nearestGarage:garage.nearestGarage          
        });
      }
    }, 1000);
  };

  resetGarage = () => {
    this.setState(() => this.initialState);
  };

  submitGarage = (event) => {
    event.preventDefault();

    const currentUser = {
        email: localStorage.getItem("email")
    };

    const garage = {
        name:this.state.name, 
        latitude:this.state.latitude, 
        longitude:this.state.longitude,
        email:this.state.email,
        address:this.state.address,
        contactNo: this.state.contactNo, 
        nearestGarage:this.state.nearestGarage ,
        createdBy: currentUser
    };

    this.props.saveGarage(garage);
    setTimeout(() => {
      if (this.props.garageObject.garage != null) {
        this.setState({ show: true, method: "post" });
        setTimeout(() => this.setState({ show: false }), 3000);
      } else {
        this.setState({ show: false });
      }
    }, 2000);
    this.setState(this.initialState);
  };

  updateGarage = (event) => {
    event.preventDefault();
    const garage = {
        id: this.state.id,
        name:this.state.name, 
        latitude:this.state.latitude, 
        longitude:this.state.longitude,
        email:this.state.email,
        address:this.state.address,
        contactNo: this.state.contactNo, 
        nearestGarage:this.state.nearestGarage ,
    };



    this.props.updateGarage(garage);
    setTimeout(() => {
      if (this.props.garageObject.garage != null) {
        this.setState({ show: true, method: "put" });
        setTimeout(() => this.setState({ show: false }), 3000);
      } else {
        this.setState({ show: false });
      }
    }, 2000);
    this.setState(this.initialState);
  };

  garageChange = (event) => {
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

  garageList = () => {
    return this.props.history.push("/list");
  };

  render() {
    const { name, latitude, longitude,email,address,contactNo, nearestGarage} =
      this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={
              this.state.method === "put"
                ? "Garage Updated Successfully."
                : "Garage Saved Successfully."
            }
            type={"success"}
          />
        </div>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <FontAwesomeIcon icon={this.state.id ? faEdit : faPlusSquare} />{" "}
            {this.state.id ? "Update Garage" : "Add New Garage"}
          </Card.Header>
          <Form
            onReset={this.resetGarage}
            onSubmit={this.state.id ? this.updateGarage : this.submitGarage}
            id="garageFormId"
          >
            <Card.Body>
              <Row className="show-grid">
                <Form.Group as={Col} controlId="formGridGarageName">
                  <Form.Label>Name</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="name"
                    value={name}
                    onChange={this.garageChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Garage Name"
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="formGridLatitude">
                  <Form.Label>Latitude</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="latitude"
                    value={latitude}
                    onChange={this.garageChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Latitude"
                  />
                </Form.Group>
              </Row>
              <Row className="show-grid">
                <Form.Group as={Col} controlId="formGridLongitude">
                  <Form.Label>Longitude</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      autoComplete="off"
                      type="text"
                      name="longitude"
                      value={longitude}
                      onChange={this.garageChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Longitude"
                    />
                  </InputGroup>
                </Form.Group>

                <Form.Group as={Col} controlId="formGridEmail">
                  <Form.Label>Email</Form.Label>
                  <InputGroup>
                    <Form.Control
                      required
                      autoComplete="off"
                      type="text"
                      name="email"
                      value={email}
                      onChange={this.garageChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Email"
                    />
                  </InputGroup>
                </Form.Group>
	
              </Row>
              <Row className="show-grid">
                <Form.Group as={Col} controlId="formGridAddress">
                  <Form.Label>Address</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="address"
                    value={address}
                    onChange={this.garageChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Address"
                  />
                </Form.Group>
                <Form.Group as={Col} controlId="formGridContactNo">
                  <Form.Label>Contact No</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="contactNo"
                    value={contactNo}
                    onChange={this.garageChange}
                    className={"bg-dark text-white"}
                    placeholder="Enter Contact No"
                  />
                </Form.Group>
                
              </Row>
              <Row className="show-grid">
              <Form.Group as={Col} controlId="formGridNearestGarage">
                  <Form.Label>Nearest Garage</Form.Label>
                  <Form.Control
                    required
                    autoComplete="off"
                    type="text"
                    name="nearestGarage"
                    value={nearestGarage}
                    onChange={this.vehicleChange}
                    className={"bg-dark text-white"}
                    placeholder="Nearest Garage"
                  />
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
            </Card.Footer>
          </Form>
        </Card>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    garageObject: state.garage,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
	  saveGarage: (garage) => dispatch(saveGarage(garage)),
	  fetchGarage: (garageId) => dispatch(fetchGarage(garageId)),
	  updateGarage: (garage) => dispatch(updateGarage(garage)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Garage);

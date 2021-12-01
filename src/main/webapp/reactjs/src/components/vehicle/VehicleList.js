import React, { Component } from "react";

import { connect } from "react-redux";
import { deleteVehicle } from "../../services/index";

import "./../../assets/css/Style.css";
import {
  Card,
  Table,
  Image,
  ButtonGroup,
  Button,
  InputGroup,
  FormControl,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faList,
  faEdit,
  faTrash,
  faStepBackward,
  faFastBackward,
  faStepForward,
  faFastForward,
} from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import MyToast from "../MyToast";
import axios from "axios";

class VehicleList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      vehicles: [],
      search: "",
      currentPage: 1,
      vehiclesPerPage: 5,
      sortDir: "asc",
    };
  }

  sortData = () => {
    setTimeout(() => {
      this.state.sortDir === "asc"
        ? this.setState({ sortDir: "desc" })
        : this.setState({ sortDir: "asc" });
      this.findAllVehicles(this.state.currentPage);
    }, 500);
  };

  componentDidMount() {
    this.findAllVehicles(this.state.currentPage);
  }

  findAllVehicles(currentPage) {
    currentPage -= 1;
    axios
      .get(
        "http://localhost:8085/fleet-api/vehicles?pageNumber=" +
          currentPage +
          "&pageSize=" +
          this.state.vehiclesPerPage +
          "&sortBy=year&sortDir=" +
          this.state.sortDir
      )
      .then((response) => response.data.data)
      .then((data) => {
        this.setState({
          vehicles: data.content,
          totalPages: data.totalPages,
          totalElements: data.totalElements,
          currentPage: data.number + 1,
        });
      })
      .catch((error) => {
        console.log(error);
        localStorage.removeItem("jwtToken");
        this.props.history.push("/");
      });
  }

  deleteVehicle = (vehicleId) => {
    this.props.deleteVehicle(vehicleId);
    setTimeout(() => {
      if (this.props.vehicleObject != null) {
        this.setState({ show: true });
        setTimeout(() => this.setState({ show: false }), 3000);
        this.findAllVehicles(this.state.currentPage);
      } else {
        this.setState({ show: false });
      }
    }, 1000);
  };

  changePage = (event) => {
    let targetPage = parseInt(event.target.value);
    if (this.state.search) {
      this.searchData(targetPage);
    } else {
      this.findAllVehicles(targetPage);
    }
    this.setState({
      [event.target.name]: targetPage,
    });
  };

  firstPage = () => {
    let firstPage = 1;
    if (this.state.currentPage > firstPage) {
      if (this.state.search) {
        this.searchData(firstPage);
      } else {
        this.findAllVehicles(firstPage);
      }
    }
  };

  prevPage = () => {
    let prevPage = 1;
    if (this.state.currentPage > prevPage) {
      if (this.state.search) {
        this.searchData(this.state.currentPage - prevPage);
      } else {
        this.findAllVehicles(this.state.currentPage - prevPage);
      }
    }
  };

  lastPage = () => {
    let condition = Math.ceil(
      this.state.totalElements / this.state.vehiclesPerPage
    );
    if (this.state.currentPage < condition) {
      if (this.state.search) {
        this.searchData(condition);
      } else {
        this.findAllVehicles(condition);
      }
    }
  };

  nextPage = () => {
    if (
      this.state.currentPage <
      Math.ceil(this.state.totalElements / this.state.vehiclesPerPage)
    ) {
      if (this.state.search) {
        this.searchData(this.state.currentPage + 1);
      } else {
        this.findAllVehicles(this.state.currentPage + 1);
      }
    }
  };

  searchChange = (event) => {
    this.setState({
      [event.target.name]: event.target.value,
    });
  };

  cancelSearch = () => {
    this.setState({ search: "" });
    this.findAllVehicles(this.state.currentPage);
  };

  render() {
    const { vehicles, currentPage, totalPages } = this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={"Bus Deleted Successfully."}
            type={"danger"}
          />
        </div>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <div style={{ float: "left" }}>
              <FontAwesomeIcon icon={faList} /> Bus List
            </div>
            
          </Card.Header>
          <Card.Body>
            <Table bordered hover striped variant="dark">
              <thead>
                <tr>
	                <th>Id</th>
	                <th>Bus No</th>
	                <th>Year</th>
	                <th>No of Wheels</th>
	                <th>Air Conditioned</th>
	                <th>Odometer Reading</th>
	                <th>Capacity</th>
	                <th>Status</th>
	                <th>Next Maintenance Date</th>
                  <th>Resale Value</th>
                </tr>
              </thead>
              <tbody>
                {vehicles.length === 0 ? (
                  <tr align="center">
                    <td colSpan="7">No Buses Available.</td>
                  </tr>
                ) : (
                  vehicles.map((vehicle) => (
                    <tr key={vehicle.id}>
                      <td>
                        <Image
                          src={vehicle.thumbnail}
                          roundedCircle
                          width="25"
                          height="25"
                        />{" "}
                        {vehicle.id}
                      </td>
                      <td>{vehicle.vehicleNo}</td>
                      <td>{vehicle.year}</td>
                      <td>{vehicle.noOfWheels}</td>
                      <td>{vehicle.airConditioned? 'Yes': 'No'}</td>
                      <td>{vehicle.odometerReading}</td>
                      <td>{vehicle.capacity}</td>
                      <td>{vehicle.status}</td>
                      <td>{vehicle.nextMaintenanceDate}</td>
                      <td>
                        <ButtonGroup>
                          <Link
                            to={"vehicle/" + vehicle.id}
                            className="btn btn-sm btn-outline-primary"
                          >
                            <FontAwesomeIcon icon={faEdit} />
                          </Link>{" "}
                          <Button
                            size="sm"
                            variant="outline-danger"
                            onClick={() => this.deleteVehicle(vehicle.id)}
                          >
                            <FontAwesomeIcon icon={faTrash} />
                          </Button>
                        </ButtonGroup>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </Table>
          </Card.Body>
          {vehicles.length > 0 ? (
            <Card.Footer>
              <div style={{ float: "left" }}>
                Showing Page {currentPage} of {totalPages}
              </div>
              <div style={{ float: "right" }}>
                <InputGroup size="sm">
                  <InputGroup.Prepend>
                    <Button
                      type="button"
                      variant="outline-info"
                      disabled={currentPage === 1 ? true : false}
                      onClick={this.firstPage}
                    >
                      <FontAwesomeIcon icon={faFastBackward} /> First
                    </Button>
                    <Button
                      type="button"
                      variant="outline-info"
                      disabled={currentPage === 1 ? true : false}
                      onClick={this.prevPage}
                    >
                      <FontAwesomeIcon icon={faStepBackward} /> Prev
                    </Button>
                  </InputGroup.Prepend>
                  <FormControl
                    className={"page-num bg-dark"}
                    name="currentPage"
                    value={currentPage}
                    onChange={this.changePage}
                  />
                  <InputGroup.Append>
                    <Button
                      type="button"
                      variant="outline-info"
                      disabled={currentPage === totalPages ? true : false}
                      onClick={this.nextPage}
                    >
                      <FontAwesomeIcon icon={faStepForward} /> Next
                    </Button>
                    <Button
                      type="button"
                      variant="outline-info"
                      disabled={currentPage === totalPages ? true : false}
                      onClick={this.lastPage}
                    >
                      <FontAwesomeIcon icon={faFastForward} /> Last
                    </Button>
                  </InputGroup.Append>
                </InputGroup>
              </div>
            </Card.Footer>
          ) : null}
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
    deleteVehicle: (vehicleId) => dispatch(deleteVehicle(vehicleId)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(VehicleList);

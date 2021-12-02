import React, { Component } from "react";

import { connect } from "react-redux";
import { deleteGarage } from "../../services/index";

import "./../../assets/css/Style.css";
import {
  Card,
  Table,
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

class GarageList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      garages: [],
      search: "",
      currentPage: 1,
      garagesPerPage: 5,
      sortDir: "asc",
    };
  }

  sortData = () => {
    setTimeout(() => {
      this.state.sortDir === "asc"
        ? this.setState({ sortDir: "desc" })
        : this.setState({ sortDir: "asc" });
      this.findAllGarages(this.state.currentPage);
    }, 500);
  };

  componentDidMount() {
    this.findAllGarages(this.state.currentPage);
  }

  findAllGarages(currentPage) {
    currentPage -= 1;
    axios
      .get(
        "http://localhost:8085/fleet-api/garages?pageNumber=" +
          currentPage +
          "&pageSize=" +
          this.state.garagesPerPage +
          "&sortBy=name&sortDir=" +
          this.state.sortDir
      )
      .then((response) => response.data.data)
      .then((data) => {
        this.setState({
          garages: data.content,
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

  deleteGarage = (garageId) => {
    this.props.deleteGarage(garageId);
    setTimeout(() => {
      if (this.props.garageObject != null) {
        this.setState({ show: true });
        setTimeout(() => this.setState({ show: false }), 3000);
        this.findAllGarages(this.state.currentPage);
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
      this.findAllGarages(targetPage);
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
        this.findAllGarages(firstPage);
      }
    }
  };

  prevPage = () => {
    let prevPage = 1;
    if (this.state.currentPage > prevPage) {
      if (this.state.search) {
        this.searchData(this.state.currentPage - prevPage);
      } else {
        this.findAllGarages(this.state.currentPage - prevPage);
      }
    }
  };

  lastPage = () => {
    let condition = Math.ceil(
      this.state.totalElements / this.state.garagesPerPage
    );
    if (this.state.currentPage < condition) {
      if (this.state.search) {
        this.searchData(condition);
      } else {
        this.findAllGarages(condition);
      }
    }
  };

  nextPage = () => {
    if (
      this.state.currentPage <
      Math.ceil(this.state.totalElements / this.state.garagesPerPage)
    ) {
      if (this.state.search) {
        this.searchData(this.state.currentPage + 1);
      } else {
        this.findAllGarages(this.state.currentPage + 1);
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
    this.findAllGarages(this.state.currentPage);
  };

  render() {
    const { garages, currentPage, totalPages } = this.state;

    return (
      <div>
        <div style={{ display: this.state.show ? "block" : "none" }}>
          <MyToast
            show={this.state.show}
            message={"Garage Deleted Successfully."}
            type={"danger"}
          />
        </div>
        <Card className={"border border-dark bg-dark text-white"}>
          <Card.Header>
            <div style={{ float: "left" }}>
              <FontAwesomeIcon icon={faList} /> Garages List
            </div>
            
          </Card.Header>
          <Card.Body>
            <Table bordered hover striped variant="dark">
              <thead>
                <tr>
	                <th>Id</th>
	                <th>Name</th>
	                <th>Latitude</th>
	                <th>Longitude</th>
	                <th>Address</th>
	                <th>Contact No</th>
	                <th>Email</th>
	                <th>Nearest Garage</th>
                </tr>
              </thead>
              <tbody>
                {garages.length === 0 ? (
                  <tr align="center">
                    <td colSpan="8">No Garages Available.</td>
                  </tr>
                ) : (
                  garages.map((garage) => (
                    <tr key={garage.id}>
                      <td>{garage.id}</td>
                      <td>{garage.name}</td>
                      <td>{garage.latitude}</td>
                      <td>{garage.longitude}</td>
                      <td>{garage.address}</td>
                      <td>{garage.contactNo}</td>
                      <td>{garage.email}</td>
                      <td>{garage.nearestGarage!=null ? garage.nearestGarage.name:""}</td>
                      <td>
                        <ButtonGroup>
                          <Link
                            to={"garage/" + garage.id}
                            className="btn btn-sm btn-outline-primary"
                          >
                            <FontAwesomeIcon icon={faEdit} />
                          </Link>{" "}
                          <Button
                            size="sm"
                            variant="outline-danger"
                            onClick={() => this.deleteGarage(garage.id)}
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
          {garages.length > 0 ? (
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
    garageObject: state.garage,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    deleteGarage: (garageId) => dispatch(deleteGarage(garageId)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(GarageList);

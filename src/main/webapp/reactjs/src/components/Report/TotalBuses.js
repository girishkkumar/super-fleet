import React, { Component } from "react";

import { connect } from "react-redux";

import "./../../assets/css/Style.css";
import {
  Card,
  Table,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faList,
} from "@fortawesome/free-solid-svg-icons";
import axios from "axios";

class TotalBuses extends Component {
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
          this.fetchAllVehiclesOfCustomersByYear(this.state.currentPage);
        }, 500);
      };
    
      componentDidMount() {
        this.fetchAllVehiclesOfCustomersByYear(this.state.currentPage);
      }

      fetchAllVehiclesOfCustomersByYear(currentPage) {
        currentPage -= 1;
        axios
          .get(
            "http://localhost:8085/fleet-api/vehicles/aggregate?pageNumber=" +
              currentPage +
              "&pageSize=" +
              this.state.vehiclesPerPage +
              "&sortBy=year&sortDir=" +
              this.state.sortDir
          )
          .then((response) => response.data.data)
          .then((data) => {
            this.setState({
              vehicles: data,
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

      /*changePage = (event) => {
        let targetPage = parseInt(event.target.value);
        if (this.state.search) {
          this.searchData(targetPage);
        } else {
          this.fetchAllVehiclesOfCustomersByYear(targetPage);
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
            this.fetchAllVehiclesOfCustomersByYear(firstPage);
          }
        }
      };
    
      prevPage = () => {
        let prevPage = 1;
        if (this.state.currentPage > prevPage) {
          if (this.state.search) {
            this.searchData(this.state.currentPage - prevPage);
          } else {
            this.fetchAllVehiclesOfCustomersByYear(this.state.currentPage - prevPage);
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
            this.fetchAllVehiclesOfCustomersByYear(condition);
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
            this.fetchAllVehiclesOfCustomersByYear(this.state.currentPage + 1);
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
        this.fetchAllVehiclesOfCustomersByYear(this.state.currentPage);
      };*/



      render() {
        const { vehicles } = this.state;
    
        return (
          <div>
            <Card className={"border border-dark bg-dark text-white"}>
              <Card.Header>
                <div style={{ float: "left" }}>
                  <FontAwesomeIcon icon={faList} /> Total Buses owned by all Customers grouped by year.
                </div>
                
              </Card.Header>
              <Card.Body>
                <Table bordered hover striped variant="dark">
                  <thead>
                    <tr>
                        <th>Year</th>
                        <th>Buses Count</th>
                    </tr>
                  </thead>
                  <tbody>
                    {vehicles.length === 0 ? (
                      <tr align="center">
                        <td colSpan="2">No Buses Available.</td>
                      </tr>
                    ) : (
                      vehicles.map((vehicle) => (
                        <tr key={vehicle.year}> 
                          <td>{vehicle.year}</td>
                          <td>{vehicle.vehiclesCount}</td>
                        </tr>
                      ))
                    )}
                  </tbody>
                </Table>
              </Card.Body>
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

export default connect(mapStateToProps) (TotalBuses);
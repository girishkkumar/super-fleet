import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navbar, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
} from "@fortawesome/free-solid-svg-icons";
import { logoutUser } from "../services/index";

const NavigationBar = () => {
    const auth = useSelector((state) => state.auth);
    const dispatch = useDispatch();
  
    const logout = () => {
      dispatch(logoutUser());
    };
  
    const guestLinks = (
      <>
        <div className="ms-auto"></div>
        <Nav className="navbar-right">
          <Link to={"register"} className="nav-link">
            <FontAwesomeIcon icon={faUserPlus} /> Register
          </Link>
          <Link to={"login"} className="nav-link">
            <FontAwesomeIcon icon={faSignInAlt} /> Login
          </Link>
        </Nav>
      </>
    );
    const userLinks = (
      <>
        <Nav className="mr-auto">
          <Link to={auth.role==='USER' ? "vehicle" : ""} className="nav-link">
            {auth.role==='USER' ? "Add Bus" : ""} 
          </Link>
          <Link to={auth.role==='USER' ? "vehicles" : ""} className="nav-link">
            {auth.role==='USER' ? "Bus List" : ""} 
          </Link>

          <Link to={auth.role==='ADMIN' ? "garage" : ""}  className="nav-link">
            {auth.role==='ADMIN' ? "Add Garage" : ""}
          </Link>

          <Link to={auth.role==='ADMIN' ? "garages" : ""}  className="nav-link">
          {auth.role==='ADMIN' ? "Garages List" : ""} 
          </Link>

          <Link to={"reports"} className="nav-link">
            Reports
          </Link>
        </Nav>
        <Nav className="ms-auto">
          <Link to={"/"} className="nav-link" onClick={logout}>
            <FontAwesomeIcon icon={faSignOutAlt} /> Logout
          </Link>
        </Nav>
      </>
    );
  
    return (
      <Navbar bg="dark" variant="dark">
        <Link to={auth.isLoggedIn ? "home" : ""} className="navbar-brand">
          <img src="bus-blue-icon.png" width="25" height="25" alt="Super Transportation Co"/>{" "}
          Super Transportation Co
        </Link>
        {auth.isLoggedIn ? userLinks : guestLinks}
        

      </Navbar>
    );
  };
  
  export default NavigationBar;
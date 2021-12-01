import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navbar, Nav,NavDropdown } from "react-bootstrap";
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
    const isAdmin = auth.role==='ADMIN' ? true : false
  
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
          <Link to={"vehicle"} className="nav-link">
            Add Bus
          </Link>
          <Link to={"vehicles"} className="nav-link">
            Bus List
          </Link>
          <NavDropdown title="Reports" id="basic-nav-dropdown" >
          <NavDropdown.Item><Link to={"statuses"} className="nav-link text-dark">Bus statuses</Link></NavDropdown.Item>
          <NavDropdown.Item><Link to={"OdoReading"} className="nav-link text-dark">Avg Odometer Reading</Link></NavDropdown.Item>
          </NavDropdown>
        </Nav>
        <Nav className="ms-auto">
          <Link to={"/"} className="nav-link" onClick={logout}>
          Hi {auth.username}, &nbsp; &nbsp;
            <FontAwesomeIcon icon={faSignOutAlt} /> Logout
          </Link>
        </Nav>
      </>
    );


    const adminLinks = (
      <>
        <Nav className="mr-auto">
          <Link to={"garage"}  className="nav-link">
            Add Garage
          </Link>

          <Link to={"garages"}  className="nav-link">
          Garages List
          </Link>

          <NavDropdown title="Reports" id="basic-nav-dropdown">
          <NavDropdown.Item><Link to={"totalBuses"} className="nav-link text-dark">Customers Bus Details</Link></NavDropdown.Item>
          </NavDropdown>
        </Nav>

        <Nav className="ms-auto">
          <Link to={"/"} className="nav-link" onClick={logout}>
          Hi {auth.username}, &nbsp; &nbsp;
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
        {auth.isLoggedIn && isAdmin ? adminLinks:""}
        {auth.isLoggedIn && !isAdmin ? userLinks:""} 
        {!auth.isLoggedIn ? guestLinks :""}
        

      </Navbar>
    );
  };
  
  export default NavigationBar;
import React from 'react';
import './App.css';

import { Container, Row, Col} from 'react-bootstrap';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';

import NavigationBar from './components/NavigationBar';
import Welcome from './components/Welcome';
import Footer from './components/Footer';
import Vehicle from './components/vehicle/Vehicle';
import VehicleList from './components/vehicle/VehicleList';
import Register from "./components/User/Register";
import Login from "./components/User/Login";
import Home from "./components/Home";
import Garage from './components/Garage/Garage';
import GarageList from './components/Garage/GarageList';

const App = () => {
  
  window.onbeforeunload = (event) => {
    const e = event || window.event;
    e.preventDefault();
    if (e) {
      e.returnValue = "";
    }
    return "";
  };


  return (
    <Router>
      <NavigationBar/>
        <Container>
          <Row>
            <Col lg={12} className={"margin-top"}>
              <Switch>
                <Route path="/" exact component={Welcome}/>
                <Route path="/home" exact component={Home} />
                <Route path="/vehicle" exact component={Vehicle}/>
                <Route path="/vehicle/:id" exact component={Vehicle} />
                <Route path="/vehicles" exact component={VehicleList}/>
                <Route path="/garage" exact component={Garage}/>
                <Route path="/garage/:id" exact component={Garage} />
                <Route path="/garages" exact component={GarageList}/>
                <Route path="/register" exact component={Register} />
                <Route path="/login" exact component={Login} />
                <Route
                path="/logout"
                exact
                component={() => (
                  <Login message="User Logged Out Successfully." />
                )}
              /> 
              </Switch>
            </Col> 
          </Row>
          
        </Container>
        <Footer/>
    </Router>
  );
}

export default App;
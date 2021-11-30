import React, { useState } from "react";
import { useDispatch } from "react-redux";
import {
  Row,
  Col,
  Card,
  Form,
  InputGroup,
  FormControl,
  Button,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faPhone,
  faEnvelope,
  faLock,
  faUndo,
  faUserPlus,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import { registerUser } from "../../services/index";
import MyToast from "../MyToast";

const Register = (props) => {
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");

  const initialState = {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
    mobile: "",
  };

  const [user, setUser] = useState(initialState);

  const userChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const dispatch = useDispatch();

  const saveUser = () => {
    dispatch(registerUser(user))
      .then((response) => {
        setShow(true);
        setMessage(response.message);
        resetRegisterForm();
        setTimeout(() => {
          setShow(false);
          props.history.push("/login");
        }, 2000);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const resetRegisterForm = () => {
    setUser(initialState);
  };

  return (
    <div>
      <div style={{ display: show ? "block" : "none" }}>
        <MyToast show={show} message={message} type={"success"} />
      </div>
      <Row className="justify-content-md-center">
        <Col xs={5}>
          <Card className={"border border-dark bg-dark text-white"}>
            <Card.Header>
              <FontAwesomeIcon icon={faUserPlus} /> Register
            </Card.Header>
            <Card.Body>
            <Row className="mb-3">
                <Form.Group as={Col}>
                  <InputGroup>
                    
                      <InputGroup.Text>
                        <FontAwesomeIcon icon={faUser} />
                      </InputGroup.Text>
                    
                    <FormControl required
                      autoComplete="off"
                      type="text"
                      name="firstName"
                      value={user.firstName}
                      onChange={userChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter First Name"
                    />
                  </InputGroup>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col}>
                  <InputGroup>
                    
                      <InputGroup.Text>
                        <FontAwesomeIcon icon={faUser} />
                      </InputGroup.Text>
                    
                    <FormControl
                      autoComplete="off"
                      type="text"
                      name="lastName"
                      value={user.lastName}
                      onChange={userChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Last Name"
                    />
                  </InputGroup>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col}>
                  <InputGroup>
                    
                      <InputGroup.Text>
                        <FontAwesomeIcon icon={faUser} />
                      </InputGroup.Text>
                    
                    <FormControl
                      autoComplete="off"
                      type="text"
                      name="companyName"
                      value={user.companyName}
                      onChange={userChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Company Name"
                    />
                  </InputGroup>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col}>
                  <InputGroup>
                   
                      <InputGroup.Text>
                        <FontAwesomeIcon icon={faEnvelope} />
                      </InputGroup.Text>
                    
                    <FormControl
                      required
                      autoComplete="off"
                      type="text"
                      name="email"
                      value={user.email}
                      onChange={userChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Email Address"
                    />
                  </InputGroup>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col}>
                  <InputGroup>
                    
                      <InputGroup.Text>
                        <FontAwesomeIcon icon={faLock} />
                      </InputGroup.Text>
                   
                    <FormControl
                      required
                      autoComplete="off"
                      type="password"
                      name="password"
                      value={user.password}
                      onChange={userChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Password"
                    />
                  </InputGroup>
                </Form.Group>
              </Row>
              <Row className="mb-3">
                <Form.Group as={Col}>
                  <InputGroup>
                   
                      <InputGroup.Text>
                        <FontAwesomeIcon icon={faPhone} />
                      </InputGroup.Text>
                    
                    <FormControl
                      autoComplete="off"
                      type="text"
                      name="mobile"
                      value={user.mobile}
                      onChange={userChange}
                      className={"bg-dark text-white"}
                      placeholder="Enter Mobile Number"
                    />
                  </InputGroup>
                </Form.Group>
              </Row>
            </Card.Body>
            <Card.Footer style={{ textAlign: "right" }}>
              <Button
                size="sm"
                type="button"
                variant="success"
                onClick={saveUser}
                disabled={user.email.length === 0 || user.password.length === 0}
              >
                <FontAwesomeIcon icon={faUserPlus} /> Register
              </Button>{" "}
              <Button
                size="sm"
                type="button"
                variant="info"
                onClick={resetRegisterForm}
              >
                <FontAwesomeIcon icon={faUndo} /> Reset
              </Button>
            </Card.Footer>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default Register;

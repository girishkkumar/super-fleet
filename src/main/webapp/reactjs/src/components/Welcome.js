import React, {Component} from 'react';

export default class Welcome extends Component{
    render() {
        return (
            <div className="container-fluid bg-dark text-light p-5">
                <div className="container bg-dark p-5">
                    <h1 className="display-4">Welcome to Fleet Management System</h1>               
                </div>
              </div>
        );
    }

}
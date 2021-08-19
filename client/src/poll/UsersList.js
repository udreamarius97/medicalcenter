import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import {getAllUsers, getPrescriptions, delUser, addUser} from "../util/APIUtils";

import LoadingIndicator from "../common/LoadingIndicator";
import ProductTable from "../util/ProductTable";
import AbortController from "abortcontroller-polyfill/dist/abortcontroller";
import {Button, notification} from "antd";

class UsersList extends Component{
    controller=new AbortController();
    constructor(props) {
        super(props);
        this.state = {
            products: [],
            isLoading: false
        };

        this.loadPollList = this.loadPollList.bind(this);
        this.handleUserInput=this.handleUserInput.bind(this);
        this.handleRowDel=this.handleRowDel.bind(this);
        this.handleProductTable=this.handleProductTable.bind(this);
        this.handleRowUpdate=this.handleRowUpdate.bind(this);
        this.addNewUser=this.addNewUser.bind(this);
    }

    loadPollList() {
        let promise;

        promise = getAllUsers(this.controller);
        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise.then(response=>{
            this.setState({
                products:response,
                isLoading: false
            })
        }).catch(error => {
            notification.error({
                message: 'Medical Center',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
            this.props.history.push("/users/me");
        });
    }

    componentDidUpdate(nextProps) {
    }

    handleUserInput(filterText) {
        this.setState({filterText: filterText});
    };
    handleRowDel(product) {
        let promise;

        promise = delUser(product.email);
        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });
        promise.then(response=>{
            notification.success({
                message: 'Medical Center',
                description: "Thank you! You're successfully registered. Please Login to continue!",
            });
            this.setState({
                isLoading: false
            });
        }).catch(error => {
            notification.error({
                message: 'Medical Center',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
        });


        var index = this.state.products.indexOf(product);
        this.state.products.splice(index, 1);
        this.setState(this.state.products);
    };

    handleRowUpdate(product){

        const user2 = {
            name: product.name,
            email: product.email,
            username: product.username,
            birthdate: product.birthdate,
            gender: product.gender,
            address: product.address,
            role: product.role
        };
        let promise;
        promise = addUser(user2);
        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });
        promise.then(response=>{
            notification.success({
                message: 'Medical Center',
                description: "Thank you! User added successfully !",
            });
            this.setState({
                isLoading: false
            });
        }).catch(error => {
            notification.error({
                message: 'Medical Center',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
        });
    }
    handleProductTable(evt) {
        var item = {
            id: evt.target.id,
            name: evt.target.name,
            value: evt.target.value
        };
        console.log(this.state.products);
        var products = this.state.products.slice();
        var newProducts = products.map(function(product) {

            for (var key in product) {
                if (key == item.name && product.id == item.id) {
                    product[key] = item.value;

                }
            }
            return product;
        });
        this.setState({products:newProducts});
    };

    componentDidMount() {
        this.loadPollList();
    }

    componentWillUnmount() {
        this.controller.abort();
    }

    addNewUser() {
        const user2 = {
            name: "",
            email: "",
            username: "",
            birthdate: "",
            gender: "",
            address: "",
            role: ""
        };
        let newProducts=this.state.products;
        newProducts.push(user2);
        this.setState({products:newProducts});

    }

    render() {
        return (
            <div className="polls-container">
                <Button type="primary"
                        htmlType="submit"
                        size="large"
                        className="signup-form-button"
                        onClick={this.addNewUser}>Add user</Button>
                {
                    this.state.isLoading ?
                        <LoadingIndicator />: <ProductTable onProductTableUpdate={this.handleProductTable}  onRowDel={this.handleRowDel} products={this.state.products}
                        onRowUpdate={this.handleRowUpdate} />
                }
            </div>
        );
    }
}
export default withRouter(UsersList);
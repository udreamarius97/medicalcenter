import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import {getAllMeds, delMeds, addMeds} from "../util/APIUtils";
import LoadingIndicator from "../common/LoadingIndicator";
import ProductTable from "../util/ProductTable";
import AbortController from "abortcontroller-polyfill/dist/abortcontroller";
import {Button, notification} from "antd";
import ProductTableMeds from "../util/ProductTableMeds";

class MedsList extends Component{
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

        promise = getAllMeds();
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

        promise = delMeds(product.name);
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

        const meds2 = {
            name: product.name,
            sideEfects: product.sideEfects,
            dosage: product.dosage,
        };
        let promise;
        promise = addMeds(meds2);
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
        const meds2 = {
            name:"",
            sideEfects: "",
            dosage: "",
        };
        let newProducts=this.state.products;
        newProducts.push(meds2);
        this.setState({products:newProducts});

    }

    render() {
        return (
            <div className="polls-container">
                <Button type="primary"
                        htmlType="submit"
                        size="large"
                        className="signup-form-button"
                        onClick={this.addNewUser}>Add medication</Button>
                {
                    this.state.isLoading ?
                        <LoadingIndicator />: <ProductTableMeds onProductTableUpdate={this.handleProductTable}  onRowDel={this.handleRowDel} products={this.state.products}
                                                            onRowUpdate={this.handleRowUpdate} />
                }
            </div>
        );
    }
}
export default withRouter(MedsList);
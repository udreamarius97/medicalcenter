import React, { Component } from 'react';
import { addMedication } from "../util/APIUtils";
import "react-datepicker/dist/react-datepicker.css";
import { Form, Input, Button, notification } from 'antd';
const FormItem = Form.Item;

class Medication extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: {
                value: ''
            },
            startDate: {
                value: ''
            },
            endDate: {
                value: ''
            },
            med2: {
                value: ''
            },
            dozaj2: {
                value: ''
            },
            med3: {
                value: ''
            },
            dozaj3: {
                value: ''
            },
            med4: {
                value: ''
            },
            dozaj4: {
                value: ''
            },
            med1: {
                value: ''
            },
            dozaj1: {
                value: ''
            }
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName] : {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        debugger;
        const addMedication2 = {
            email: this.state.email.value,
            startDate: this.state.startDate.value,
            endDate: this.state.endDate.value,
            med1:this.state.med1.value,
            dozaj1: this.state.dozaj1.value,
            med2:this.state.med2.value,
            dozaj2: this.state.dozaj2.value,
            med3:this.state.med3.value,
            dozaj3: this.state.dozaj3.value,
            med4:this.state.med4.value,
            dozaj4: this.state.dozaj4.value
        };
        let promise;

        promise = addMedication(addMedication2);
        if(!promise) {
            return;
        }
        promise.then(response => {
                notification.success({
                    message: 'Medical Center',
                    description: "Thank you! You're successfully registered. Please Login to continue!",
                });
            }).catch(error => {
            notification.error({
                message: 'Medical Center',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
        });
    }

    render() {
        return (
            <div className="signup-container">
                <h1 className="page-title">Sign Up</h1>
                <div className="signup-content">
                    <Form onSubmit={this.handleSubmit} className="signup-form">
                        <FormItem
                            label="Patient email">
                            <Input
                                size="large"
                                name="email"
                                autoComplete="off"
                                placeholder="email"
                                value={this.state.email.value}
                                onChange={(event) => this.handleInputChange(event,(email)=>true)} />
                        </FormItem>
                        <FormItem label="Start date">
                            <Input
                                size="large"
                                name="startDate"
                                placeholder="Start date(dd-MM-yyyy)"
                                value={this.state.startDate.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem label="End date">
                            <Input
                                size="large"
                                name="endDate"
                                placeholder="End date(dd-MM-yyyy)"
                                value={this.state.endDate.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="Medication 1">
                            <Input
                                size="large"
                                name="med1"
                                placeholder="Medication 1"
                                value={this.state.med1.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="dozaje 1">
                            <Input
                                size="large"
                                name="dozaj1"
                                placeholder="dozaje"
                                value={this.state.dozaj1.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="Medication 2">
                            <Input
                                size="large"
                                name="med2"
                                placeholder="Medication 2"
                                value={this.state.med2.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="dozaje 2">
                            <Input
                                size="large"
                                name="dozaj2"
                                placeholder="dozaje"
                                value={this.state.dozaj2.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="Medication 3">
                            <Input
                                size="large"
                                name="med3"
                                placeholder="Medication 3"
                                value={this.state.med3.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="dozaje 3">
                            <Input
                                size="large"
                                name="dozaj3"
                                placeholder="dozaje"
                                value={this.state.dozaj3.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="Medication 4">
                            <Input
                                size="large"
                                name="med4"
                                placeholder="Medication 4"
                                value={this.state.med4.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem
                            label="dozaje 4">
                            <Input
                                size="large"
                                name="dozaj4"
                                placeholder="dozaje"
                                value={this.state.dozaj4.value}
                                onChange={(event) => this.handleInputChange(event,()=>true)} />
                        </FormItem>
                        <FormItem>
                            <Button type="primary"
                                    htmlType="submit"
                                    size="large"
                                    className="signup-form-button">Add medication</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }
}

export default Medication;
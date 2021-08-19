import React, { Component } from 'react';
import {Link, withRouter} from "react-router-dom";
import {addRecomandation} from "../util/APIUtils";
import {Button, Form, Input, notification} from "antd";
const FormItem = Form.Item;

class Recomandation extends Component{
    constructor(props) {
        super(props);
        this.state = {
            patient: null,
            caregiver: null,
            message: null
        };
        this.handleSubmit=this.handleSubmit.bind(this);
        this.handleChange1 = this.handleChange1.bind(this);
        this.handleChange2 = this.handleChange2.bind(this);
        this.handleChange3 = this.handleChange3.bind(this);
    }

    handleChange1(event) {
        this.setState({patient: event.target.value});
    }

    handleChange2(event) {
        this.setState({caregiver: event.target.value});
    }

    handleChange3(event) {
        this.setState({message: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        let promise;
        console.log(this.state);
        promise = addRecomandation(this.state);
        if(!promise) {
            return;
        }
        promise.then(response=>{
            notification.success({
                message: 'Medical Center',
                description: "Thank you! ",
            });
        }).catch(error => {
            notification.error({
                message: 'Medical Center',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
            });
        });
    }

    render(){
        return (
            <div className="user-details">
                <Form onSubmit={this.handleSubmit} className="login-form">
                    <FormItem
                        label="Nume pacient">
                        <Input
                            type="text"
                            value={this.state.patient}
                            onChange={this.handleChange1} />
                    </FormItem>
                    <FormItem
                        label="Nume caregiver">
                        <Input
                            type="text"
                            value={this.state.caregiver}
                            onChange={this.handleChange2} />
                    </FormItem>
                    <FormItem
                        label="Mesaj">
                        <Input
                            type="text"
                            value={this.state.message}
                            onChange={this.handleChange3} />
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" size="large" className="login-form-button">Add</Button>
                    </FormItem>
                </Form>
            </div>
        )
    }
}

export default withRouter(Recomandation);
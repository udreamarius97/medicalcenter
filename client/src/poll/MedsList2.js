import React, { Component } from 'react';
import Meds from "./Meds";
import {getMedsTaken} from "../util/APIUtils";
import {Button, Form, Input} from "antd";
import LoadingIndicator from "../common/LoadingIndicator";
import {withRouter} from "react-router-dom";

const FormItem = Form.Item;

class MedsList2 extends Component{
    constructor(props) {
        super(props);
        this.state = {
            activities: [],
            name:'',
            date:'',
            isLoading: false
        };
        this.getMedsTaken = this.getMedsTaken.bind(this);
        this.handleChange2 = this.handleChange2.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit=this.handleSubmit.bind(this);
    }


    handleChange(event) {
        this.setState({name: event.target.value});
    }

    handleChange2(event) {
        this.setState({date: event.target.value});
    }
    getMedsTaken(name,date){
        let promise;
        promise = getMedsTaken(name.replace(" ","_"),date);
        if(!promise) {
            return;
        }

        promise.then(response=>{
            console.log(response);
            this.setState({
                activities:response
            })
        })
    }

    handleSubmit(event) {
        event.preventDefault();
        this.setState({
            isLoading: true
        });
    }

    componentDidUpdate() {
        if(this.state.isLoading==true){
            this.getMedsTaken(this.state.name,this.state.date);
            this.state.isLoading=false;

        }
    }

    render() {
        const patientsViews = [];
        this.state.activities.forEach((activities) => {
            patientsViews.push(<Meds medsTaken={activities} />)
        });

        return (
            <div className="user-details">
                {patientsViews}
                {
                    !this.state.isLoading && this.state.activities.length === 0 ? (
                        <Form onSubmit={this.handleSubmit} className="login-form">
                            <FormItem
                                label="Nume pacient">
                                <Input
                                    type="text"
                                    value={this.state.name}
                                    onChange={this.handleChange} />
                            </FormItem>
                            <FormItem
                                label="Data">
                                <Input
                                    type="text"
                                    value={this.state.date}
                                    onChange={this.handleChange2} />
                            </FormItem>
                            <FormItem>
                                <Button type="primary" htmlType="submit" size="large" className="login-form-button">Find</Button>
                            </FormItem>
                        </Form>
                    ): null
                }
                {
                    this.state.isLoading ?
                        <LoadingIndicator />: null
                }
            </div>
        );
    }
}

export default withRouter(MedsList2);
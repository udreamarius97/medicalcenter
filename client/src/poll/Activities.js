import React, { Component } from 'react';
import {Link, withRouter} from "react-router-dom";
import {getActivities, calculateDataSet} from "../util/APIUtils";
import LoadingIndicator from "../common/LoadingIndicator";
import Activity from "./Activity";
import {Button, Form, Input, notification} from "antd";
import {ACCESS_TOKEN} from "../constants";
import Chart from "react-google-charts";
const FormItem = Form.Item;

const data = [
    ["Activity", "Frecventa"],
    ["Sleeping", 2],
    ["Toileting", 3],
    ["Showering", 1],
    ["Breakfast", 1],
    ["Grooming", 1],
    ["Spare_Time/TV", 2],
    ["Leaving", 1]
];

const options = {
    title: "Frecventa activitatilor unui pacient",
    legend: { position: "none" }
};

class Activities extends Component{
    constructor(props) {
        super(props);
        this.state = {
            activities: [],
            name:'',
            isLoading: false
        };
        this.loadActivities = this.loadActivities.bind(this);
        this.handleInputChange=this.handleInputChange(this);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit=this.handleSubmit.bind(this);
        //this. calculate=this.calculate.bind(this);
    }


    handleChange(event) {
        this.setState({name: event.target.value});
    }

    handleInputChange(event) {
        this.setState({
            name : event.value
        });
    }
    loadActivities(name){

        let promise;
        promise = getActivities(name.replace(" ","_"));
        if(!promise) {
            return;
        }

        promise.then(response=>{
            console.log(response);
            this.setState({
                activities:response
            });

        });

    }

    handleSubmit(event) {
        event.preventDefault();
        this.setState({
            isLoading: true
        });
    }

    componentDidUpdate() {
        if(this.state.isLoading==true){
            this.loadActivities(this.state.name);
            this.state.isLoading=false;
        }
    }
    calculate(){
        let promise;
        promise = calculateDataSet(this.state.activities);
        if(!promise) {
            return;
        }

        promise.then(response=>{
            console.log(response);
            this.setState({
                data:response
            });
        });
    }
    render() {
        const patientsViews = [];
        console.log(this.state.activities);
        this.state.activities.forEach((activities) => {
            patientsViews.push(<Activity activities={activities} name={this.state.name}/>);
        });

        return (
            <div className="user-details">

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
                            <FormItem>
                                <Button type="primary" htmlType="submit" size="large" className="login-form-button">Find</Button>
                            </FormItem>
                        </Form>
                    ): ( <Chart
                        chartType="Histogram"
                        width="100%"
                        height="350px"
                        data={data}
                        options={options}
                    />)
                }
                {patientsViews}
                {
                    this.state.isLoading ?
                        <LoadingIndicator />: null
                }
            </div>
        );
    }
}

export default withRouter(Activities);
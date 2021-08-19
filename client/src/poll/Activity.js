import React, { Component } from 'react';
import {withRouter} from "react-router-dom";
import {Button, Form} from "antd";
import FormItem from "antd/es/form/FormItem";
import {getActivities, setActivities} from "../util/APIUtils";

class Activity extends Component{
    constructor(props) {
        super(props);
        this.state = {
            mesage:''
        };
        //this.handleSubmit = this.handleSubmit.bind(this);
        this.firstFunction=this.firstFunction.bind(this);
        this.secondFunction=this.secondFunction.bind(this);
    }

    firstFunction(){
        this.props.activities.behavior='normal';
        let promise;
        promise = setActivities(this.props.name.replace(" ","_"),this.props.activities);
        if(!promise) {
            return;
        }

        promise.then(response=>{
            console.log(response);
            this.setState({
                mesage:response
            })
        })
    }

    secondFunction(){
        console.log("not normal")
        this.props.activities.behavior='not normal';
        let promise;
        promise = setActivities(this.props.name.replace(" ","_"),this.props.activities);
        if(!promise) {
            return;
        }

        promise.then(response=>{
            console.log(response);
            this.setState({
                mesage:response
            })
        })
    }


    render(){
        return(
            <div >

                <Form >
                    <FormItem >
                        <table>
                        <tr>
                        <th>{this.props.activities.activity}</th>
                        <th>{this.props.activities.startDate}</th>
                        <th>{this.props.activities.endDate}</th>
                        <th>{this.props.activities.behavior}</th>
                        </tr>
                        </table>
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" size="large" className="login-form-button" onClick={this.firstFunction}>Normal</Button>
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" size="large" className="login-form-button" onClick={this.secondFunction}>Not Normal</Button>
                    </FormItem>
                </Form>
            </div>
        )
    }
}

export default Activity;
import React, { Component } from 'react';
import {withRouter} from "react-router-dom";
import {Button, Form} from "antd";
import FormItem from "antd/es/form/FormItem";


class Meds extends Component{
    constructor(props) {
        super(props);
    }


    render(){
        return(
            <div >
                <Form >
                    <FormItem >

                        <p>{this.props.medsTaken.medication}</p>
                        <p>{this.props.medsTaken.day}</p>
                        <p>{this.props.medsTaken.isTaken}</p>
                    </FormItem>
                </Form>
            </div>
        )
    }
}

export default Meds;
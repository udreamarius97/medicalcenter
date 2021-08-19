import React, { Component } from 'react';
import { getPrescriptions } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import { withRouter } from 'react-router-dom';
import {notification} from "antd";
class PrescriptionList extends Component{
    constructor(props) {
        super(props);
        this.state = {
            prescriptions: [],
            isLoading: false
        };
        this.loadPollList = this.loadPollList.bind(this);
        this.renderTableData = this.renderTableData.bind(this);

    }

    loadPollList() {
        let promise;

        promise = getPrescriptions();



        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise.then(response=>{
            this.setState({
                prescriptions:response,
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });
    }

    componentDidMount() {
        this.loadPollList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                prescriptions: [],
                isLoading: false
            });
            this.loadPollList();
        }
    }

    renderTableData() {
        let i=0;
        return this.state.prescriptions.map((prescription, index) => {
            const { patientUsername, startDate, endDate, medsPrescs } = prescription
            return medsPrescs.map((medPresc,index2) => {
                const {numeMed,perioadaTratament} = medPresc;
                i++;
                return (
                    <tr key={i}>
                        <td>{startDate.toString().substring(0,10)}</td>
                        <td>{endDate.toString().substring(0,10)}</td>
                        <td>{numeMed}</td>
                        <td>{perioadaTratament}</td>
                    </tr>
                )
            })
        })
    }

    render() {
        return (
            <div className="user-details">
                {
                    this.props.currentUser ?(
                    !this.state.isLoading ? (
                        this.props.currentUser.role=="ROLE_PATIENT" ?(
                        this.state.prescriptions.length === 0 ? (
                            <div className="no-polls-found">
                                <span>No Prescriptions found for: </span>
                                <span>{this.props.currentUser.email}</span>
                            </div>
                        ): (
                            <div>
                                <h1 id='title'>Medications</h1>
                                <table id='prescriptions'>
                                    <tbody>
                                    {this.renderTableData()}
                                    </tbody>
                                </table>
                            </div>
                        )
                        ):(
                            <div className="hello">
                                <span>Hello</span>
                            </div> )
                    ) : <LoadingIndicator />
                    ) : (this.props.history.push("/login"))
                }
            </div>
        );
    }
}

export default withRouter(PrescriptionList);
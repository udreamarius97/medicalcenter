import React, { Component } from 'react';
import { getUserProfile, getMessages } from '../../util/APIUtils';
import { Avatar, Tabs } from 'antd';
import { getAvatarColor } from '../../util/Colors';
import LoadingIndicator  from '../../common/LoadingIndicator';
import './Profile.css';
import NotFound from '../../common/NotFound';
import ServerError from '../../common/ServerError';
import AbortController from "abortcontroller-polyfill/dist/abortcontroller";

const TabPane = Tabs.TabPane;

class Profile extends Component {
    controller=new AbortController();
    constructor(props) {
        super(props);
        this.state = {
            user: null,
            isLoading: false
        }
        this.loadUserProfile = this.loadUserProfile.bind(this);
        this.loadMessages=this.loadMessages.bind(this);
    }

    loadUserProfile(username) {
        this.setState({
            isLoading: true
        });

        getUserProfile(username,this.controller)
        .then(response => {
            this.setState({
                user: response,
                isLoading: false
            });
        }).catch(error => {
            if(error.status === 404) {
                this.setState({
                    notFound: true,
                    isLoading: false
                });
            } else {
                this.setState({
                    serverError: true,
                    isLoading: false
                });        
            }
        });        
    }

    loadMessages(){
        getMessages().then(
            response=>{
                console.log(response);
            }
        )
    }
    componentDidMount() {
        const username = this.props.match.params.username;
        this.loadUserProfile(username);
        this.loadMessages();
    }

    componentDidUpdate(nextProps) {

    }

    componentWillUnmount() {
        this.controller.abort();
    }

    render() {
        if(this.state.isLoading) {
            return <LoadingIndicator />;
        }

        if(this.state.notFound) {
            return <NotFound />;
        }

        if(this.state.serverError) {
            return <ServerError />;
        }

        const tabBarStyle = {
            textAlign: 'center'
        };

        return (
            <div className="profile">
                { 
                    this.state.user ? (
                        <div className="user-profile">
                            <div className="user-details">
                                <div className="user-avatar">
                                    <Avatar className="user-avatar-circle" style={{ backgroundColor: getAvatarColor(this.state.user.name)}}>
                                        {this.state.user.name[0].toUpperCase()}
                                    </Avatar>
                                </div>
                                <div className="user-summary">
                                    <div className="full-name">{this.state.user.name}</div>
                                    <div className="username">@{this.state.user.username}</div>
                                    <div className="email">{this.state.user.email}</div>
                                    <div className="birthdate">{this.state.user.birthdate.substring(0,10)}</div>
                                    <div className="address">{this.state.user.address}</div>
                                    <div className="gender">{this.state.user.gender}</div>
                                </div>
                            </div>
                        </div>  
                    ): null               
                }
            </div>
        );
    }
}

export default Profile;
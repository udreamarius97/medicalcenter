import React, { Component } from 'react';
import './App.css';
import {
  Route,
  withRouter,
  Switch
} from 'react-router-dom';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import PrescriptionList from "../poll/PrescriptionList";
import LoadingIndicator from '../common/LoadingIndicator';

import { Layout, notification } from 'antd';
import UsersList from "../poll/UsersList";
import AbortController from "abortcontroller-polyfill/dist/abortcontroller";
import MedsList from "../poll/MedsList";
import Medication from "../poll/Medication";
const { Content } = Layout;

class App extends Component {
  controller=new AbortController();
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });    
  }

  loadCurrentUser() {
      this.setState({
        isLoading: true
      });
      getCurrentUser(this.controller)
          .then(response => {
            this.setState({
              currentUser: Object.assign({}, response),
              isAuthenticated: true,
              isLoading: false
            });
          }).catch(error => {
        this.setState({
          isLoading: false
        });
      });
  }

  componentDidMount() {
    this.loadCurrentUser();
  }

  componentDidUpdate() {

  }

  componentWillUnmount() {
    this.controller.abort();
  }

  handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);
    
    notification[notificationType]({
      message: 'Medical Center',
      description: description,
    });
  }

  handleLogin() {
    notification.success({
      message: 'Medical Center',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    this.props.history.push("/users/me");
  }

  render() {
    if(this.state.isLoading) {
      return <LoadingIndicator />
    }
    return (
        <Layout className="app-container">
          <AppHeader isAuthenticated={this.state.isAuthenticated} 
            currentUser={this.state.currentUser} 
            onLogout={this.handleLogout} />

          <Content className="app-content">
            <div className="container">
              <Switch>
                <Route path="/users/me"
                       render={(props) => <PrescriptionList isAuthenticated={this.state.isAuthenticated}
                                                            currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                </Route>
                <Route path="/login" 
                  render={(props) => <Login onLogin={this.handleLogin} {...props} />}></Route>
                <Route path="/signup" component={Signup}></Route>
                <Route path="/users/userlist"
                       render={(props) => <UsersList isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
                </Route>
                <Route path="/users/medslist"
                       render={(props) => <MedsList isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
                </Route>
                <Route path="/users/addmedication"
                       render={(props) => <Medication isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
                </Route>
                <Route path="/users/:username"
                       render={(props) => <Profile isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
                </Route>
                <Route component={NotFound}></Route>
              </Switch>
            </div>
          </Content>
        </Layout>
    );
  }
}

export default withRouter(App);

import { API_BASE_URL, POLL_LIST_SIZE, ACCESS_TOKEN } from '../constants';
const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};



export function login(loginRequest) {
    console.log(loginRequest);
    return request({
        url: API_BASE_URL + "/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}


export function getCurrentUser(option) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET',
        abortSignal:option.signal
    });

}

export function getUserProfile(username,controller) {
    console.log(username);
    return request({
        url: API_BASE_URL + "/user/" + username,
        method: 'GET',
        abortSignal: controller.signal
    });
}

export function getPrescriptions() {

    return request({
        url: API_BASE_URL + "/user/users/prescriptions",
        method: 'GET'
    });
}

export function getAllUsers(options) {
    return request({
        url: API_BASE_URL + "/users/doctor/patients",
        method: 'POST',
        abortSignal:options.signal
    });
}

export function delUser(email) {
    return request({
        url: API_BASE_URL + "/user/doctor",
        method: 'POST',
        body: email
    });
}

export function addUser(user) {
    return request({
        url: API_BASE_URL + "/users/userlist/adduser",
        method: 'POST',
        body: JSON.stringify(user)
    });
}

export function getAllMeds() {
    return request({
        url: API_BASE_URL + "/medication/medications",
        method: 'GET'
    });
}

export function delMeds(name) {
    return request({
        url: API_BASE_URL + "/medications/delete",
        method: 'POST',
        body: name
    });
}

export function addMeds(meds) {
    return request({
        url: API_BASE_URL + "/medications/addMedication",
        method: 'POST',
        body: JSON.stringify(meds)
    });
}

export function addMedication(meds) {
    return request({
        url: API_BASE_URL + "/doctor/tratement",
        method: 'POST',
        body: JSON.stringify(meds)
    });
}

export function getMessages(){

    return request({
        url: API_BASE_URL + "/user/messages",
        method: 'GET',
    });
}

export function getActivities(nume){
    return request({
        url: API_BASE_URL + "/auth/doctor/activities/name="+nume,
        method: 'GET',
    });
}

export function setActivities(nume,activity){
    console.log("activity");
    return request({
        url: API_BASE_URL + "/auth/doctor/activities/name="+nume,
        method: 'POST',
        body: JSON.stringify(activity)
    });
}

export function getMedsTaken(nume,date){
    return request({
        url: API_BASE_URL + "/auth/doctor/medicationTaken/name="+nume,
        method: 'POST',
        body:date
    });
}

export function addRecomandation(date){
    console.log("aici3");
    return request({
        url: API_BASE_URL + "/auth/doctor/recomandation",
        method: 'POST',
        body:date
    });
}
export function calculateDataSet(activities){
    const data = [
        ["Activity", "Frecventa"]
    ];
    let actv=[];
    let frecv=[];
    let m=0;
    activities.forEach((activities) => {

        if (actv.length === 0) {
            actv.push(activities.activity);
            frecv.push(1);
            m = m + 1;
        } else {
            for (let i = 0; i < m; i++) {
                if (actv[i] == activities.activity)
                    frecv[i] = frecv[i] + 1;
                else {
                    actv.push(activities.activity);
                    frecv.push(1);
                    console.log(activities.activity);
                    m = m + 1;
                }
            }
        }
    });
    for(let i=0;i<m;i++){
        console.log([actv[i],frecv[i]]);
        data.push([actv[i],frecv[i]]);
    }
    return {data};
}

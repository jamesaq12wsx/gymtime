import fetch from 'unfetch';
import { MdRoom } from 'react-icons/md';
import { ACCESS_TOKEN } from '../components/constants';

const apiRoot = '/api/v1';

const checkStatus = response => {

    console.log('checkStatus', response);

    if (response.status === 200) {
        return response;
    } else {
        let error = new Error(response.statusText);
        error.response = response;

        response.json().then(e => {
            error.error = e;
        });
        return Promise.reject(error);
    }
}

export const getAllClubs = () => {
    
    const jwtToken = localStorage.getItem(ACCESS_TOKEN);

    if(jwtToken){
        return fetch(apiRoot + '/clubs', {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        }).then(checkStatus);

    }else{
        return fetch(apiRoot + '/clubs').then(checkStatus);
    }
}

export const getAllClubsWithLocation = (lat, lon) => fetch(apiRoot + `/clubs/location?lat=${lat}&lon=${lon}`).then(checkStatus);

// export const getClubDetail = (uuid) => fetch(apiRoot + `/clubs/club/${uuid}`).then(checkStatus);

export const getClubDetailWithToken = (uuid) => {

    const token = localStorage.getItem(ACCESS_TOKEN);

    if(token){
        return fetch(apiRoot + `/clubs/club/${uuid}`,{
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(checkStatus);
    }else{
        return fetch(apiRoot + `/clubs/club/${uuid}`).then(checkStatus);
    }
}

export const getClubPosts = (clubUuid, date) => fetch(apiRoot + `/clubs/club/${clubUuid}/posts/${date}`).then(checkStatus);

export const getUserPost = (year) => fetch(apiRoot + `/post`, {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
    }
}).then(checkStatus);

export const signUp = (values) => fetch(
    apiRoot + '/auth/signup',
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }
).then(checkStatus);

export const login = (values) => fetch(
    '/login',
    {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }
).then(checkStatus);

export const checkToken = async () => {

    let response = await fetch(apiRoot + '/auth/check', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
        }
    });

    // console.log('checkToken', response);

    return response.status === 200 ? true : false;
}

export const quickPost = (clubUuid, token) => {
    if(token){
        return fetch(apiRoot + `/post`,{
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({clubUuid: clubUuid})
        }).then(checkStatus);
    }else{
        return Promise.reject(new Error('No token'));
    }
}

export const newPost = (post) => fetch(apiRoot + '/post', {
    method: 'POST',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(post)
}).then(checkStatus);

export const updatePost = (post) => fetch(apiRoot + '/post', {
    method: 'PUT',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(post)
}).then(checkStatus);

export const deletePost = (postUuid) => fetch(apiRoot+`/post/${postUuid}`, {
    method: 'DELETE',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    }
}).then(checkStatus);

export const getCountryItems = () => fetch(apiRoot + '/info/select/country').then(checkStatus);

export const getAllExercise = () => fetch(apiRoot+'/exercise').then(checkStatus);

export const getAllFitness = (country) => fetch(apiRoot + `/select/club?country=${country}`).then(checkStatus); 

export const getUserIpInfo = () => fetch('http://api.ipstack.com/check?access_key=07ba5f3189ccf2badb42ac5d0311a522').then(checkStatus);

// export const getStudentCourses = studentId => fetch(`/api/students/${studentId}/courses`).then(checkStatus);

// export const addNewStudent = student => 
//     fetch('api/students',{
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         method: 'POST',
//         body: JSON.stringify(student)
//     }).then(checkStatus);
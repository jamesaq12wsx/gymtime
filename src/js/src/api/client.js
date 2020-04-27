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

    if (jwtToken) {
        return fetch(apiRoot + '/club', {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        }).then(checkStatus);

    } else {
        return fetch(apiRoot + '/clubs').then(checkStatus);
    }
}

export const getAllClubsWithLocation = (lat, lng) => fetch(apiRoot + `/club?lat=${lat}&lng=${lng}`).then(checkStatus);

// export const getClubDetail = (uuid) => fetch(apiRoot + `/clubs/club/${uuid}`).then(checkStatus);

export const getClubDetailWithToken = (uuid) => {

    const token = localStorage.getItem(ACCESS_TOKEN);

    if (token) {
        return fetch(apiRoot + `/club/${uuid}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }).then(checkStatus);
    } else {
        return fetch(apiRoot + `/club/${uuid}`).then(checkStatus);
    }
}

export const getClubPosts = (clubUuid, date) => fetch(apiRoot + `/club/${clubUuid}/post/${date}`).then(checkStatus);

export const getUserPost = (year) => fetch(apiRoot + `/post`, {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
    }
}).then(checkStatus);

export const getPostByUuid = (uuid) => fetch(apiRoot + `/post/${uuid}`, {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
    }
}).then(checkStatus);

export const newPostRecord = (postUuid, recordValues) =>
    fetch(apiRoot + `/post/${postUuid}/record`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(recordValues)
    }).then(checkStatus);

export const deletePostRecord = (postUuid, recordUuid) =>
    fetch(apiRoot + `/post/${postUuid}/record/${recordUuid}`, {
        method: 'DELETE'
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

export const getCurrentUser = () => fetch(apiRoot + '/user/me', {
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
    }
}).then(checkStatus);

export const checkToken = async () => {

    let response = await fetch(apiRoot + '/auth/check', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
        }
    });

    // console.log('checkToken', response);

    return response.status === 200 ? true : false;
}

export const updateUserName = (name) =>
    fetch(apiRoot + '/user/name', {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: name })
    }).then(checkStatus);

export const updateUserGender = (gender) =>
    fetch(apiRoot + '/user/gender', {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ gender: gender })
    }).then(checkStatus);

export const updateUserBirthday = (birthday) =>
    fetch(apiRoot + '/user/birthday', {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ birthday: birthday })
    }).then(checkStatus);

export const updateUserPicture = (file) => {

    var formData = new FormData();
    formData.append('picture', file);

    return fetch(apiRoot + '/user/picture', {
        method: 'PUT',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
        },
        body: formData
    }).then(checkStatus);
}

export const fetchUserBodyStat = () =>
    fetch(apiRoot + '/user/stat', {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
        },
    }).then(checkStatus);

export const changeUserHeight = (values) =>
    fetch(apiRoot + '/user/height', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }).then(checkStatus);

export const newUserWeight = (values) =>
    fetch(apiRoot + '/user/weight', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }).then(checkStatus);

export const deleteWeight = (id) => fetch(apiRoot + `/user/weight/${id}`, {
    method: 'DELETE',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    }
}).then(checkStatus);

export const newUserBodyFat = (values) =>
    fetch(apiRoot + '/user/bodyfat', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(values)
    }).then(checkStatus);

export const deleteBodyFat = (id) => fetch(apiRoot + `/user/bodyfat/${id}`, {
    method: 'DELETE',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    }
}).then(checkStatus);

export const postUserHeightUnit = (id) => fetch(apiRoot + '/user/unit/height', {
    method: 'POST',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        "heightUnit": id
    })
})

export const postUserWeightUnit = (id) => fetch(apiRoot + '/user/unit/weight', {
    method: 'POST',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        "weightUnit": id
    })
})

export const postUserDistanceUnit = (id) => fetch(apiRoot + '/user/unit/distance', {
    method: 'POST',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        "distanceUnit": id
    })
})

export const quickPost = (clubUuid) => {
    return fetch(apiRoot + `/post`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ clubUuid: clubUuid })
    }).then(checkStatus);
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

export const deletePost = (postUuid) => fetch(apiRoot + `/post/${postUuid}`, {
    method: 'DELETE',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`,
    }
}).then(checkStatus);

export const getCountryItems = () => fetch(apiRoot + '/info/select/country').then(checkStatus);

export const getAllExercise = () => fetch(apiRoot + '/exercise').then(checkStatus);

export const getAllFitness = (country) => fetch(apiRoot + `/select/club?country=${country}`).then(checkStatus);

// export const getStudentCourses = studentId => fetch(`/api/students/${studentId}/courses`).then(checkStatus);

// export const addNewStudent = student => 
//     fetch('api/students',{
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         method: 'POST',
//         body: JSON.stringify(student)
//     }).then(checkStatus);
import fetch from 'unfetch';

const apiRoot = '/api/v1';

const checkStatus = response => {

    console.log('checkStatus', response);

    if (response.ok) {
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

export const getAllClubs = () => fetch(apiRoot + '/clubs').then(checkStatus);

export const getAllClubsWithLocation = (lat, lon) => fetch(apiRoot + `/clubs/location?lat=${lat}&lon=${lon}`).then(checkStatus);

export const getClubDetail = (uuid) => fetch(apiRoot + `/clubs/club/${uuid}`).then(checkStatus);

export const getClubPosts = (clubUuid, date) => fetch(apiRoot + `/clubs/club/${clubUuid}/posts/${date}`);

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

export const checkToken = async (token) => {
    let response = await fetch(apiRoot+'/auth/check', {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    });

    return response.status === 200;
};

// export const getStudentCourses = studentId => fetch(`/api/students/${studentId}/courses`).then(checkStatus);

// export const addNewStudent = student => 
//     fetch('api/students',{
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         method: 'POST',
//         body: JSON.stringify(student)
//     }).then(checkStatus);
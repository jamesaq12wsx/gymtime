import { checkToken } from '../api/client';
import { ACCESS_TOKEN } from '../components/constants';

export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';
export const SET_LOCATION = 'SET_LOCATION';
export const LOCATION_STATE = 'LOCATION_STATE';
export const FETCHED_LOCATION_ERROR = 'FETCHED_LOCATION_ERROR';
export const SET_BODY_STAT = 'SET_BODY_STAT';
export const SET_USER = 'SET_USER';

export const appContextReducer = (state, action) => {

    switch (action.type) {

        case LOGIN:

            const jwtToken = action.payload;

            state = { ...state, authenticated: true, ACCESS_TOKEN: jwtToken };

            break;

        case LOGOUT:

            state = { ...state, authenticated: false, ACCESS_TOKEN: '' };

            break;

        case LOCATION_STATE:

            state = {...state, locationPermission: action.payload};

            break;

        case SET_LOCATION:

            state = { ...state, location: action.payload, locationPermission: 'granted' };

            break;

        case FETCHED_LOCATION_ERROR:

            state = {...state, fetchedLocation: false};

            break;

        case SET_BODY_STAT:

            state= {...state, userBodyStat: action.payload}

            break;

        case SET_USER: 

            state = {...state, currentUser: action.payload};

            break;

    }

    return state;
}
import { checkToken } from '../api/client';
import { ACCESS_TOKEN } from '../components/constants';

export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';
export const SET_LOCATION = 'SET_LOCATION';
export const FETCHED_LOCATION_ERROR = 'FETCHED_LOCATION_ERROR';
export const SET_BODY_STAT = 'SET_BODY_STAT';

export const appContextReducer = (state, action) => {

    switch (action.type) {

        case LOGIN:

            const jwtToken = action.payload;

            state = { ...state, authenticated: true, ACCESS_TOKEN: jwtToken };

            break;

        case LOGOUT:

            state = { ...state, authenticated: false, ACCESS_TOKEN: '' };

            break;

        case SET_LOCATION:

            state = { ...state, location: action.payload, fetchedLocation: true };

            break;

        case FETCHED_LOCATION_ERROR:

            state = {...state, fetchedLocation: false};

            break;

        case SET_BODY_STAT:

            state= {...state, userBodyStat: action.payload}

            break;

    }

    return state;
}
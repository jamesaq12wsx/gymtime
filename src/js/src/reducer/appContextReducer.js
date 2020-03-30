import { checkToken } from '../api/client';

export const LOGIN = 'LOGIN';
export const LOGOUT = 'LOGOUT';
export const SET_LOCATION = 'SET_LOCATION';

export const appContextReducer = (state, action) => {

    switch (action.type) {

        case LOGIN:

            const jwtToken = action.payload;

            state = { ...state, authenticated: true, jwtToken: jwtToken };

            break;

        case LOGOUT:

            state = { ...state, authenticated: false, jwtToken: '' };

            break;

        case SET_LOCATION:

            state = { ...state, location: action.payload, fetchedLocation: true };

            break;

    }

    return state;
}
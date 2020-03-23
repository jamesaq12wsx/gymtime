import { checkToken } from '../api/client';

export const appContextReducer = (state, action) => {

    switch (action.type) {

        case 'LOGIN':
            
            const {jwtToken} = action.payload;

            state = {...state, authenticated: true, jwtToken: jwtToken};

            break;

        case 'LOGOUT':
            
            state = {...state, authenticated: false, jwtToken: ''};
            
            break;
    }

    return state;
}
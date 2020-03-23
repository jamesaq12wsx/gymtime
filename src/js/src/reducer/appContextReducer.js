import { checkToken } from '../api/client';
import decode from 'jwt-decode';
import auth from '../components/Auth';

export const appContextReducer = (state, action) => {

    switch (action.type) {
        case 'CHECK_AUTH':

            if(auth.isAuthenticated()){
                state = {...state, authenticated: true};
            }else{
                state = {...state, authenticated: false};
            }

            break;

            // const token = localStorage.getItem('jwtToken');

            // console.log("Check Auth", token);

            // if (token === '') {
            //     state = { ...state, isAuthenticated: false, jwtToken: '' };

            //     break;
            // }

            // try {

            //     const payload = decode(token);

            //     console.log('Jwt token payload', payload);

            //     console.log(payload.exp);
            //     console.log(new Date().getTime());

            //     if(payload.exp < new Date().getTime() / 1000 ){
                    
            //         state = { ...state, isAuthenticated: false, jwtToken: '' };

            //         break;
            //     }

            //     checkToken(token)
            //         .then(res => {
            //             console.log('Token is valid');
            //         })
            //         .catch((err) => {
            //             state = { ...state, isAuthenticated: false, jwtToken: '' };

            //             localStorage.setItem('jwtToken', '');
            //         });

            // } catch (e) {
            //     console.log(`Decode token ${token} error` , e);
            // }

            break;

        case 'LOGIN':
            state = { ...state, isAuthenticated: true, jwt: action.payload };

            localStorage.setItem('jwtToken', action.payload);

            break;
        case 'LOGOUT':
            state = { ...state, isAuthenticated: false, jwt: null };
            break;
    }

    return state;
}
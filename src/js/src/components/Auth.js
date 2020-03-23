import { login, checkToken } from '../api/client';
import decode from 'jwt-decode';
import { wait } from '@testing-library/react';

class Auth {

    constructor() {
        this.jwtToken = localStorage.getItem('jwtToken');
        this.refreshToken = localStorage.getItem('refreshToken');
        this.authenticated = this.isAuthenticated();
    }

    login(values, cb, errCb) {
        login(values)
            .then(res => {

                const token = res.headers.get('Authorization').slice(7);

                this.jwtToken = token;

                this.authenticated = true;

                localStorage.setItem('jwtToken', token);

                if(cb){
                    cb();
                }

            })
            .catch(err => {
                this.jwtToken = '';
                this.authenticated = false;
                localStorage.setItem('jwtToken', '');

                errCb(err);
            })
    }

    logout(cb) {

        console.log('logout');

        localStorage.setItem('jwtToken', '');

        if(cb){
            cb();
        }

    }

    getToken() {
        return localStorage.getItem('jwtToken');
    }

    isAuthenticated() {

        const token = localStorage.getItem('jwtToken') || '';

        if(token === ''){
            localStorage.setItem('jwtToken', '');

            return false;
        }

        try {
            const payload = decode(token);

            if (payload.exp < new Date().getTime() / 1000) {

                console.log('token expire', payload);

                this.authenticated = false;

                this.jwtToken = '';

                localStorage.setItem('jwtToken', '');

            }else{

                let authenticated = checkToken(token);

                console.log('check token from server', authenticated);

                if(authenticated){

                   this.authenticated = true;
                   
                   return true;

                }else{
                    
                    localStorage.setItem('jwtToken', '');

                    this.authenticated = false;

                    return false;

                }

            }

        }catch(e) {
            
            localStorage.setItem('jwtToken', '');

            this.authenticated = false;

            return false;

        }
        
    }

}

export default new Auth();
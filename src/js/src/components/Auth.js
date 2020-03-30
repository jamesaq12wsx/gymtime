import { login, signUp, checkToken } from '../api/client';
import decode from 'jwt-decode';
import { wait } from '@testing-library/react';

class Auth {

    constructor() {
        this.jwtToken = localStorage.getItem('jwtToken');
        this.refreshToken = localStorage.getItem('refreshToken');
        this.authenticated = this.isAuthenticated();
        this.serverCheck = false;
    }

    signUp(values, cb, errCb){
        signUp(values)
            .then(res => {
                if(cb){
                    cb();
                }
            })
            .catch(err => {
                console.error(err);
                if(errCb){
                    errCb(err);
                }
            });
    }

    login(values, cb, errCb) {
        login(values)
            .then(res => {

                const token = res.headers.get('Authorization').slice(7);

                this.jwtToken = token;

                this.authenticated = true;

                localStorage.setItem('jwtToken', token);

                if(cb){
                    cb(token);
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

                if(this.serverCheck && this.authenticated){
                    return true;
                }

                let authenticated = checkToken(token);

                console.log('check token from server', authenticated);

                if(authenticated){

                   this.authenticated = true;

                   this.serverCheck = true;
                   
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
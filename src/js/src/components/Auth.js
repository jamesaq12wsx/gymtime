import { login, signUp, checkToken } from '../api/client';
import decode from 'jwt-decode';
import { wait } from '@testing-library/react';
import { ACCESS_TOKEN, REFRESH_TOKEN } from './constants';

class Auth {

    constructor() {
        this.accessToken = localStorage.getItem(ACCESS_TOKEN);
        this.refreshToken = localStorage.getItem(REFRESH_TOKEN);
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

                this.accessToken = token;

                this.authenticated = true;

                localStorage.setItem(ACCESS_TOKEN, token);

                if(cb){
                    cb(token);
                }

            })
            .catch(err => {
                this.accessToken = '';
                this.authenticated = false;
                localStorage.setItem(ACCESS_TOKEN, '');

                errCb(err);
            })
    }

    logout(cb) {

        console.log('logout');

        localStorage.setItem(ACCESS_TOKEN, '');

        if(cb){
            cb();
        }

    }

    getToken() {
        return localStorage.getItem(ACCESS_TOKEN);
    }

    isAuthenticated() {

        const token = localStorage.getItem(ACCESS_TOKEN) || '';

        if(token === ''){
            localStorage.setItem(ACCESS_TOKEN, '');

            return false;
        }

        try {
            const payload = decode(token);

            if (payload.exp < new Date().getTime() / 1000) {

                console.log('token expire', payload);

                this.authenticated = false;

                this.accessToken = '';

                localStorage.setItem(ACCESS_TOKEN, '');

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
                    
                    localStorage.setItem(ACCESS_TOKEN, '');

                    this.authenticated = false;

                    return false;

                }

            }

        }catch(e) {
            
            localStorage.setItem(ACCESS_TOKEN, '');

            this.authenticated = false;

            return false;

        }
        
    }

}

export default new Auth();
import { login, checkToken } from '../api/client';
import decode from 'jwt-decode';

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

                cb();

            })
            .catch(err => {
                this.jwtToken = '';
                this.authenticated = false;
                localStorage.setItem('jwtToken', '');

                errCb(err);
            })
    }

    logout(cb) {

        localStorage.setItem('jwtToken', '');

        if(cb){
            cb();
        }

    }

    isAuthenticated() {

        console.log('IsAuthenticated');

        const token = localStorage.getItem('jwtToken');

        try {
            const payload = decode(token);

            if (payload.exp < new Date().getTime() / 1000) {

                this.authenticated = false;

                this.jwtToken = '';

                localStorage.setItem('jwtToken', '');
            }else{

                let authenticated = checkToken(token);

                if(authenticated){
                   this.authenticated = true;
                   
                   return true;
                }else{
                    localStorage.setItem('jwtToken', '');

                    this.authenticated = false;

                }

            }

            return this.authenticated;

        }catch(e) {
            localStorage.setItem('jwtToken', '');
            return false;
        }
    }

}

export default new Auth();
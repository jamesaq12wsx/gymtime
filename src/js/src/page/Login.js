import React from 'react';
import LoginForm from '../components/form/LoginForm';
import auth from '../components/Auth';
import { Redirect } from 'react-router-dom';

const Login = () => {
    return (
        auth.isAuthenticated() ?
        <Redirect to="/" /> :
        <LoginForm />
    );
};

export default Login;
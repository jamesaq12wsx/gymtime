import React, { useEffect } from 'react';
import auth from '../components/Auth';
import { Redirect } from 'react-router-dom';

const LogoutPage = () => {
    useEffect(() => {
        auth.logout();
    }, []);

    return <Redirect to='/' />;
}

export default LogoutPage;
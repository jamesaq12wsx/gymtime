import React, { Component, useContext } from 'react';
import { ACCESS_TOKEN } from './constants';
import { Redirect } from 'react-router-dom';
import {AppContext} from '../context/AppContextProvider';

const OAuth2RedirectHandler = (props) => {

    const appContext = useContext(AppContext);
    const {dispatch: appDispatch} = appContext;

    const getUrlParameter = (name) => {
        name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
        var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');

        var results = regex.exec(props.location.search);
        return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
    };


    const token = getUrlParameter('token');
    const error = getUrlParameter('error');

    console.log('oauth2 redirect handler token', token);
    console.log('oauth2 error', error);

    if (token) {

        localStorage.setItem(ACCESS_TOKEN, token);

        appDispatch({ type: 'LOGIN', payload: token });

        return <Redirect to={{
            pathname: "/",
            state: { from: props.location }
        }} />;
    } else {
        return <Redirect to={{
            pathname: `/login/${error}`,
            state: {
                from: props.location,
                error: error
            }
        }} />;
    }

}

export default OAuth2RedirectHandler;
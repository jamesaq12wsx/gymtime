import React, { useContext } from 'react';
import SignUpForm from '../components/form/SignUpForm';
import auth from '../components/Auth';
import { Redirect, useHistory } from 'react-router-dom';
import { AppContext } from '../context/AppContextProvider';
import { errorNotification } from '../components/Notification';

const SignUp = (props) => {

    const history = useHistory();

    const appContext = useContext(AppContext);

    const {state: appState, dispatch: appDispatch} = appContext;

    const {auth} = appState;

    return (
        auth.isAuthenticated() ?
        <Redirect to="/" /> :
        <SignUpForm
            onSubmit={(values, { setSubmitting }) => {

                console.log("SignUp", values);

                auth.signUp(values, () => {

                    const {username, password} = values;

                    auth.login({username, password}, (token) => {
                        appDispatch({type:'LOGIN', payload: token});
                        history.push('/');
                        setSubmitting(false);
                    }, (err) => {
                        console.log('sign in Fail');
                        setSubmitting(false);
                    });

                    // props.onSuccess()
                }, (err) => {
                    setSubmitting(false);
                    errorNotification('Sign Up Fail', err.message);
                });

            }}
         />
    );
};

export default SignUp;
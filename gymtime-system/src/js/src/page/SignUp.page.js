import React, { useContext } from 'react';
import SignUpForm from '../components/form/SignUpForm';
import auth from '../components/Auth';
import { Redirect, useHistory } from 'react-router-dom';
import { AppContext } from '../context/AppContextProvider';
import { errorNotification, successNotification } from '../components/Notification';
import { Card, Col, Row, Divider, Button } from 'antd';
import Container from '../components/Container';
import { FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL } from '../components/constants';
import { createFromIconfontCN } from '@ant-design/icons';
import { FaGoogle, FaFacebook } from "react-icons/fa";
import SocialLogin from '../components/SocialLogin';


const IconFont = createFromIconfontCN({
    scriptUrl: '//at.alicdn.com/t/font_8d5l8fzk5b87iudi.js',
});


const SignUp = (props) => {

    const history = useHistory();

    const appContext = useContext(AppContext);

    const { state: appState, dispatch: appDispatch } = appContext;

    const { auth } = appState;

    if (auth.isAuthenticated()) {
        return <Redirect to="/" />
    } else {
        return (
            <Container>
                <Card
                    className="sign-up-card"
                    style={{
                        width: 400,
                        margin: '0 auto',
                        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)'
                    }}>
                    <SignUpForm
                        onSubmit={(values, { setSubmitting }) => {

                            console.log("SignUp", values);

                            auth.signUp(values, () => {

                                successNotification('Sign up Success');

                                history.push('/login');

                            }, (err) => {
                                setSubmitting(false);
                                errorNotification('Sign Up Fail', err.message);
                            });

                        }}
                    />

                    <Divider> OR </Divider>

                    <SocialLogin />

                </Card>
            </Container>
        );
    }
};

export default SignUp;
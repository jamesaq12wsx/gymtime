import React, { useContext, useState, useEffect } from 'react';
import LoginForm from '../components/form/LoginForm';
import { Redirect } from 'react-router-dom';
import { AppContext } from '../context/AppContextProvider';
import { Divider, Card, Alert } from 'antd';
import SocialLogin from '../components/SocialLogin';

const Login = ({ match: { params }, location }) => {

    const [errorMessage, setErrorMessage] = useState(null);

    const appContext = useContext(AppContext);
    const { state: appState } = appContext;

    const { auth } = appState;

    useEffect(() => {

        if(params.error){
            setErrorMessage(params.error);
        }

    }, [params.error])

    const getAlert = () => {
        if (errorMessage) {
            return (
                <Alert
                    style={{marginTop: 10, marginBottom: 10}}
                    className="login-error"
                    message={`${errorMessage}`}
                    type="error"
                    closable
                />
            );
        }
    }

    if (auth.isAuthenticated()) {
        return <Redirect to="/" />;
    } else {
        return (
            <React.Fragment>
                <Card 
                    className="login-card" 
                    style={{ 
                        width: 400, 
                        margin: '0 auto',
                        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)'
                    }} >
                    <h2>GymTime</h2>

                    {getAlert()}

                    <LoginForm onFailure={(err) => {
                        if(err.error.message){
                            setErrorMessage(err.error.message);
                        }
                    }} />
                    <Divider> OR </Divider>
                    <SocialLogin login />
                </Card>
            </React.Fragment>
        );
    }

};

export default Login;
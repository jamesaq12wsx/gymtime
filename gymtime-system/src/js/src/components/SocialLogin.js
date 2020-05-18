import React from 'react';
import { Button } from 'antd';
import { FaFacebook, FaGoogle } from 'react-icons/fa'
import { FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL } from './constants';

const SocialLogin = (props) => {

    const { login } = props;

        return (
            <React.Fragment>
                <Button
                    block
                    size="large"
                    style={{ width: 300, backgroundColor: 'rgb(65,86,139)', color: 'white' }}
                    href={FACEBOOK_AUTH_URL} >
                    <FaFacebook style={{ marginRight: 10 }} />
                    {`${login ? 'Login' : 'Sign Up'} with Facebook`}
                </Button>
                <br />
                <Button
                    block
                    size="large"
                    style={{ width: 300, marginTop: 10, backgroundColor: 'rgb(81,134,237)', color: 'white' }}
                    href={GOOGLE_AUTH_URL}>
                    <FaGoogle style={{ marginRight: 10 }} />
                    {`${login ? 'Login' : 'Sign Up'} with Google`}
            </Button>
            </React.Fragment>
        );
}

export default SocialLogin;
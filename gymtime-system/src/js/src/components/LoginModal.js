import React from 'react';
import LoginForm from './form/LoginForm';
import { Modal, Divider } from 'antd';
import SocialLogin from './SocialLogin';
import Login from '../page/Login';

const LoginModal = ({ visible, onOk, onCancel, onSuccess, onFailure, toSignUp }) => {

    return (
        <Modal
            title="Login"
            visible={visible ? visible : false}
            onOk={onOk}
            onCancel={onCancel}

        >
            {/* <LoginForm
                onSuccess={onSuccess}
                onFailure={onFailure}
                toSignUp={toSignUp}
            />

            <Divider> OR </Divider>

            <SocialLogin /> */}

            <Login />

        </Modal>
    );

}

export default LoginModal;
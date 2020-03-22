import React from 'react';
import LoginForm from './form/LoginForm';
import { Modal } from 'antd';

const LoginModal = ({ visible, onOk, onCancel, onSuccess, onFailure }) => {

    return (
        <Modal
            title="Login"
            visible={visible ? visible : false}
            onOk={onOk}
            onCancel={onCancel}
        >
            <LoginForm
                onSuccess={onSuccess}
                onFailure={onFailure}
            />

        </Modal>
    );

}

export default LoginModal;
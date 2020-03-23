import React from 'react';
import { Button } from 'antd';
import auth from '../components/Auth';
import { useHistory } from 'react-router-dom';

const User = () => {

    let history = useHistory();

    return (
        <div className="user">
            <h1>User</h1>
            <Button
                onClick={() => {
                    auth.logout();
                    history.push('/');
                }}
                danger
            >
                Logout
            </Button>
        </div>
    );
};

export default User;
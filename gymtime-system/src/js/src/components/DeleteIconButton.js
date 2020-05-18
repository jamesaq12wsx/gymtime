import React from 'react';
import { Button } from 'antd';
import {
    DeleteOutlined
} from '@ant-design/icons';

const DeleteIconButton = (props) => {

    return (
        <Button
            {...props}
            type="danger"
            icon={<DeleteOutlined />}
        >
        </Button>
    );

}

export default DeleteIconButton;
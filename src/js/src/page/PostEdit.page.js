import React, { useState, useContext, useEffect } from 'react';
import { Button, Input, Tag, Row, Col, Select, TreeSelect } from 'antd';
import { Link, useHistory } from 'react-router-dom';
import { PostContext } from '../context/PostContextProvider';
import PostForm from '../components/form/PostForm';
import { updatePost, getUserPost } from '../api/client';
import { errorNotification } from '../components/Notification';

var _ = require('lodash');

const inputBottomStyle = { marginBottom: '5px' };

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const { Option } = Select;
const { TreeNode } = TreeSelect;

const timeFormat = 'HH:mm';

const MyInput = ({ field, form, ...props }) => {
    return <input {...field} {...props} />;
};

const PostEdit = (props) => {

    const history = useHistory();

    const post = props;

    const postContext = useContext(PostContext);

    const { state, dispatch } = postContext;

    // console.log('PostEdit', JSON.stringify(state.editingPost, null, 4));

    return (
        <PostForm
            initPost={state.editingPost}
            onSubmit={(values) => {
                console.log('PostForm submit', values);
                const clubUuid = values.club.clubUuid;
                updatePost({
                    ...values,
                    clubUuid
                })
                .then(res => {
                    
                    dispatch({type:'FINISH_EDIT'});

                    getUserPost()
                        .then(res => res.json())
                        .then(posts => {
                            dispatch({type: 'SET_POSTS', payload: posts});
                        })
                        .catch(err => {
                            errorNotification('Refresh Post Failed', err.error.message);
                        })
                })
                .catch(err => {
                    errorNotification('Update Post Failed', err.error.message);
                });
            }}
        />
    );

}

export default PostEdit;
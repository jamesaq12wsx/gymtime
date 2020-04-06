import React, { useContext } from 'react';
import { PostContext } from '../context/PostContextProvider';
import PostForm from '../components/form/PostForm';
import { newPost, getUserPost } from '../api/client';
import { errorNotification, successNotification } from '../components/Notification';


const NewPost = (props) => {

    const postContext = useContext(PostContext);
    const { state: postState, dispatch: postDispatch } = postContext;
    const { newPostClub } = postState;

    return (
        <PostForm
            initPost={{ club: newPostClub }}
            onSubmit={(values) => {
                const clubUuid = values.club.clubUuid;
                console.log('New Post submit', values);

                newPost({
                    ...values,
                    clubUuid
                }).then(res => {
                    successNotification('You Just Posted');
                    postDispatch({type: 'FINISH_NEW_POST'});

                    getUserPost()
                        .then(res => res.json())
                        .then(posts => postDispatch({type:'SET_POSTS', payload: posts}))
                        .catch(err => errorNotification('Refresh User Posts Failed', err.error.message));

                }).catch(err => {
                    errorNotification('New Post Failed', err.error.message);
                })
            }}
        />
    );
}

export default NewPost;
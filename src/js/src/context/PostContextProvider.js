import React, { useReducer } from 'react';
import { postContextReducer } from '../reducer/postContextReducer';

// this is the equivalent to the createStore method of Redux
// https://redux.js.org/api/createstore

export const PostContext = React.createContext();

/**
 * which use to save user post data, 
 * countries, exercises
 * @param {*} props 
 */
const PostContextProvider = (props) => {

    const initAppState = {
        selectedPost: null,
        posts: []
    };

    const [state, dispatch] = useReducer(postContextReducer,initAppState);

    return (
        <PostContext.Provider value={{state, dispatch}}>
            {props.children}
        </PostContext.Provider>
    );
}

export default PostContextProvider;

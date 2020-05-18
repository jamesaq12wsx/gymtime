var _ = require('lodash');

export const SET_POSTS = 'SET_POSTS';
export const SELECT_POST = "SELECT_POST";
export const DESELECT_POST = "DESELECT_POST";

export const postContextReducer = (state, action) => {

    switch (action.type) {

        case SET_POSTS:

            state = { ...state, posts: action.payload, fetched: true };

            break;

        case 'NEW_POST':

            state = { ...state, newPost: true,  newPostClub: action.payload || {}};

            break;

        case 'FINISH_NEW_POST': 

            state = {...state, newPost: false, newPostClub: null}

            break;

        case SELECT_POST:
            
            // console.log('set editing post', JSON.stringify(action, null, 4));

            state = { ...state, selectedPost: action.payload };

            break;

        case DESELECT_POST:

            state = { ...state, selectedPost: null };

            break;

    }

    return state;
}
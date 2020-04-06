var _ = require('lodash');

export const postContextReducer = (state, action) => {

    switch (action.type) {

        case 'SET_POSTS':

            state = { ...state, posts: action.payload };

            break;

        case 'NEW_POST':

            state = { ...state, newPost: true,  newPostClub: action.payload || {}};

            break;

        case 'FINISH_NEW_POST': 

            state = {...state, newPost: false, newPostClub: null}

            break;

        case 'EDITING':
            
            // console.log('set editing post', JSON.stringify(action, null, 4));

            state = { ...state, editing: true, editingPost: action.post, editingPostChanged: action.payload };

            break;

        case 'FINISH_EDIT':

            state = { ...state, editing: false, editingPost: {}, editingPostChanged: {} };

            break;

    }

    return state;
}
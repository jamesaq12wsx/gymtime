var _ = require('lodash');

export const postContextReducer = (state, action) => {

    switch (action.type) {

        case 'SET_POSTS':

            state = { ...state, posts: action.payload };

            break;

        case 'EDITING':
            
            // console.log('set editing post', JSON.stringify(action, null, 4));

            state = { ...state, editing: true, editingPost: action.post, editingPostChanged: action.payload };

            break;

        case 'FINISH_EDIT':

            state = { ...state, editing: false, editingPost: {}, editingPostChanged: {} };

            break;

        case 'SET_EDITING_CHANGE': 

            state = {...state, editingPostChanged: action.payload};

            break;

    }

    return state;
}
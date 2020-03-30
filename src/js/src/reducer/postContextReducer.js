var _ = require('lodash');

export const postContextReducer = (state, action) => {

    switch (action.type) {

        case 'SET_POSTS':

            state = { ...state, posts: action.payload };

            break;

        case 'EDITING':

            state = { ...state, editing: true, editingPost: _.clone(action.payload, true), editingPostChanged: _.clone(action.payload, true) };

            break;

        case 'FINISH_EDIT':

            state = { ...state, editing: false, editingPost: null, editingPostChanged: null };

            break;

        case 'SET_EDITING_CHANGE': 

            state = {...state, editingPostChanged: action.payload};

            break;

    }

    return state;
}
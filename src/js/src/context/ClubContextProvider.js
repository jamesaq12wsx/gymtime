import React, { useReducer, useEffect } from 'react';
import { clubContextReducer } from '../reducer/clubContextReducer';

// this is the equivalent to the createStore method of Redux
// https://redux.js.org/api/createstore

export const ClubContext = React.createContext();

const ClubContextProvider = (props) => {

    const initAppState = {
        fetching: false,
        nearClubs: []
    };

    const [state, dispatch] = useReducer(clubContextReducer,initAppState);

    return (
        <ClubContext.Provider value={{state, dispatch}}>
            {props.children}
        </ClubContext.Provider>
    );
}

export default ClubContextProvider;

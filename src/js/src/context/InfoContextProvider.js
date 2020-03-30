import React, { useReducer, useEffect } from 'react';
import { infoContextReducer } from '../reducer/infoContextReducer';

// this is the equivalent to the createStore method of Redux
// https://redux.js.org/api/createstore

export const InfoContext = React.createContext();

/**
 * which use to save info data, 
 * countries, exercises
 * @param {*} props 
 */
const InfoContextProvider = (props) => {

    const initAppState = {
        exercises: [],
        countries: []
    };

    const [state, dispatch] = useReducer(infoContextReducer,initAppState);

    return (
        <InfoContext.Provider value={{state, dispatch}}>
            {props.children}
        </InfoContext.Provider>
    );
}

export default InfoContextProvider;

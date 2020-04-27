import React, { useReducer, useEffect } from 'react';
import { appContextReducer } from '../reducer/appContextReducer';
import auth from '../components/Auth';

// this is the equivalent to the createStore method of Redux
// https://redux.js.org/api/createstore

export const AppContext = React.createContext();

const AppContextProvider = (props) => {

    const initAppState = {
        auth: auth,
        location: {},
        fetchedLocation: false,
        currentUser: null,
        userBodyStat: null
    };

    const [state, dispatch] = useReducer(appContextReducer,initAppState);

    return (
        <AppContext.Provider value={{state, dispatch}}>
            {props.children}
        </AppContext.Provider>
    );
}

export default AppContextProvider;

import React, { useReducer, useEffect } from 'react';
import { appContextReducer } from '../reducer/appContextReducer';
import auth from '../components/Auth';

// this is the equivalent to the createStore method of Redux
// https://redux.js.org/api/createstore

export const AppContext = React.createContext();

const AppContextProvider = (props) => {

    const authenticated = auth.isAuthenticated();

    const initAppState = {
        authenticated: authenticated,
        user: {}
    };

    const [state, dispatch] = useReducer(appContextReducer,initAppState);

    return (
        <AppContext.Provider value={{state, dispatch}}>
            {props.children}
        </AppContext.Provider>
    );
}

export default AppContextProvider;

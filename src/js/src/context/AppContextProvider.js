import React, { useReducer } from 'react';
import { appContextReducer } from '../reducer/appContextReducer';

// this is the equivalent to the createStore method of Redux
// https://redux.js.org/api/createstore

export const AppContext = React.createContext();

const AppContextProvider = (props) => {

    const initAppState = {
        login: false
    };

    const [state, dispatch] = useReducer(appContextReducer,initAppState);

    return (
        <AppContext.Provider value={{state, dispatch}}>
            {props.children}
        </AppContext.Provider>
    );
}

export default AppContextProvider;

import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import AppContextProvider from './context/AppContextProvider';
import ClubContextProvider from './context/ClubContextProvider';
import InfoContextProvider, { InfoContext } from './context/InfoContextProvider';
import PostContextProvider from './context/PostContextProvider';
import {Router} from 'react-router-dom'

ReactDOM.render(
    <AppContextProvider>
        <ClubContextProvider>
            <InfoContextProvider>
                <PostContextProvider>
                    <App />
                </PostContextProvider>
            </InfoContextProvider>
        </ClubContextProvider>
    </AppContextProvider>
    , document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

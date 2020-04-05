import React, { useState, useContext } from 'react';

import LoadingList from '../components/list/LoadingList';
import ClubList from '../components/list/ClubList';
import { useHistory } from 'react-router-dom';
import { ClubContext } from '../context/ClubContextProvider';
import { InfoContext } from '../context/InfoContextProvider';
import { AppContext } from '../context/AppContextProvider';

const Clubs = ({ markOnClick, detailOnClick }) => {

    let history = useHistory();

    const clubContext = useContext(ClubContext);
    const { state, dispatch } = clubContext;

    const infoContext = useContext(InfoContext);
    const { state: infoState, dispatch: infoDispatch } = infoContext;

    const appContext = useContext(AppContext);
    const { state: appState } = appContext;

    const { fetching } = state;

    const { clubs } = infoState;

    const { location } = appState;

    const markOnClickHander = (e, club) => {
        console.log(e);
    };

    const detailOnClickHandler = (e, club) => {
        console.log(e);

        if (club) {
            history.push(`/club/${club.clubUuid.toLowerCase()}`);
            detailOnClick(e, club);
        }

    }

    const getList = () => {
        if (fetching) {
            return <LoadingList />;
        };

        const nearClubs = clubs ? clubs.sort(compare).slice(0,50) : []; 

        return (
            <ClubList clubs={nearClubs} markOnClick={markOnClick} detailOnClick={detailOnClickHandler} />
        );
    }

    const compare = (a, b) => {

        const { lat, lng } = location;

        if ((Math.pow(lat - a.latitude, 2) + Math.pow(lng - a.longitude, 2)) < (Math.pow(lat - b.latitude, 2) + Math.pow(lng - b.longitude, 2))) {
            return -1;
        } else {
            return 1;
        }
    }

    return (
        <div className="clubs">
            {getList()}
        </div>
    )
}

export default Clubs;
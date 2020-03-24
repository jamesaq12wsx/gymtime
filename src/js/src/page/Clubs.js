import React, { useState, useContext } from 'react';

import LoadingList from '../components/list/LoadingList';
import ClubList from '../components/list/ClubList';
import { useHistory } from 'react-router-dom';
import { ClubContext } from '../context/ClubContextProvider';

const Clubs = ({ markOnClick, detailOnClick }) => {

    let history = useHistory();

    const clubContext = useContext(ClubContext);
    const {state, dispatch} = clubContext;

    const {fetching, nearClubs} = state;

    const markOnClickHander =(e, club) => {
        console.log(e);
    };

    const detailOnClickHandler =(e, club) => {
        console.log(e);
        
        if(club){
            history.push(`/club/${club.uuid.toLowerCase()}`);
            detailOnClick(e, club);
        }

    }

    const getList = () => {
        if (fetching) {
            return <LoadingList />;
        };

        return (
            <ClubList clubs={nearClubs} markOnClick={markOnClick} detailOnClick={detailOnClickHandler} />
        );
    }

    return (
        <div className="clubs">
            {getList()}
        </div>
    )
}

export default Clubs;
import React, { useState } from 'react';

import LoadingList from '../components/list/LoadingList';
import ClubList from '../components/list/ClubList';
import { useHistory } from 'react-router-dom';

const Clubs = ({ fetching, clubs, markOnClick, detailOnClick }) => {

    let history = useHistory();

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
            <ClubList clubs={clubs} markOnClick={markOnClick} detailOnClick={detailOnClickHandler} />
        );
    }

    return (
        <div className="clubs">
            {getList()}
        </div>
    )
}

export default Clubs;
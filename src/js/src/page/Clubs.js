import React, { useState, useContext } from 'react';

import LoadingList from '../components/list/LoadingList';
import ClubList from '../components/list/ClubList';
import { useHistory } from 'react-router-dom';
import { ClubContext } from '../context/ClubContextProvider';
import { InfoContext } from '../context/InfoContextProvider';
import { AppContext } from '../context/AppContextProvider';
import { Empty, Card, Row, Col } from 'antd';
import { EnvironmentFilled, ClockCircleFilled, MehFilled, MoreOutlined, HomeOutlined, DownCircleOutlined, ShopOutlined } from '@ant-design/icons';
import { WEEKDAY } from '../components/constants';
import CardList from '../components/CardList';

const Clubs = (props) => {

    let history = useHistory();

    const clubContext = useContext(ClubContext);
    const { state, dispatch } = clubContext;

    const infoContext = useContext(InfoContext);
    const { state: infoState, dispatch: infoDispatch } = infoContext;

    const appContext = useContext(AppContext);
    const { state: appState } = appContext;

    const { fetching } = state;

    const { clubs } = infoState;

    const { location, fetchedLocation } = appState;

    const detailOnClickHandler = (club) => {

        if (club) {
            history.push(`/club/${club.clubUuid.toLowerCase()}`);
        }

    }

    const getClubCard = (club) => {

        let today = new Date();

        return (
            <Card title={club.clubName}
                actions={[
                    <DownCircleOutlined key="newPost" />,
                    <MoreOutlined onClick={(e) => detailOnClickHandler(club)} key="more" />,
                    <EnvironmentFilled key="map" />
                ]}>
                {club.icon ? <img width="100" src="https://www.lafitness.com/Pages/Images/LAF_logo_2C_H.gif" style={{ marginBottom: '15px' }} /> : <React.Fragment />}
                <Row>
                    <Col span={4}>
                        <ShopOutlined />
                    </Col>
                    <Col span={20}>{`${club.brand.brandName}`}</Col>
                </Row>
                <Row>
                    <Col span={4}>
                        <EnvironmentFilled />
                    </Col>
                    <Col span={20}>{`${club.address} ${club.city}${club.state ? ' ,' + club.state : ''} ${club.zipCode ? ' ' + club.zipCode : ''}`}</Col>
                </Row>
                <Row>
                    <Col span={4}>
                        <ClockCircleFilled />
                    </Col>
                    <Col span={20}>{club.openHours ? club.openHours[WEEKDAY[today.getDay()]] : 'No Provide Data'}</Col>
                </Row>
                <Row>
                    <Col span={4}>
                        <MehFilled />
                    </Col>
                    <Col span={20}>{club.crowd}</Col>
                </Row>
            </Card>
        );
    }

    const getList = () => {
        if (fetching) {
            return <LoadingList />;
        };

        if (!fetchedLocation) {
            return (
                <Empty
                    description={() => <span>Please allow location permission to check out near clubs</span>}
                />
            );
        }

        const clubCards = clubs ? clubs.map((c, i) => getClubCard(c)) : [];

        return (
            <CardList cards={clubCards} />
        );
    }

    return (
        <div className="clubs">
            {getList()}
        </div>
    )
}

export default Clubs;
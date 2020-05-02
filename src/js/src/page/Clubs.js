import React, { useState, useContext, useEffect } from 'react';

import LoadingList from '../components/list/LoadingList';
import ClubList from '../components/list/ClubList';
import { useHistory } from 'react-router-dom';
import { ClubContext } from '../context/ClubContextProvider';
import { clubContextReducerType } from '../reducer/clubContextReducer';
import { InfoContext } from '../context/InfoContextProvider';
import { AppContext } from '../context/AppContextProvider';
import { PostContext } from '../context/PostContextProvider';
import { Empty, Card, Row, Col, Tooltip, Modal, Button, Spin } from 'antd';
import { EnvironmentFilled, ClockCircleFilled, MehFilled, MoreOutlined, HomeOutlined, DownCircleOutlined, ShopOutlined } from '@ant-design/icons';
import { WEEKDAY, APP_BACKGROUND_COLOR } from '../components/constants';
import CardList from '../components/CardList';
import { newPost, quickPost, getAllClubsWithLocation } from '../api/client';
import { successNotification, errorNotification } from '../components/Notification';

const Clubs = (props) => {

    let history = useHistory();

    const clubContext = useContext(ClubContext);
    const { state: clubState, dispatch: clubDispatch } = clubContext;
    const { fetching, nearClubs: clubs } = clubState;

    const infoContext = useContext(InfoContext);
    const { state: infoState, dispatch: infoDispatch } = infoContext;

    const appContext = useContext(AppContext);
    const { state: appState } = appContext;
    const { location, locationPermission, fetchedLocation, auth } = appState;

    const [newPostModalVisible, setNewPostModalVisible] = useState(false);
    const [newPostModalClub, setNewPostModalClub] = useState(null);
    const [posting, setPosting] = useState(false);

    useEffect(() => {

        if (!clubs || clubs.length === 0) {
            if (location) {

                clubDispatch({ type: clubContextReducerType.FETCHING });

                getAllClubsWithLocation(location.lat, location.lng)
                    .then(res => res.json())
                    .then(res => res.result)
                    .then(clubs => {
                        console.log('fetch clubs', clubs);
                        clubDispatch({ type: clubContextReducerType.SET_CLUBS, payload: clubs });
                    })
                    .catch(err => {
                        console.error("Cannot get clubs", err);
                        errorNotification(err.message, err.message);
                        clubDispatch({ type: clubContextReducerType.FETCH_FAILED });
                    });
            }
        }

    }, [location]);

    const openNewPostModal = () => setNewPostModalVisible(true);
    const closeNewPostModal = () => {
        setNewPostModalClub(null);
        setNewPostModalVisible(false);
        setPosting(false);
    };

    const postOnClickHandler = (club) => {

        if (club) {
            setNewPostModalClub(club);
            openNewPostModal();
        }

    }

    const newPost = () => {
        if (newPostModalClub) {

            setPosting(true);

            quickPost(newPostModalClub.clubUuid)
                .then(res => {
                    successNotification('New Workout Saved');
                    closeNewPostModal();
                })
                .catch(err => {
                    errorNotification('New Workout Save Failed');

                    console.log('New Workout Save Failed', err);
                })
                .finally(() => {
                    setPosting(false);
                })
        }
    }

    const detailOnClickHandler = (club) => {

        if (club) {
            history.push(`/club/${club.clubUuid.toLowerCase()}`);
        }

    }

    const navOnClickHandler = (club) => {

        if (club) {

            const { address, city, state, zipCode } = club;

            const googleMapUrl = "https://www.google.com/maps/place/" + address.replace(',', '').split(" ").join('+') + `+${city}+${state}+${zipCode}`;

            var win = window.open(googleMapUrl, '_blank');
            win.focus();

        }

    }

    const getClubCard = (club) => {

        let today = new Date();

        return (
            <Card title={club.clubName}
                actions={[
                    <Tooltip title="New Exercise Log">
                        <DownCircleOutlined onClick={() => postOnClickHandler(club)} key="newPost" />
                    </Tooltip>,
                    <Tooltip title="Club Detail">
                        <MoreOutlined onClick={(e) => detailOnClickHandler(club)} key="more" />
                    </Tooltip>,
                    <Tooltip title="Navigate By Map">
                        <EnvironmentFilled onClick={(e) => navOnClickHandler(club)} key="map" />
                    </Tooltip>
                ]}>
                {club.icon ? <img width="100" src="https://www.lafitness.com/Pages/Images/LAF_logo_2C_H.gif" style={{ marginBottom: '15px' }} /> : <React.Fragment />}
                <Row>
                    <Col span={4}>
                        <Tooltip title="Brand">
                            <ShopOutlined />
                        </Tooltip>
                    </Col>
                    <Col span={20}>{`${club.brand.brandName}`}</Col>
                </Row>
                <Row>
                    <Col span={4}>
                        <Tooltip title="Address">
                            <EnvironmentFilled />
                        </Tooltip>
                    </Col>
                    <Col span={20}>{`${club.address} ${club.city}${club.state ? ' ,' + club.state : ''} ${club.zipCode ? ' ' + club.zipCode : ''}`}</Col>
                </Row>
                <Row>
                    <Col span={4}>
                        <Tooltip title="Open Hours">
                            <ClockCircleFilled />
                        </Tooltip>
                    </Col>
                    <Col span={20}>{club.openHours ? club.openHours[WEEKDAY[today.getDay()]] : 'No Provide Data'}</Col>
                </Row>
                <Row>
                    <Col span={4}>
                        <Tooltip title="Work out today">
                            <MehFilled />
                        </Tooltip>
                    </Col>
                    <Col span={20}>{club.crowd}</Col>
                </Row>
            </Card>
        );
    }

    const getList = () => {

        if (locationPermission !== 'granted') {
            return (
                <Empty
                    description={() => <Button title="Allow Locatiion" />}>
                    <Button shape="round" href="https://support.google.com/chrome/answer/142065?hl=en" >Location Permission</Button>
                </Empty>
            );
        }

        if (fetching) {
            return <LoadingList />;
        };

        const clubCards = clubs ? clubs.map((c, i) => getClubCard(c)) : [];

        return (
            <CardList cards={clubCards} />
        );
    }

    const newPostModal = () => {

        if (newPostModalClub) {

            const postDisable = !auth.isAuthenticated() || posting;

            return (
                <Modal
                    visible={newPostModalVisible}
                    onCancel={() => closeNewPostModal()}
                    footer={[
                        <Button
                            shape="round"
                            onClick={() => {
                                closeNewPostModal();
                            }}>
                            Cancle
                        </Button>,
                        <Tooltip title={!auth.isAuthenticated() ? "Please login to log your exercise" : null}>
                            <Button
                                disabled={postDisable}
                                shape="round"
                                style={{ color: 'white', backgroundColor: postDisable ? 'grey' : APP_BACKGROUND_COLOR }}
                                onClick={() => {
                                    newPost();
                                }}
                            >
                                {posting ? <Spin /> : 'Workout'}
                            </Button>
                        </Tooltip>
                    ]}
                >
                    <div>
                        <h2>{`New Workout at `}</h2>
                        <h3>{newPostModalClub ? newPostModalClub.clubName : ''}</h3>
                        <p>{newPostModalClub ? newPostModalClub.brand.brandName : ''}</p>
                    </div>
                </Modal>
            );
        }
    }

    return (
        <div className="clubs">
            {getList()}

            {newPostModal()}
        </div>
    )
}

export default Clubs;
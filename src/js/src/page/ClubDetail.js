import React, { useState, useEffect, useContext } from 'react';
import EmptyBar from '../components/chart/EmptyBar';
import SimpleMap from '../components/SimpleMap';
import NewPost from '../page/NewPost.page';
import { Skeleton, Switch, Card, List, Avatar, Row, Col, Button, Modal } from 'antd';
import { getClubDetailWithToken, getClubPosts } from '../api/client';
import { EnvironmentFilled, GlobalOutlined, ClockCircleOutlined, RightOutlined } from '@ant-design/icons';
import PostChart from '../components/chart/PostChart';
import { AppContext } from '../context/AppContextProvider';
import { appContextReducer } from '../reducer/appContextReducer';
import { quickPost } from '../api/client';
import { successNotification, errorNotification } from '../components/Notification';
import { PostContext } from '../context/PostContextProvider';
import moment from 'moment';

const { Meta } = Card;

const weekday = {
    1: 'MONDAY',
    2: 'TUESDAY',
    3: 'WEDNESDAY',
    4: 'THURSDAY',
    5: 'FRIDAY',
    6: 'SATURDAY',
    0: 'SUNDAY'
};

// const getDate = () => {
//     let d = new Date();
//     let year = appendFront(4, d.getFullYear());
//     let month = appendFront(2, d.getMonth() + 1);
//     let date = appendFront(2, d.getDate());

//     return `${year}-${month}-${date}`;
// }

const getDate = (originalD, number, add) => {

    let d;

    if (add) {
        d = new Date(new Date().setDate(originalD.getDate() + number));
    } else {
        d = new Date(new Date().setDate(originalD.getDate() - number));
    }

    let year = appendFront(4, d.getFullYear());
    let month = appendFront(2, d.getMonth() + 1);
    let date = appendFront(2, d.getDate());

    return `${year}-${month}-${date}`;
}

const appendFront = (len, val) => {

    let valStr = val.toString();

    for (let i = valStr.length; i < len; i++) {
        valStr = '0' + valStr;
    }

    return valStr;
}

const ClubDetail = (props) => {

    const appContext = useContext(AppContext);
    const { state, dispatch } = appContext;
    const { authenticated, jwtToken } = state;

    const postContext = useContext(PostContext);
    const { state: postState, dispatch: postDispatch } = postContext;
    const { newPost } = postState;

    const { clubUuid } = props.match.params
    const { currentPosition } = props;

    const [club, setClub] = useState(null);
    const [fetchingChartData, setFetchingChartData] = useState(false);
    const [posts, setPosts] = useState([]);
    const [lastWeekPosts, setLastWeekPosts] = useState([]);
    const [newPostModalVisible, setNewPostModalVisible] = useState(false);

    const fetchClub = () => {
        if (clubUuid) {

            getClubDetailWithToken(clubUuid)
                .then(r => r.json())
                .then(r => r.result)
                .then(club => {
                    console.log('get club by id', club);
                    setClub(club);
                });
        }
    }

    const fetchPostData = () => {

        setFetchingChartData(true);

        getClubPosts(clubUuid, getDate(new Date(), 0, false))
            .then(r => r.json())
            .then(r => r.result)
            .then(posts => setPosts(posts));

        getClubPosts(clubUuid, getDate(new Date(), 7, false))
            .then(r => r.json())
            .then(r => r.result)
            .then(posts => setLastWeekPosts(posts));

        setFetchingChartData(false);

    }

    useEffect(() => {

        fetchClub();

        fetchPostData();

    }, [authenticated]);

    const getQuickPostButton = () => {
        return (
            <Button onClick={() => setNewPostModalVisible(true)} shape="round" size={"medium"} style={{backgroundColor: 'rgb(223, 123, 46)', color: 'white'}}>
                Exercise
            </Button>
        );
    }

    const getMarksChart = () => {
        if (posts.length == 0) {
            return (
                <List.Item>
                    <EmptyBar />

                    {getQuickPostButton()}
                </List.Item>
            );
        }

        return (
            <List.Item>
                <PostChart today={posts} lastWeek={lastWeekPosts} />

                {getQuickPostButton()}

            </List.Item>
        )

    }

    const markOnClick = () => {
        console.log(`mark ${clubUuid}`);

        postDispatch({ type: 'NEW_POST', payload: club });

        
    }

    const getLocationListItem = () => {
        if (!club) {
            return <React.Fragment />
        }

        const { address, city, state, zipCode } = club;

        const googleMapUrl = "https://www.google.com/maps/place/" + address.replace(',', '').split(" ").join('+') + `+${city}+${state}+${zipCode}`;

        return (
            <List.Item>
                <Row >
                    <Col span={2}>
                        <EnvironmentFilled />
                    </Col>
                    <Col span={20}>
                        {`${address} ${city}, ${state} ${zipCode}`}
                    </Col>
                    <Col span={2}>
                        <Button
                            onClick={() => {
                                var win = window.open(googleMapUrl, '_blank');
                                win.focus();
                            }}
                            style={{backgroundColor: 'rgb(223, 123, 46)', color: 'white'}}
                            shape="circle"
                            icon={<RightOutlined />}
                            size="small" />
                    </Col>
                </Row>
            </List.Item>
        );

    }

    const getOpenHourListItem = () => {

        if (!club) {
            return <React.Fragment />
        }

        const { openHours } = club;

        return (
            <List.Item>
                <Row >
                    <Col span={2}>
                        <ClockCircleOutlined />
                    </Col>
                    <Col span={22}>
                        {`${openHours[weekday[new Date().getDay()]]}`}
                    </Col>
                </Row>
            </List.Item>
        );

    }

    const getUrlListItem = () => {
        if (!club) {
            return <React.Fragment />
        }

        const { homeUrl } = club;

        return (
            <List.Item>
                <Row >
                    <Col span={2}>
                        <GlobalOutlined />
                    </Col>
                    <Col span={22}>
                        <a href={homeUrl}>Club Home Page</a>
                        {/* {`${homeUrl}`} */}
                    </Col>
                </Row>
            </List.Item>
        );
    }

    const getMapListItem = () => {
        if (!club) {
            return <React.Fragment />
        }

        return (
            <List.Item>
                <SimpleMap
                    center={{ lat: club.latitude, lng: club.longitude }}
                    mark={{ lat: club.latitude, lng: club.longitude }}
                />
            </List.Item>
        );

    }

    const getDetailList = () => {
        return (
            <List
                itemLayout="vertical"
                dataSource={[club]}
                renderItem={item => (
                    <React.Fragment>
                        {getMarksChart()}
                        {getLocationListItem()}
                        {getOpenHourListItem()}
                        {getUrlListItem()}
                        {getMapListItem()}
                        {/* TODO add amenities */}
                    </React.Fragment>
                )}
            />
        );
    }

    const newPostModalOnCancelHandler = () => {
        setNewPostModalVisible(false);
    }

    if (!club) {
        return (
            <React.Fragment>
                <Card style={{ marginTop: 16 }} loading={true}>
                    <Meta
                        avatar={
                            <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
                        }
                        title="Card title"
                        description="This is the description"
                    />
                </Card>
            </React.Fragment>
        );
    }

    return (
        <div className="club-detail">
            <h2>{club.clubName}</h2>
            <p>{club.brand.brandName}</p>
            {getDetailList()}

            <Modal
                visible={newPostModalVisible}
                // onOk={newPostModalOnCancelHandler}
                onCancel={newPostModalOnCancelHandler}
                footer={[
                    <Button key="cancle" onClick={() => setNewPostModalVisible(false)}>
                        Cancle
                    </Button>,
                    <Button key="post" onClick={() => {
                        quickPost(clubUuid)
                            .then(res => res.json())
                            .then(r => {
                                successNotification('New Post Successed');
                                setNewPostModalVisible(false);
                            })
                            .catch(err => {
                                console.error(err);
                                errorNotification('New Post Failed', err.error);
                            })
                    }}>
                        New Workout Log
                    </Button>
                ]}
            >
                <h2>{`${club.clubName}`}</h2>
                <h4>Time now : {moment().format('YYYY/MM/DD HH:mm:ss')}</h4>
            </Modal>
        </div>
    );
}

export default ClubDetail;
import React, { useState, useEffect } from 'react';
import EmptyBar from '../components/chart/EmptyBar';
import SimpleMap from '../components/SimpleMap';
import { Skeleton, Switch, Card, List, Avatar, Row, Col, Button } from 'antd';
import { getClubDetail } from '../api/client';
import { EnvironmentFilled, GlobalOutlined, ClockCircleOutlined, RightOutlined } from '@ant-design/icons';

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


const ClubDetail = (props) => {

    const { clubUuid } = props.match.params
    const { currentPosition } = props;

    const [club, setClub] = useState(null);
    const [fetchingChartData, setFetchingChartData] = useState(false);

    console.log('Club Detail', clubUuid);

    console.log('current position', currentPosition);

    useEffect(() => {

        if (clubUuid) {

            setFetchingChartData(true);

            getClubDetail(clubUuid).then(r => r.json()).then(club => setClub(club));

            setFetchingChartData(false);
        }

    }, []);

    const getMarksChart = () => {
        if (club && club.marks) {
            return (
                <List.Item>
                    <EmptyBar />

                    <Button onClick={markOnClick} type="primary" shape="round" size={"medium"}>
                        Exercise
                    </Button>
                </List.Item>
            );
        }

    }

    const markOnClick = () => {
        console.log(`mark ${clubUuid}`);
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
                            type="primary"
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
                        {`${homeUrl}`}
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
        <div>
            <h2>{club.name}</h2>
            <p>{club.brand}</p>
            {getDetailList()}

            {/* <EmptyBar />
            <SimpleMap /> */}
        </div>
    );
}

ClubDetail.defaultProps = {
    // club: {
    //     uuid: '',
    //     brand: '',
    //     name: 'ALHAMBRA',
    //     latitude: 0.0,
    //     longitude: 0.0,
    //     address: '',
    //     city: '',
    //     state: '',
    //     zipCode: '',
    //     homeUrl: '',
    //     openHours: {},
    //     distance: 0.0
    // }
};

export default ClubDetail;
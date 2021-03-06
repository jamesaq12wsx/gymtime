import React from 'react';
import { List, Card, Row, Col, Avatar } from 'antd';
import { EnvironmentFilled, ClockCircleFilled, MehFilled, SettingOutlined, EditOutlined, DownCircleTwoTone, MoreOutlined, HomeOutlined } from '@ant-design/icons';

import { useHistory } from 'react-router-dom'
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

const loadingList = [{}, {}, {}];

const ClubList = ({ clubs, markOnClick, detailOnClick }) => {

    console.log('Club List', clubs.slice(0, 10));

    let today = new Date();

    return (
        <List
            grid={{
                gutter: 16,
                xs: 1,
                sm: 2,
                md: 3,
                lg: 3,
                xl: 4,
                xxl: 4,
            }}
            dataSource={clubs.slice(0, 50)}
            renderItem={item => (
                <List.Item>
                    <Card title={item.clubName}
                        actions={[
                            // <DownCircleTwoTone onClick={(e) => markOnClick(e, item)} twoToneColor="#eb2f96" key="check" />,
                            <MoreOutlined onClick={(e) => detailOnClick(e, item)} key="more" />
                        ]}>
                        {item.icon ? <img width="100" src="https://www.lafitness.com/Pages/Images/LAF_logo_2C_H.gif" style={{ marginBottom: '15px' }} /> : <React.Fragment />}
                        <Row>
                            <Col span={4}>
                                <HomeOutlined />
                            </Col>
                            <Col span={20}>{`${item.brand.brandName}`}</Col>
                        </Row>
                        <Row>
                            <Col span={4}>
                                <EnvironmentFilled />
                            </Col>
                            <Col span={20}>{`${item.address} ${item.city}${item.state ? ' ,' + item.state : ''} ${item.zipCode ? ' ' + item.zipCode : ''}`}</Col>
                        </Row>
                        <Row>
                            <Col span={4}>
                                <ClockCircleFilled />
                            </Col>
                            <Col span={20}>{item.openHours ? item.openHours[weekday[today.getDay()]] : 'No Provide Data'}</Col>
                        </Row>
                        <Row>
                            <Col span={4}>
                                <MehFilled />
                            </Col>
                            <Col span={20}>{item.crowd}</Col>
                        </Row>
                    </Card>
                </List.Item>
            )}
        />
    );

}

export default ClubList;
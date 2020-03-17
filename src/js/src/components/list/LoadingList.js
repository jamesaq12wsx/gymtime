import React from 'react';
import { List, Card, Row, Col, Avatar } from 'antd';
import { EnvironmentFilled, ClockCircleFilled, MehFilled, SettingOutlined, EditOutlined, DownCircleTwoTone, MoreOutlined } from '@ant-design/icons';
const { Meta } = Card;

const loadingData = [{},{},{}];

const loadingList = () => {

    return (
        <List
            grid={{
                gutter: 16,
                xs: 1,
                sm: 2,
                md: 4,
                lg: 4,
                xl: 6,
                xxl: 3,
            }}
            dataSource={loadingData}
            renderItem={item => (
                <List.Item>
                    <Card loading={true}>
                        <Meta
                            avatar={
                                <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
                            }
                            title="Card title"
                            description="This is the description"
                        />
                    </Card>
                </List.Item>
            )}
        />
    );

}

export default loadingList;
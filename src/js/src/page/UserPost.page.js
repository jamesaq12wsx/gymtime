import React, { useState, useEffect } from 'react';
import { Calendar, Badge, Select, Radio, Col, Row, Button, Card, Descriptions } from 'antd';
import moment from 'moment';
import { getUserYearPost } from '../api/client';
import { errorNotification } from '../components/Notification';
import { EditOutlined, DeleteOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import './UserPost.page.css';
import CardList from '../components/CardList';

const { Group, Button: RadioButton } = Radio;
const { Meta } = Card;


function onPanelChange(value, mode) {
    console.log(value, mode);
}

const UserPost = () => {

    const [today, setToday] = useState(moment())
    const [selectedDate, setSelectedDate] = useState(moment());
    const [posts, setPosts] = useState([]);
    const [selectedDatePosts, setSelectedDatePosts] = useState([]);

    useEffect(() => {
        getUserYearPost(moment().format('YYYY'))
            .then(res => res.json())
            .then(posts => {
                setPosts(posts);
                setSelectedDateHandler(selectedDate);
            })
            .catch(err => {
                errorNotification('Fetch Posts Error', err.message);
            });
    }, []);

    // useEffect(() => {
    //     setSelectedDatePosts(posts.filter(p => moment(p.postDate).format('YYYY-MM-dd') === selectedDate.format('YYYY-MM-dd')));
    // }, [selectedDate]);

    const setSelectedDateHandler = (date) => {
        setSelectedDate(date);
        const datePosts = posts.filter(p => moment(p.postTime).format('YYYY-MM-DD') === date.format('YYYY-MM-DD'));

        setSelectedDatePosts(datePosts);
    }

    const dateCellRender = (date) => {

        return (
            <React.Fragment>
                {posts.filter(p => moment(p.postTime).format('YYYY-MM-DD') === date.format('YYYY-MM-DD')).length > 0 ?
                    <Badge status="warning" /> :
                    <Badge />}
            </React.Fragment>
        );

    }

    const onPanelChange = (value) => {
        setToday(value);
    };

    const getCalendar = () => {
        return (
            <div className="site-calendar-demo-card">
                <Calendar
                    headerRender={({ value, type, onChange, onTypeChange }) => {
                        const start = 0;
                        const end = 12;
                        const monthOptions = [];

                        const current = value.clone();
                        const localeData = value.localeData();
                        const months = [];
                        for (let i = 0; i < 12; i++) {
                            current.month(i);
                            months.push(localeData.monthsShort(current));
                        }

                        for (let index = start; index < end; index++) {
                            monthOptions.push(
                                <Select.Option className="month-item" key={`${index}`}>
                                    {months[index]}
                                </Select.Option>,
                            );
                        }
                        const month = value.month();

                        const year = value.year();
                        const options = [];
                        for (let i = year - 10; i < year + 10; i += 1) {
                            options.push(
                                <Select.Option key={i} value={i} className="year-item">
                                    {i}
                                </Select.Option>,
                            );
                        }
                        return (
                            <div style={{ padding: 10 }}>
                                {/* <div style={{ marginBottom: '10px' }}>Custom header </div> */}
                                <Row style={{ flexWrap: 'nowrap' }} gutter={8}>
                                    <Col
                                    // style={{ flex: 'none' }}
                                    >
                                        <Group size="small" onChange={e => onTypeChange(e.target.value)} value={type}>
                                            <RadioButton value="month">Month</RadioButton>
                                            <RadioButton value="year">Year</RadioButton>
                                        </Group>
                                    </Col>
                                    <Col
                                    // style={{ flex: 'auto' }}
                                    >
                                        <Select
                                            size="small"
                                            dropdownMatchSelectWidth={false}
                                            className="my-year-select"
                                            onChange={newYear => {
                                                const now = value.clone().year(newYear);
                                                onChange(now);
                                            }}
                                            value={String(year)}
                                        >
                                            {options}
                                        </Select>
                                    </Col>
                                    <Col
                                    // style={{ flex: 'auto' }}
                                    >
                                        <Select
                                            size="small"
                                            dropdownMatchSelectWidth={false}
                                            value={String(month)}
                                            onChange={selectedMonth => {
                                                const newValue = value.clone();
                                                newValue.month(parseInt(selectedMonth, 10));
                                                onChange(newValue);
                                            }}
                                        >
                                            {monthOptions}
                                        </Select>
                                    </Col>
                                    <Col
                                        style={{ flex: 'auto' }}
                                    >
                                        <Button
                                            onClick={() => {
                                                const newDate = selectedDate.clone().add(-1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                                // onChange(selectedDate.add(-1, 'month'))
                                            }}
                                        >-</Button>
                                        <Button
                                            onClick={() => {
                                                const newDate = selectedDate.clone().add(1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                            }}
                                        >+</Button>
                                    </Col>
                                </Row>
                            </div>
                        );
                    }}
                    value={today}
                    selectedDate={selectedDate}
                    onSelect={(date) => {
                        setSelectedDateHandler(date);
                    }}
                    onPanelChange={onPanelChange}
                    dateCellRender={dateCellRender}
                    fullscreen={false}
                    onPanelChange={onPanelChange}
                />
            </div>
        );
    }

    const getExercises = (p) => {
        if(p.exercises && Object.keys(p.exercises).length != 0){

        const exercises = Object.keys(p.exercises).map(key => <Descriptions.Item label={key}>{p.exercises[key]}</Descriptions.Item>)

            return (
                <Descriptions title="Exercises">
                    {exercises}
                </Descriptions>
            )
        }else{
            return(
                <Descriptions title="No Exercises" />
            );
        }
    }

    const getPostCards = () => {
        return selectedDatePosts.map(p => {
            return (
                <Card
                    actions={[
                        <DeleteOutlined key="delete" />,
                        <EditOutlined key="edit" />,
                    ]}
                >
                    <Meta title={p.clubName} description={moment(p.postTime).format('LT')} />
                    <br />
                    {getExercises(p)}
                </Card>
            )
        })
    }

    return (
        <div className="user-post">
            <h3>Daily Workout</h3>
            {getCalendar()}
            <div className="day-post">
                <CardList className="daily-post-list" cards={getPostCards()} />
            </div>
        </div>
    );
}

export default UserPost;
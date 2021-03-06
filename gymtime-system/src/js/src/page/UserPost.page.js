import React, { useState, useEffect, useContext } from 'react';
import { Calendar, Badge, Select, Radio, Col, Row, Button, Card, Descriptions, Modal, List, Tooltip } from 'antd';
import moment from 'moment';
import { getUserPost, deletePost, updatePost } from '../api/client';
import { errorNotification } from '../components/Notification';
import { EditOutlined, DeleteOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import CardList from '../components/CardList';
import './UserPost.page.css';
import { useHistory } from 'react-router-dom';
import { PostContext } from '../context/PostContextProvider';
import { AppContext } from '../context/AppContextProvider';
import { SELECT_POST, DESELECT_POST, SET_POSTS } from '../reducer/postContextReducer';
import PostEdit from './PostEdit.page';
import { convertUnit } from '../util';
import { SET_USER } from '../reducer/appContextReducer';
var _ = require('lodash');

const { Group, Button: RadioButton } = Radio;
const { Meta } = Card;


function onPanelChange(value, mode) {
    console.log(value, mode);
}

const UserPost = (props) => {

    const [today, setToday] = useState(moment())
    const [selectedDate, setSelectedDate] = useState(moment());
    // const [posts, setPosts] = useState([]);
    const [selectedDatePosts, setSelectedDatePosts] = useState([]);
    const [deleteModalVisible, setDeletedModalVisible] = useState(false);
    const [selectDeletePost, setSelectDeletePost] = useState(null);

    const history = useHistory();

    const postContext = useContext(PostContext);
    const { state: postState, dispatch } = postContext;
    const { posts, fetched } = postState;

    const appContext = useContext(AppContext);
    const { state: appState, dispatch: appDispatch } = appContext;
    const { auth, currentUser } = appState;

    useEffect(() => {
        if (!fetched) {
            getYearPost(moment().format('YYYY'));
        }

        dispatch({ type: DESELECT_POST });

    }, []);

    useEffect(() => {
        if (!currentUser) {
            auth.fetchCurrentUser((user) => {
                appDispatch({ type: SET_USER, payload: user });
            }, (err) => {
                errorNotification('Fetch User Info Failed', err.error.message);
            });
        }
    }, []);

    useEffect(() => {

        const datePosts = posts.filter(p => moment(p.exerciseTime).format('YYYY-MM-DD') === selectedDate.format('YYYY-MM-DD'));

        setSelectedDatePosts(datePosts);

    }, [selectedDate]);

    const postCardEditClickHandler = (post) => {

        dispatch({ type: SELECT_POST, payload: post });

        history.push(`/post/${post.postUuid}`);

    }

    const getYearPost = (year) => {
        getUserPost(year)
            .then(res => res.json())
            .then(res => res.result)
            .then(posts => {
                dispatch({ type: SET_POSTS, payload: posts });

                setSelectedDate(moment());
            })
            .catch(err => {
                errorNotification('Fetch Posts Error', err.error.message);
            });
    }

    const openDeleteModal = () => setDeletedModalVisible(true);
    const closeDeleteModal = () => setDeletedModalVisible(false);

    const setSelectedDateHandler = (date) => {
        setSelectedDate(date);
    }

    const dateCellRender = (date) => {

        if (!posts) {
            return <React.Fragment />
        }

        return (
            <React.Fragment>
                {posts.filter(p => moment(p.exerciseTime).format('YYYY-MM-DD') === date.format('YYYY-MM-DD')).length > 0 ?
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
            <div className="post-calendar">
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
                                <Row
                                    justify="space-between"
                                    style={{ flexWrap: 'nowrap' }}
                                    gutter={8}>
                                    <Col
                                    // span={6}
                                    // style={{ flex: 'none' }}
                                    >
                                        <Group size="small" onChange={e => onTypeChange(e.target.value)} value={type}>
                                            <RadioButton value="month">Month</RadioButton>
                                            <RadioButton value="year">Year</RadioButton>
                                        </Group>
                                    </Col>
                                    <Col
                                        span={4}
                                    >
                                        <Select
                                            size="small"
                                            style={{ width: 100 }}
                                            // dropdownMatchSelectWidth={false}
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
                                    // span={4}
                                    // style={{ flex: 'auto' }}
                                    >
                                        <Select
                                            size="small"
                                            style={{ width: '5rem' }}
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
                                    // span={6}
                                    >
                                        <Tooltip
                                            title="last month">
                                            <Button
                                                size='small'
                                                onClick={() => {
                                                    const newDate = selectedDate.clone().add(-1, 'month');
                                                    setToday(newDate);
                                                    setSelectedDate(newDate);
                                                    // onChange(selectedDate.add(-1, 'month'))
                                                }}
                                            >{"<"}</Button>
                                        </Tooltip>
                                        <Tooltip 
                                            title="next month">
                                            <Button
                                                size='small'
                                                onClick={() => {
                                                    const newDate = selectedDate.clone().add(1, 'month');
                                                    setToday(newDate);
                                                    setSelectedDate(newDate);
                                                }}
                                            >{">"}</Button>
                                        </Tooltip>

                                    </Col>
                                    {/* <Col
                                        span={2}
                                    >
                                        <Button
                                            size='small'
                                            onClick={() => {
                                                const newDate = selectedDate.clone().add(1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                            }}
                                        >+</Button>

                                    </Col> */}
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

        if (p.records && p.records.length !== 0) {

            const records = p.records.map((r, i) => {

                const { measurementType } = r.exercise;
                let description = '';

                switch (measurementType.toLowerCase()) {
                    case 'weight':
                        description = `${convertUnit(currentUser.userUnitSetting.weightUnit, r.measurementUnit, r.weight)} ${getWeightUnitAlias()} x ${r.reps} reps`;
                        break;
                    case 'distance':
                        description = `${convertUnit(currentUser.userUnitSetting.distanceUnit, r.measurementUnit, r.distance)} ${getDistanceUnitAlias()} x ${r.min} mins`;
                        break;
                    case 'duration':
                        description = `${r.duration} ${r.measurementUnit.alias} x ${r.reps} reps`;
                        break;
                    default:
                        console.error(`recorde type ${measurementType} error`);
                }

                return (
                    <React.Fragment key={i}>
                        <Descriptions.Item key={i}>{`${r.exercise.name} - ${description}`}</Descriptions.Item>
                        <br />
                    </React.Fragment>
                );
            });

            return records;

        } else {
            return (
                <Descriptions title="No Exercises" />
            );
        }
    }

    const getPostCards = () => {

        return selectedDatePosts.map(p => {

            return (
                <Card
                    id={p.postUuid}
                    actions={[
                        <DeleteOutlined onClick={() => {
                            setSelectDeletePost(p);
                            openDeleteModal();
                        }} key="delete" />,
                        <EditOutlined
                            key="edit"
                            onClick={() => postCardEditClickHandler(p)}
                        />,
                    ]}
                >
                    <Meta
                        title={
                            <div className="post-card-title">
                                <h4 className="post-card-title-club-name">{p.club.clubName}</h4>
                                <p className="post-card-title-club-brand">{_.get(p, 'club.brand.brandName', '')}</p>
                            </div>
                        }
                        description={moment(p.exerciseTime).format('YYYY/MM/DD HH:mm')} />
                    <br />
                    {getExercises(p)}
                </Card>
            )
        });
    }

    const getWeightUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.weightUnit.alias;
        }
    }

    const getDistanceUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.distanceUnit.alias;
        }
    }

    const cards = getPostCards();

    return (
        auth.currentUser ?
            <div className="user-post">
                {getCalendar()}

                <br />

                <div className="day-post">
                    <CardList className="daily-post-list" cards={getPostCards()} />
                </div>

                <Modal
                    title="DELETE POST"
                    visible={deleteModalVisible}
                    onOk={() => closeDeleteModal()}
                    onCancel={() => {
                        closeDeleteModal();
                        setSelectDeletePost(null);
                    }}
                    footer={[
                        <Button key="cancel" shape='round' onClick={() => {
                            setSelectDeletePost(null);
                            closeDeleteModal();
                        }}>
                            Cancel
                    </Button>,
                        <Button key="delete" shape='round' type='danger' onClick={() => {
                            // TODO: DELETE POST
                            deletePost(selectDeletePost.postUuid)
                                .then(res => {
                                    closeDeleteModal();
                                    getYearPost(selectedDate.format('YYYY'));
                                })
                                .catch(err => {
                                    errorNotification('Delete Fail', 'Cannot delete post');
                                    console.error(err);
                                });

                        }}>
                            DELETE
                    </Button>,
                    ]}
                >
                    <p>Are you sure you want to delete this post?</p>
                    <p>{selectDeletePost ? `${selectDeletePost.club.clubName}-${selectDeletePost.club.brand.brandName}` : ''}</p>
                    <p>{selectDeletePost ? moment(selectDeletePost.postTime).format('YYYY-MM-DD HH:mm') : ''}</p>
                </Modal>
            </div> :
            <React.Fragment />
    );
}

export default UserPost;
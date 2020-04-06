import React, { useState, useEffect, useContext } from 'react';
import { Calendar, Badge, Select, Radio, Col, Row, Button, Card, Descriptions, Modal, List } from 'antd';
import moment from 'moment';
import { getUserPost, deletePost, updatePost } from '../api/client';
import { errorNotification } from '../components/Notification';
import { EditOutlined, DeleteOutlined, EllipsisOutlined, SettingOutlined } from '@ant-design/icons';
import CardList from '../components/CardList';
import './UserPost.page.css';
import { useHistory } from 'react-router-dom';
import { PostContext } from '../context/PostContextProvider';
import PostEdit from './PostEdit.page';
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

    const { posts, editing, editingPost, editingPostChanged } = postState;

    useEffect(() => {

        getYearPost(moment().format('YYYY'));

    }, []);

    useEffect(() => {

        const datePosts = posts.filter(p => moment(p.exerciseTime).format('YYYY-MM-DD') === selectedDate.format('YYYY-MM-DD'));

        // console.log('datePosts', datePosts);

        setSelectedDatePosts(datePosts);

    }, [selectedDate]);

    const setPosts = (posts) => {
        dispatch({ type: 'SET_POSTS', payload: posts });
    }

    const getYearPost = (year) => {
        getUserPost(year)
            .then(res => res.json())
            .then(posts => {
                setPosts(posts);

                const datePosts = posts.filter(p => moment(p.exerciseTime).format('YYYY-MM-DD') === selectedDate.format('YYYY-MM-DD'));

                setSelectedDatePosts(datePosts);
            })
            .catch(err => {
                errorNotification('Fetch Posts Error', err.message);
            });
    }

    // useEffect(() => {
    //     setSelectedDatePosts(posts.filter(p => moment(p.postDate).format('YYYY-MM-dd') === selectedDate.format('YYYY-MM-dd')));
    // }, [selectedDate]);

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
                                        span={6}
                                    // style={{ flex: 'none' }}
                                    >
                                        <Group size="small" onChange={e => onTypeChange(e.target.value)} value={type}>
                                            <RadioButton value="month">Month</RadioButton>
                                            <RadioButton value="year">Year</RadioButton>
                                        </Group>
                                    </Col>
                                    <Col
                                        span={4}
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
                                        span={4}
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
                                        span={6}
                                    >
                                        <Button
                                            size='small'
                                            onClick={() => {
                                                const newDate = selectedDate.clone().add(-1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                                // onChange(selectedDate.add(-1, 'month'))
                                            }}
                                        >-</Button>
                                        <Button
                                            size='small'
                                            onClick={() => {
                                                const newDate = selectedDate.clone().add(1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                            }}
                                        >+</Button>

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
        if (p.exercises && getExercises.length != 0) {

            const exercises = p.exercises.map((ex, i) => <Descriptions.Item key={i}>{`${ex.name} - ${ex.description}`}</Descriptions.Item>)

            return (
                <Descriptions title="Exercises">
                    {exercises}
                </Descriptions>
            )
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
                            onClick={() => {
                                console.log('edit post on click', p);
                                dispatch({ type: 'EDITING', post: p});
                            }}
                        />,
                    ]}
                >
                    <Meta
                        title={
                            <div className="post-card-title">
                                <h4 className="post-card-title-club-name">{p.club.clubName}</h4>
                                <p className="post-card-title-club-brand">{ _.get(p, 'club.brand.brandName', '')}</p>
                            </div>
                        }
                        description={moment(p.exerciseTime).format('YYYY/MM/DD HH:mm')} />
                    <br />
                    {getExercises(p)}
                </Card>
            )
        })
    }

    const couldSave = () => {
        if (editing) {
            return _.isEqual(editingPostChanged, editingPost);
        } else {
            return false;
        }
    }

    const cards = getPostCards();

    return (
        <div className="user-post">
            {getCalendar()}

            <br />

            <div className="day-post">
                <CardList className="daily-post-list" cards={cards} />
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

            <Modal
                title="EDIT POST"
                visible={editing}
                onOk={() => closeDeleteModal()}
                onCancel={() => {
                    dispatch({ type: 'FINISH_EDIT' });
                }}
                style={{
                    width: '80%',
                    height: '80%'
                }}
                footer={[
                    <Button key="cancel" shape='round' onClick={() => {
                        dispatch({ type: 'FINISH_EDIT' });
                    }}>
                        CANCEL
                    </Button>,
                    <Button
                        key="save"
                        shape='round'
                        type='primary'
                        onClick={() => {
                            // TODO: SAVE POST
                            updatePost(editingPostChanged)
                                .then(res => {
                                    dispatch({ type: 'FINISH_EDIT' });
                                    getYearPost(selectedDate.format('YYYY'));
                                })
                                .catch(err => {
                                    errorNotification('Update Fail', err.message);
                                });
                        }}
                        disabled={couldSave()}
                    >
                        SAVE
                    </Button>,
                ]}
            >
                {editing ? <PostEdit /> : <React.Fragment />}
            </Modal>
        </div>
    );
}

export default UserPost;
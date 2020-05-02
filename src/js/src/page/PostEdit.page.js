import React, { useState, useContext, useEffect } from 'react';
import { Button, Input, Tag, Row, Col, Select, TreeSelect, Card, Modal, InputNumber, Spin } from 'antd';
import { EditOutlined, DeleteOutlined } from '@ant-design/icons';
import { Link, useHistory } from 'react-router-dom';
import { PostContext } from '../context/PostContextProvider';
import PostForm from '../components/form/PostForm';
import { updatePost, getUserPost, getPostByUuid, newPostRecord, deletePostRecord } from '../api/client';
import { errorNotification, successNotification } from '../components/Notification';
import { SELECT_POST } from '../reducer/postContextReducer';
import moment from 'moment';
import CardList from '../components/CardList';
import Container from '../components/Container';
import ExerciseSelect from '../components/ExerciseSelect';
import { AppContext } from '../context/AppContextProvider';
import { SET_USER } from '../reducer/appContextReducer';
import { APP_BACKGROUND_COLOR } from '../components/constants';

const { Meta } = Card;

var _ = require('lodash');

const inputBottomStyle = { marginBottom: '5px' };

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const { Option } = Select;
const { TreeNode } = TreeSelect;

const timeFormat = 'HH:mm';

const PostEdit = (props) => {

    const history = useHistory();

    const postContext = useContext(PostContext);
    const appContext = useContext(AppContext);

    const { state: postState, dispatch: postDispatch } = postContext;
    const { state: appState, dispatch: appDispatch } = appContext;

    const { selectedPost } = postState;
    const { auth, currentUser } = appState;

    const [post, setPost] = useState(selectedPost || null);
    const [weightUnit, setWeightUnit] = useState(null);
    const [distanceUnit, setDistanceUnit] = useState(null);

    const { postUuid } = props.match.params

    const [newRecordModalVisible, setNewRecordModalVisible] = useState(false);
    const [newRecordExercise, setNewRecordExercise] = useState(null);
    const [newRecordDataOne, setNewRecordDataOne] = useState(0);
    const [newRecordDataTwo, setNewRecordDataTwo] = useState(0);
    const [updatingNewRecord, setUpdatingNewRecord] = useState(false);


    useEffect(() => {
        if (!post) {
            fetchPost(postUuid);
        }

        if (!currentUser) {
            fetchUser();
        }
    }, []);

    useEffect(() => {
        if (currentUser) {
            const user = auth.currentUser;
            const { weightUnit, distanceUnit } = user.userUnitSetting;
            setWeightUnit(weightUnit);
            setDistanceUnit(distanceUnit);
            // setWeightUnit(auth.currentUser.userUnitSetting.weightUnit);
        }
    }, [auth]);

    const openNewRecordModal = () => setNewRecordModalVisible(true);

    const closeNewRecordModal = () => {
        setNewRecordModalVisible(false);
        setNewRecordExercise(null);
        setNewRecordDataOne(0);
        setNewRecordDataTwo(0);
        setUpdatingNewRecord(false);
    };

    const fetchUser = () => {
        auth.fetchCurrentUser((user) => {
            appDispatch({ type: SET_USER, payload: user });
        }, (err) => {
            errorNotification('Fetch User Info Failed', err.error.message);
        });
    }

    const fetchPost = (uuid) => {
        getPostByUuid(uuid)
            .then(res => res.json())
            .then(res => {
                setPost(res.result);
                postDispatch({ type: SELECT_POST, payload: res.result });
            })
            .catch(err => {
                errorNotification('Fetch Post Failed', err.error);
                history.push('/');
            });
    }

    const deleteRecord = (uuid) => {
        //TODO: delete record
        console.log('delete record', uuid);

        deletePostRecord(postUuid, uuid)
            .then(res => res.json())
            .then(res => {
                successNotification('Delete Exercise');
                fetchPost(postUuid);
            })
            .catch(err => {
                errorNotification('Delete Record Failed', err.error);
            });
    }

    const getRecordDescript = (r) => {
        if (r) {
            switch (r.measurementUnit.measurementType.toLowerCase()) {
                case 'weight':
                    return `${r.weight} ${r.measurementUnit.alias} x ${r.reps} reps`;
                case 'distance':
                    return `${r.distance} ${r.measurementUnit.alias} x ${r.min} mins`;
                case 'duration':
                    return `${r.duration} ${r.measurementUnit.alias} x ${r.reps} reps`;
            }
        }
    }

    const getRecordCards = () => {

        const { records } = post;

        if (records) {
            return records.map((r, i) => {
                return (
                    <Card
                        actions={[
                            <DeleteOutlined onClick={() => {
                                deleteRecord(r.uuid);
                            }} key="delete" />
                        ]}
                    >
                        <Meta
                            title={r.exercise.name}
                            description={getRecordDescript(r)}
                        />
                    </Card>
                )
            })
        }
    }

    const getRecordCardList = () => {

        const cards = getRecordCards();

        return (
            <Container>
                <CardList className='record-card-list' cards={cards} locale={{ emptyText: 'Log New Exercise' }} />
            </Container>
        )
    }

    const getWeightUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.weightUnit.alias;
        }
    }

    const getDistanceUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.distanceUnit.alias;
        }
    }

    const getExerciseDataInput = () => {
        if (newRecordExercise) {
            return (
                <Input.Group
                    style={{ marginTop: 10 }}
                    className="new-exercise-data">
                    <Row gutter={12} justify="space-around">
                        <Col span={10}>
                            <span>{newRecordExercise.measurementType.toLowerCase() === 'weight' ? getWeightUnitAlias() : getDistanceUnitAlias()}</span>
                            <InputNumber
                                min={0}
                                defaultValue={10}
                                value={newRecordDataOne}
                                onChange={(value) => setNewRecordDataOne(value)}
                                placeholder={newRecordExercise.measurementType.toLowerCase() === 'weight' ? getWeightUnitAlias() : getDistanceUnitAlias()}
                            />
                        </Col>
                        <Col span={10}>
                            <span>{newRecordExercise.measurementType.toLowerCase() === 'weight' ? 'Reps.' : 'Mins'}</span>
                            <InputNumber
                                min={0}
                                defaultValue={10}
                                value={newRecordDataTwo}
                                onChange={(value) => setNewRecordDataTwo(value)}
                                placeholder={newRecordExercise.measurementType.toLowerCase() === 'weight' ? 'Reps.' : 'Mins'}
                            />

                        </Col>
                    </Row>
                </Input.Group>
            );
        }
    }

    const newRecordModal = () => {

        const saveDisable = updatingNewRecord || !newRecordExercise;
        const saveBackgroundColor = saveDisable ? 'lightgray' : APP_BACKGROUND_COLOR;

        return (
            <Modal
                title="New Exercise"
                visible={newRecordModalVisible}
                onCancel={() => closeNewRecordModal()}
                footer={[
                    <Button
                        className="cancle-new-exercise-btn"
                        key="cancle"
                        shape="round"
                        onClick={() => closeNewRecordModal()}>
                        Cancle
                    </Button>,
                    <Button
                        shape="round"
                        className="new-exercise-btn"
                        type="primary"
                        key="newRecord"
                        disabled={saveDisable}
                        style={{ color: 'white', backgroundColor: saveBackgroundColor }}
                        onClick={() => {
                            console.log("New Record");
                            if (newRecordExercise) {
                                let newRecord = {};
                                newRecord.exerciseId = newRecordExercise.id;
                                switch (newRecordExercise.measurementType.toLowerCase()) {
                                    case 'weight':

                                        newRecord.weight = newRecordDataOne;
                                        newRecord.reps = newRecordDataTwo;

                                        break;
                                    case 'distance':

                                        newRecord.distance = newRecordDataOne;
                                        newRecord.min = newRecordDataTwo;

                                        break;
                                    case 'duration':
                                        // TODO: duration type workout
                                        break;
                                }

                                newPostRecord(post.postUuid, newRecord)
                                    .then(res => res.json())
                                    .then(res => {
                                        closeNewRecordModal();
                                        successNotification('New Exercise Saved');
                                        fetchPost(post.postUuid);
                                    }).catch(err => {
                                        errorNotification('New Record Failed', err.error);
                                    })

                            } else {
                                errorNotification('New Exercise Failed', 'You need to select Exercise');
                            }
                        }}>
                        {updatingNewRecord ? <Spin /> : 'Save'}
                    </Button>
                ]}
            >
                <ExerciseSelect onChange={(ex) => {
                    setNewRecordExercise(ex);
                    setNewRecordDataOne(0);
                    setNewRecordDataTwo(0);
                }} style={{ width: '100%' }} />

                <br />

                {getExerciseDataInput()}
            </Modal>
        );
    }

    if (!post) {
        return <React.Fragment />
    }

    return (
        <React.Fragment>
            <h2>{post.club.clubName}</h2>
            <span>{post.club.brand.brandName}</span>
            <br />
            <h3>{moment(post.exerciseTime).format("YYYY/MM/DD HH:mm")}</h3>
            {getRecordCardList()}
            <Button onClick={() => openNewRecordModal()} shape="round" style={{ color: 'white', backgroundColor: 'rgb(223, 123, 46)' }}>New Exercise</Button>

            {newRecordModal()}
        </React.Fragment>
    );

}

export default PostEdit;
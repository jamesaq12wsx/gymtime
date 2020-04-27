import React, { useContext, useEffect, useState } from 'react';
import { Button, Row, Avatar, Badge, List, Tooltip, Col, Modal, InputNumber, Input, Typography, Radio, Upload, Spin, DatePicker } from 'antd';
import { UserOutlined, GoogleOutlined, FacebookFilled, ProfileOutlined, CameraOutlined, FileImageOutlined, CloseOutlined, SolutionOutlined } from '@ant-design/icons';
import { FaBirthdayCake, FaTransgender } from "react-icons/fa";
import { useHistory, useRouteMatch, Switch, Route } from 'react-router-dom';
import { AppContext } from '../context/AppContextProvider';
import { SET_BODY_STAT, SET_USER } from '../reducer/appContextReducer';
import { fetchUserBodyStat, changeUserHeight, newUserWeight, newUserBodyFat, deleteWeight, deleteBodyFat, postUserHeightUnit, postUserWeightUnit, postUserDistanceUnit, updateUserName, updateUserBirthday, updateUserGender, updateUserPicture } from '../api/client';
import { errorNotification, successNotification } from '../components/Notification';
import { convertUnit } from '../util';
import WeightChart from '../components/chart/WeightChart';
import BodyFatChart from '../components/chart/BodyFatChart';
import DeleteIconButton from '../components/DeleteIconButton';
import { APP_BACKGROUND_COLOR } from '../components/constants';
import moment from 'moment';

const { Dragger } = Upload;

const User = (props) => {

    let match = useRouteMatch();

    const appContext = useContext(AppContext);

    return (
        <Switch>
            <Route exact path={`${match.path}/unit`} component={UserUnitSetting} />
            <Route exact path={`${match.path}/stat`} component={UserBodyStat} />
            <Route path="/" component={UserHome} />
        </Switch>
    );
};

const UserHome = () => {
    let history = useHistory();
    let match = useRouteMatch();

    const appContext = useContext(AppContext);
    const { state: appState, dispatch: appDispatch } = appContext;
    const { auth } = appState;
    const { currentUser } = appState;

    const [uploadingPic, setUploadingPic] = useState(false);

    const [uploadPic, setUploadPic] = useState(null);

    const [userPicUploadModalVisible, setUserPicUploadModalVisible] = useState(false);

    const [userNameModalVisible, setUserNameModalVisible] = useState(false);
    const [userUpdateName, setUserUpdateName] = useState('');
    const [updatingName, setUpdatingName] = useState(false);

    const [userBirthdayModalVisible, setUserBirthdayModalVisible] = useState(false);
    const [userUpdateBirthday, setUserUpdateBirthday] = useState(null);
    const [updatingBirthday, setUpdatingBirthday] = useState(false);

    const [userGenderModalVisible, setUserGenderModalVisible] = useState(false);
    const [userUpdateGender, setUserUpdateGender] = useState(null);
    const [updatingGender, setUpdatingGender] = useState(false);

    useEffect(() => {
        if (!currentUser) {
            fetchCurrentUser();
        }
    }, []);

    const openUploadPicModal = () => setUserPicUploadModalVisible(true);
    const closeUploadPicModal = () => {
        setUserPicUploadModalVisible(false);
        setUploadingPic(false);
        setUploadPic(null);
    };

    const openUserNameModal = () => {
        setUserNameModalVisible(true);
        setUserUpdateName(currentUser.name ? currentUser.name : '');
    };
    const closeUserNameModal = () => {

        setUserNameModalVisible(false);
        setUserUpdateName('');
        setUpdatingName(false);
    };

    const openUserBirthdayModal = () => {
        setUserBirthdayModalVisible(true);
        if (currentUser) {
            const { userInfo } = currentUser;
            if (userInfo && userInfo.birthday) {
                setUserUpdateBirthday(moment(currentUser.userInfo.birthday));
            }
        }
    }

    const closeUserBirthdayModal = () => {
        setUserBirthdayModalVisible(false);
        setUserUpdateBirthday(null);
        setUpdatingBirthday(false);
    }

    const openUserGenderModal = () => {
        setUserGenderModalVisible(true);
        if (currentUser) {
            const { userInfo } = currentUser;
            if (userInfo && userInfo.gender) {
                setUserUpdateGender(userInfo.gender.toLowerCase());
            }
        }
    }

    const closeUserGenderModal = () => {
        setUserGenderModalVisible(false);
        setUserUpdateGender(null);
        setUpdatingGender(false);
    }

    const fetchCurrentUser = () => {
        auth.fetchCurrentUser((user) => {
            appDispatch({ type: SET_USER, payload: user });
        }, (err) => {
            errorNotification('Fetch User Info Failed', err.error.message);
            console.error(err);
        })
    }

    const getAuthLocgo = () => {

        if (currentUser) {

            const logoStyle = { fontSize: '2rem' };

            switch (currentUser.authProvider.toLowerCase()) {
                case 'google':
                    return (
                        <Tooltip title="Login by Google">
                            <GoogleOutlined style={logoStyle} />
                        </Tooltip>
                    );
                case 'facebook':
                    return <FacebookFilled style={logoStyle} />;
                case 'local':
                    return (
                        <Tooltip title="Upload Picture">
                            <CameraOutlined style={logoStyle} onClick={() => openUploadPicModal()} />
                        </Tooltip>
                    );
            }
        }

    }

    const getUserImage = () => {
        if (currentUser) {
            return (
                <React.Fragment>
                    <Row className="user-image-row" justify="center">
                        <Badge offset={[0, 200]} count={currentUser.authProvider ? getAuthLocgo() : <React.Fragment />} >
                            {currentUser.imageUrl ? <Avatar size={200} src={currentUser.imageUrl} /> : <Avatar size={200} icon={<UserOutlined />} />}
                        </Badge>
                    </Row>
                </React.Fragment>
            );
        }
    }

    const getUserSetting = () => {
        if (currentUser) {
            return (
                <List
                    className="user-setting"
                    itemLayout="horizontal"
                    locale={{ emptyText: 'No Setting' }}
                >
                    <List.Item
                        key="name"
                        onClick={() => openUserNameModal()}
                    >
                        <List.Item.Meta
                            avatar={<Avatar style={{ backgroundColor: APP_BACKGROUND_COLOR }} icon={<UserOutlined />} />}
                            title={currentUser.name ? currentUser.name : ''}
                        />
                    </List.Item>

                    <List.Item
                        key="birthday"
                        onClick={() => openUserBirthdayModal()}
                    >
                        <List.Item.Meta
                            avatar={<Avatar style={{ backgroundColor: APP_BACKGROUND_COLOR }} icon={<FaBirthdayCake />} />}
                            title={currentUser.userInfo ? currentUser.userInfo.birthday ? currentUser.userInfo.birthday : '' : ''}
                        />
                    </List.Item>

                    <List.Item
                        key="gender"
                        onClick={() => openUserGenderModal()}
                    >
                        <List.Item.Meta
                            avatar={<Avatar style={{ backgroundColor: APP_BACKGROUND_COLOR }} icon={<FaTransgender />} />}
                            title={currentUser.userInfo ? currentUser.userInfo.gender ? currentUser.userInfo.gender : '' : ''}
                        />
                    </List.Item>

                    <List.Item
                        onClick={() => history.push('/user/stat')}
                    >
                        <List.Item.Meta
                            avatar={<Avatar style={{ backgroundColor: APP_BACKGROUND_COLOR }} icon={<SolutionOutlined />} />}
                            title="Body Stat"
                        />
                    </List.Item>
                    <List.Item
                        onClick={() => history.push('/user/unit')}
                    >
                        <List.Item.Meta
                            avatar={<Avatar style={{ backgroundColor: APP_BACKGROUND_COLOR }} icon={<ProfileOutlined />} />}
                            title="Unit Setting"
                        />
                    </List.Item>
                </List>
            );
        }
    }

    const beforeUpload = (file) => {

        console.log('before upload', file);

        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
        if (!isJpgOrPng) {
            errorNotification('You can only upload JPG/PNG file!');

            return false;
        }
        const isLt2M = file.size / 1024 / 1024 < 1;
        if (!isLt2M) {
            errorNotification('Image must smaller than 1MB!');

            return false;
        }
        // return isJpgOrPng && isLt2M;

        setUploadPic(file);

        return false;

    }

    const onRemove = (file) => {

        setUploadPic(null);

    }

    const handleUpload = () => {

        if (uploadPic) {

            setUploadingPic(true);

            updateUserPicture(uploadPic)
                .then(res => {
                    successNotification('Update User Picture Success');

                    fetchCurrentUser();

                    closeUploadPicModal();

                })
                .catch(err => {
                    errorNotification('Update User Picture Failed', err.error.message);
                })
                .finally(() => {
                    setUploadingPic(false);
                });
        }

    };

    const updateName = () => {
        if (userUpdateName) {

            setUpdatingName(true);

            updateUserName(userUpdateName)
                .then(res => {
                    successNotification('Update User Name Success');

                    fetchCurrentUser();

                    closeUserNameModal();
                })
                .catch(err => {
                    errorNotification('Update User Name Failed');
                    console.error('update user name failed', err);
                })
                .finally(() => {
                    setUpdatingName(false);
                });
        }
    }

    const updateBirthday = () => {
        setUpdatingBirthday(true);

        updateUserBirthday(updateUserBirthday.format('YYYY-MM-DD'))
            .then(res => {
                successNotification('Update Birthday Success');
                fetchCurrentUser();
                closeUserBirthdayModal();
            })
            .catch(err => {
                errorNotification('Update Birthday Failed');
                console.error('Update Birthday Failed', err);
            })
            .finally(() => {
                setUpdatingBirthday(false);
            })
    }

    const updateGender = () => {
        setUpdatingGender(true);

        updateUserGender(userUpdateGender)
            .then(res => {
                successNotification('Update User Gender Success');
                closeUserGenderModal();
            })
            .catch(err => {
                errorNotification('Update User Gender Failed');

                console.error('Update User Gender Failed', err);
            })
            .finally(() => {
                setUpdatingGender(false);
            });
    }

    const birthdayModal = () => {

        if (currentUser) {
            const { userInfo } = currentUser;
            const { birthday } = userInfo || {};

            const saveDisable = userUpdateBirthday === null || userUpdateBirthday.format('YYYY-MM-DD') === birthday;

            return (
                <Modal
                    title="Birthday"
                    visible={userBirthdayModalVisible}
                    onCancel={() => closeUserBirthdayModal()}
                    footer={[
                        <Button shape="round" icon={<CloseOutlined />} onClick={() => closeUserBirthdayModal()} />,
                        <Button
                            onClick={() => {
                                updateBirthday();
                            }}
                            disabled={saveDisable}
                            shape="round"
                            style={{ color: 'white', backgroundColor: saveDisable ? 'grey' : APP_BACKGROUND_COLOR }}>
                            {updatingBirthday ? <Spin /> : 'Save'}
                        </Button>
                    ]}
                >
                    {/* <Input onChange={(e) => set(e.target.value)} value={userUpdateName} placeholder="Your Name" prefix={<UserOutlined />} /> */}
                    <DatePicker
                        value={userUpdateBirthday}
                        onChange={(date, dateString) => {
                            setUserUpdateBirthday(date);
                        }} />
                </Modal>
            );
        }

    }

    const genderModal = () => {

        if (currentUser) {
            const { userInfo } = currentUser;
            const { gender } = userInfo || {};

            const saveDisable = userUpdateGender === null || gender === null || userUpdateGender === gender.toLowerCase();

            return (
                <Modal
                    title="Gender"
                    visible={userGenderModalVisible}
                    onCancel={() => closeUserGenderModal()}
                    footer={[
                        <Button shape="round" icon={<CloseOutlined />} onClick={() => closeUserGenderModal()} />,
                        <Button
                            onClick={() => {
                                updateGender();
                            }}
                            disabled={saveDisable}
                            shape="round"
                            style={{ color: 'white', backgroundColor: saveDisable ? 'grey' : APP_BACKGROUND_COLOR }}>
                            {updatingGender ? <Spin /> : 'Save'}
                        </Button>
                    ]}
                >
                    <Radio.Group value={userUpdateGender} onChange={(e) => setUserUpdateGender(e.target.value)}>
                        <Radio.Button value="male">Male</Radio.Button>
                        <Radio.Button value="female">Female</Radio.Button>
                    </Radio.Group>
                </Modal>
            );
        }

    }

    if (!currentUser) {
        return (
            <div className="user">
                <h1>User Page</h1>
                <Spin />
            </div>
        );
    }

    return (
        <div className="user">
            <h1>User Page</h1>
            {getUserImage()}
            {getUserSetting()}

            <Modal
                title="Upload New Picture"
                className="image-upload-modal"
                onCancel={() => closeUploadPicModal()}
                onOk={() => closeUploadPicModal()}
                visible={userPicUploadModalVisible}
                footer={[
                    <Button shape="round" icon={<CloseOutlined />} onClick={() => closeUploadPicModal()} />,
                    <Button
                        onClick={() => {
                            handleUpload();
                        }}
                        disabled={uploadingPic ? true : uploadPic ? false : true}
                        shape="round"
                        style={{ color: 'white', backgroundColor: uploadPic ? APP_BACKGROUND_COLOR : 'grey' }}>
                        {uploadingPic ? <Spin /> : 'Save'}
                    </Button>
                ]}
            >
                <Dragger
                    name="file"
                    multiple={false}
                    beforeUpload={beforeUpload}
                    onRemove={onRemove}
                    fileList={uploadPic ? [uploadPic] : []}
                >
                    <p className="ant-upload-drag-icon">
                        <FileImageOutlined
                            style={{ color: APP_BACKGROUND_COLOR }}
                        />
                    </p>
                    <p className="ant-upload-text">Click or drag image to this area to upload your account picture</p>
                </Dragger>
            </Modal>

            <Modal
                title="Name"
                visible={userNameModalVisible}
                onCancel={() => closeUserNameModal()}
                footer={[
                    <Button shape="round" icon={<CloseOutlined />} onClick={() => closeUserNameModal()} />,
                    <Button
                        onClick={() => {
                            updateName();
                        }}
                        disabled={currentUser.name === userUpdateName ? true : false}
                        shape="round"
                        style={{ color: 'white', backgroundColor: currentUser.name === userUpdateName ? 'grey' : APP_BACKGROUND_COLOR }}>
                        {updatingName ? <Spin /> : 'Save'}
                    </Button>
                ]}
            >
                <Input onChange={(e) => setUserUpdateName(e.target.value)} value={userUpdateName} placeholder="Your Name" prefix={<UserOutlined />} />
            </Modal>

            {birthdayModal()}

            {genderModal()}

        </div>
    );
}

const UserBodyStat = () => {

    const appContext = useContext(AppContext);
    const { state: appState, dispatch: appDispatch } = appContext;

    const { userBodyStat, auth, currentUser } = appState;

    const [newHeightModalVisible, setNewHeightModalVisible] = useState(false);
    const [newHeightValue, setNewHeightValue] = useState(0);

    const [newWeightModalVisible, setNewWeightModalVisible] = useState(false);
    const [newWeightValue, setNewWeightValue] = useState(0);

    const [newBodyFatModalVisible, setNewBodyFatModalVisible] = useState(false);
    const [newBodyFatValue, setNewBodyFatValue] = useState(0);

    useEffect(() => {

        if (!userBodyStat) {
            fetchUserBodyStatFromServer();
        }

    });

    const openHeightModal = () => {
        setNewHeightModalVisible(true);

        if (userBodyStat && currentUser) {
            const { height } = userBodyStat;
            if (height) {
                setNewHeightValue(getHeightValue());
            } else {
                setNewHeightValue(0);
            }
        }
    };
    const closeHeightModal = () => setNewHeightModalVisible(false);

    const openWeightModal = () => {
        setNewWeightModalVisible(true);

        const lastWeight = getLastWeightValue();

        if (lastWeight) {
            setNewWeightValue(lastWeight);
        } else {
            setNewWeightValue(0);
        }
    };
    const closeWeightModal = () => setNewWeightModalVisible(false);

    const opentBodyFatModal = () => {
        setNewBodyFatModalVisible(true);

        setNewBodyFatValue(getLastBodyFatValue());
    };
    const closeBodyFatModal = () => setNewBodyFatModalVisible(false);

    const getHeightValue = () => {
        if (userBodyStat && currentUser) {
            const { height } = userBodyStat;
            if (height) {
                return convertUnit(currentUser.userUnitSetting.heightUnit, height.measurementUnit, height.height);
            } else {
                return null;
            }
        }
    }

    const getHeightUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.heightUnit.alias;
        }
    }

    const getWeightValues = () => {
        if (userBodyStat && currentUser) {
            const { weights } = userBodyStat;
            if (weights && weights.length !== 0) {
                return weights.map(w => {
                    return {
                        id: w.id,
                        date: new Date(w.date + ' 00:00'),
                        value: convertUnit(currentUser.userUnitSetting.weightUnit, w.measurementUnit, w.weight)
                    };
                }).sort((a, b) => b.date - a.date);
            } else {
                return [];
            }
        }
    }

    const getLastWeightValue = () => {
        const weights = getWeightValues();

        if (weights && weights.length !== 0) {
            return weights[0].value;
        }
    }

    const getWeightUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.weightUnit.alias;
        }
    }

    const getBodyFatValues = () => {
        if (userBodyStat && currentUser) {
            const { bodyFats } = userBodyStat;
            if (bodyFats && bodyFats.length !== 0) {
                return bodyFats.map(bf => {
                    return {
                        id: bf.id,
                        date: new Date(bf.date + ' 00:00'),
                        value: bf.bodyFat
                    }
                }).sort((a, b) => b.date - a.date);
            } else {
                return [];
            }
        }
    }

    const getLastBodyFatValue = () => {
        const bodyFats = getBodyFatValues();

        if (bodyFats && bodyFats.length !== 0) {
            return bodyFats[0].value;
        }
    }

    const fetchUserBodyStatFromServer = () => {
        fetchUserBodyStat()
            .then(res => res.json())
            .then(res => res.result)
            .then(res => {
                appDispatch({ type: SET_BODY_STAT, payload: res });
            })
            .catch(err => {
                errorNotification('Fetch User Body Stat Failed');
            });
    }

    const getHeight = () => {
        if (userBodyStat) {
            const { height } = userBodyStat;
            if (height) {
                return (
                    <h4>{getHeightValue()} {getHeightUnitAlias()}</h4>
                );
            } else {
                return (
                    <p>Add Height Data</p>
                )
            }
        }
    }

    const getWeight = () => {
        if (userBodyStat) {
            const { weights } = userBodyStat;
            if (weights && weights.length != 0) {

                return (
                    <h4>{getLastWeightValue()} {getWeightUnitAlias()}</h4>
                );
            } else {
                return (
                    <p>Add Weight Data</p>
                )
            }
        }
    }

    const getBodyFat = () => {
        if (userBodyStat) {
            const { bodyFats } = userBodyStat;
            if (bodyFats && bodyFats.length !== 0) {

                const bf = getLastBodyFatValue();

                return (
                    <React.Fragment>
                        <h4>{bf} %</h4>
                    </React.Fragment>
                );
            } else {
                return (
                    <p>Add Body Fat Data</p>
                )
            }
        }
    }

    const getHeightUnit = () => {
        if (auth) {
            if (currentUser) {
                return currentUser.userUnitSetting.heightUnit.alias
            }
        }
    }

    const newWeightBtnDisable = (userBodyStat && userBodyStat.weights && currentUser) ?
        userBodyStat.weights.length === 0 ? false :
            getWeightValues()[0].date.toLocaleDateString() === new Date().toLocaleDateString() : false;

    const newBodyFatBtnDisable = (userBodyStat && userBodyStat.bodyFats && currentUser) ?
        userBodyStat.bodyFats.length === 0 ? false :
            getBodyFatValues()[0].date.toLocaleDateString() === new Date().toLocaleDateString() : false;

    return (
        <div className="user-stat">
            <h2>Body Stat</h2>
            <List>
                <List.Item
                    onClick={() => openHeightModal()}
                >
                    <List.Item.Meta
                        title={<h3 className="height-lable" style={{ width: 100 }}>Height</h3>}
                    />
                    <div className="height-value">
                        {getHeight()}
                    </div>
                </List.Item>
                <List.Item
                    onClick={() => openWeightModal()}
                >
                    <List.Item.Meta
                        title={<h3 style={{ width: 100 }}>Weight</h3>}
                    />
                    <div className="weight-value">
                        {getWeight()}
                    </div>
                </List.Item>
                <List.Item
                    onClick={() => opentBodyFatModal()}>
                    <List.Item.Meta
                        title={<h3 style={{ width: 100 }}>Body Fat</h3>}
                    />
                    <div className="body-fat-value">
                        {getBodyFat()}
                    </div>
                </List.Item>
            </List>

            <Modal
                title="Height"
                visible={newHeightModalVisible}
                onOk={() => closeHeightModal()}
                onCancel={() => closeHeightModal()}
                footer={[
                    <Button
                        onClick={() => closeHeightModal()}>
                        Cancle
                        </Button>,
                    <Button
                        disabled={newHeightValue === getHeightValue()}
                        onClick={() => {
                            const postValue = { height: newHeightValue };
                            changeUserHeight(postValue)
                                .then(res => {
                                    closeHeightModal()
                                    fetchUserBodyStatFromServer();
                                })
                                .catch(err => {
                                    errorNotification('Update Height Failed', err.error);
                                });

                        }}
                    >Save</Button>
                ]}
            >
                <Row justify="space-around">
                    <h3>Height</h3>
                    <Col>
                        <InputNumber min={0} max={300} step={0.1} onChange={(v) => setNewHeightValue(v)} value={newHeightValue} />
                        {getHeightUnit()}
                    </Col>
                </Row>

            </Modal>

            <Modal
                className="new-weight-modal"
                title="Weights"
                visible={newWeightModalVisible}
                onOk={() => closeWeightModal()}
                onCancel={() => closeWeightModal()}
                footer={[
                    <Button onClick={() => closeWeightModal()}>Close</Button>
                ]}
            >
                <WeightChart weights={getWeightValues()} />

                <Row justify="space-around">
                    <h3>New Weight Record</h3>
                    <Col>
                        <InputNumber min={0} step={0.1} onChange={(v) => setNewWeightValue(v)} value={newWeightValue} />
                        {getWeightUnitAlias()}
                    </Col>
                    <Col>
                        <Button
                            disabled={newWeightBtnDisable}
                            onClick={() => {
                                if (newWeightValue !== 0) {

                                    const today = new Date();

                                    const postValue = {
                                        weight: newWeightValue,
                                        date: today
                                    }

                                    newUserWeight(postValue)
                                        .then(res => {
                                            successNotification('Saved New Weight');

                                            fetchUserBodyStatFromServer();
                                        })
                                        .catch(err => {
                                            errorNotification('Save New Weight Failed', err.error.message);
                                        });
                                } else {
                                    errorNotification('Save New Weight Failed', 'You can not set weight as Zero');
                                }
                            }}>Save</Button>
                    </Col>
                </Row>
                <br />
                <Row>
                    <Col span={24}>
                        <List
                            header={<div>Weights</div>}
                            bordered
                            dataSource={getWeightValues()}
                            renderItem={item => (
                                <List.Item
                                    key={item.id}>
                                    <Typography.Text> {`${item.date.toLocaleDateString()} - ${item.value} ${getWeightUnitAlias()}`}  </Typography.Text>

                                    <DeleteIconButton
                                        shape="circle"
                                        onClick={() => {
                                            deleteWeight(item.id)
                                                .then(res => {
                                                    successNotification('Delete Weight Record Success');
                                                    fetchUserBodyStatFromServer();
                                                })
                                                .catch(err => {
                                                    errorNotification('Delete Weight Record Failed', err.error.message);
                                                })
                                        }} />
                                </List.Item>
                            )}
                        />
                    </Col>
                </Row>

            </Modal>

            <Modal
                title="Body Fats"
                className="body-fat-modal"
                visible={newBodyFatModalVisible}
                onOk={() => closeBodyFatModal()}
                onCancel={() => closeBodyFatModal()}
                footer={[
                    <Button
                        onClick={() => closeBodyFatModal()}>
                        Close
                        </Button>
                ]}
            >
                <BodyFatChart bodyFats={getBodyFatValues()} />

                <Row justify="space-around">
                    <h3>New Body Fat Record</h3>
                    <Col>
                        <InputNumber min={0} max={100} step={0.1} onChange={(v) => setNewBodyFatValue(v)} value={newBodyFatValue} />
                        %
                    </Col>
                    <Col>
                        <Button
                            disabled={newBodyFatBtnDisable}
                            onClick={() => {
                                if (newBodyFatValue !== 0) {

                                    const postValue = {
                                        bodyFat: newBodyFatValue,
                                        date: new Date()
                                    }

                                    newUserBodyFat(postValue)
                                        .then(res => {
                                            successNotification('Saved Body Fat Record Success');

                                            fetchUserBodyStatFromServer();
                                        })
                                        .catch(err => {
                                            errorNotification('Save Body Fat Record Failed', err.error.message);
                                        });
                                } else {
                                    errorNotification('Save Body Fat Record Failed', 'No one could have 0% body fat, don\'t joke');
                                }
                            }}>Save</Button>
                    </Col>
                </Row>
                <br />
                <Row>
                    <Col span={24}>
                        <List
                            header={<div>Body Fat</div>}
                            bordered
                            dataSource={getBodyFatValues()}
                            renderItem={item => (
                                <List.Item
                                    key={item.id}>
                                    <Typography.Text> {`${item.date.toLocaleDateString()} - ${item.value} %`}  </Typography.Text>

                                    <DeleteIconButton
                                        shape="circle"
                                        onClick={() => {
                                            deleteBodyFat(item.id)
                                                .then(res => {
                                                    successNotification('Delete Body Fat Record Success');
                                                    fetchUserBodyStatFromServer();
                                                })
                                                .catch(err => {
                                                    errorNotification('Delete Body Fat Record Failed', err.error.message);
                                                })
                                        }} />
                                </List.Item>
                            )}
                        />
                    </Col>
                </Row>
            </Modal>
        </div>
    );
}

const UserUnitSetting = () => {

    const appContext = useContext(AppContext);
    const { state: appState, dispatch: appDispatch } = appContext;

    const { userBodyStat, auth, currentUser } = appState;

    const [newHeightUnit, setNewHeightUnit] = useState('');
    const [newWeightUnit, setNewWeightUnit] = useState('');
    const [newDistanceUnit, setNewDistanceUnit] = useState('');

    const [heightUnitAlias, setHeightUnitAlias] = useState('');
    const [weightUnitAlias, setWeightUnitAlias] = useState('');
    const [distanceUnitAlias, setDistanceUnitAlias] = useState('');

    const [heightUnitModalVisible, setHeightUnitModalVisible] = useState(false);
    const [weightUnitModalVisible, setWeightUnitModalVisible] = useState(false);
    const [distanceUnitModalVisible, setDistanceUnitModalVisible] = useState(false);

    const closeHeightUnitModal = () => setHeightUnitModalVisible(false);
    const openHeightUnitModal = () => {
        setHeightUnitModalVisible(true);
        setNewHeightUnit(getHeightUnitId());
    };

    const closeWeightUnitModal = () => setWeightUnitModalVisible(false);
    const openWeightUnitModal = () => {
        setWeightUnitModalVisible(true);
        setNewWeightUnit(getWeightUnitId());
    };

    const closeDistanceUnitModal = () => setDistanceUnitModalVisible(false);
    const openDistanceUnitModal = () => {
        setDistanceUnitModalVisible(true);
        setNewDistanceUnit(getDistanceUnitId());
    };


    useEffect(() => {
        // setWeightAlias(getWeightUnitAlias());
        console.log('current user changed');
        if (!currentUser) {
            auth.fetchCurrentUser((user) => {
                appDispatch({ type: SET_USER, payload: user });
            }, (err) => {
                errorNotification('Fetch User Info Failed', err.error.message);
            });
        }

    }, []);

    const getUnitAliases = () => {
        setHeightUnitAlias(getHeightUnitAlias());
        setWeightUnitAlias(getWeightUnitAlias());
        setDistanceUnitAlias(getDistanceUnitAlias());
    }

    const getHeightUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.heightUnit.alias;
        }
    }

    const getHeightUnitId = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.heightUnit.id;
        }
    }

    const getWeightUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.weightUnit.alias;
        }
    }

    const getWeightUnitId = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.weightUnit.id;
        }
    }

    const getDistanceUnitAlias = () => {
        if (currentUser) {
            return currentUser.userUnitSetting.distanceUnit.alias;
        }
    }

    const getDistanceUnitId = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.distanceUnit.id;
        }
    }

    if (!currentUser) {
        return (
            <Spin />
        )
    }

    return (
        <div className="user-unit-setting">
            <h2>Unit Setting</h2>
            <List>
                <List.Item
                    key="height"
                >
                    <List.Item.Meta
                        title={<div className="height-unit-lable" style={{ width: 100, fontWeight: 'bold' }}>Height Unit</div>}
                    />
                    <div className="height-unit">
                        <Button
                            onClick={() => {
                                openHeightUnitModal();
                            }}
                            className="change-height-unit-btn">
                            {getHeightUnitAlias()}
                        </Button>
                    </div>
                </List.Item>
                <List.Item
                    key="weight"
                // onClick={() => openWeightModal()}
                >
                    <List.Item.Meta
                        title={<div className="weight-unit-lable" style={{ width: 100, fontWeight: 'bold' }}>Weight Unit</div>}
                    />
                    <div className="weight-unit">
                        <Button
                            onClick={() => {
                                openWeightUnitModal();
                            }}
                            className="change-weight-unit-btn">
                            {getWeightUnitAlias()}
                        </Button>
                    </div>
                </List.Item>
                <List.Item
                    key="distance"
                // onClick={() => opentBodyFatModal()}
                >
                    <List.Item.Meta
                        title={<div className="distance-unit-lable" style={{ width: 100, fontWeight: 'bold' }}>Distance Unit</div>}
                    />
                    <div className="distance-unit">
                        <Button
                            onClick={() => {
                                openDistanceUnitModal();
                            }}
                            className="change-distance-unit-btn">
                            {getDistanceUnitAlias()}
                        </Button>
                    </div>
                </List.Item>
            </List>

            <Modal
                title="Height Unit"
                visible={heightUnitModalVisible}
                onOk={() => closeHeightUnitModal()}
                onCancel={() => closeHeightUnitModal()}
                footer={[
                    <Button
                        onClick={() => closeHeightUnitModal()}>
                        Close
                        </Button>,
                    <Button
                        disabled={getHeightUnitId() === newHeightUnit}
                        onClick={() => {
                            postUserHeightUnit(newHeightUnit)
                                .then(res => {
                                    successNotification('Change Height Unit Success');

                                    closeHeightUnitModal();

                                    auth.fetchCurrentUser((user) => {
                                        appDispatch({ type: SET_USER, payload: user });
                                    }, (err) => {
                                        errorNotification('Refresh User Info Failed');
                                    });
                                })
                                .catch(err => {
                                    errorNotification('Change Height Unit Failed', err.error.message);
                                });
                        }}
                    >
                        Save
                    </Button>
                ]}
            >
                <Row justify="space-around">
                    <div>Height Unit :</div>
                    <Radio.Group
                        onChange={(e) => setNewHeightUnit(e.target.value)}
                        value={newHeightUnit}>
                        <Radio.Button value={1}>Cm</Radio.Button>
                        <Radio.Button value={2}>Inch</Radio.Button>
                    </Radio.Group>
                </Row>
            </Modal>

            <Modal
                title="Weight Unit"
                visible={weightUnitModalVisible}
                onOk={() => closeWeightUnitModal()}
                onCancel={() => closeWeightUnitModal()}
                footer={[
                    <Button
                        onClick={() => closeWeightUnitModal()}>
                        Close
                        </Button>,
                    <Button
                        disabled={getWeightUnitId() === newWeightUnit}
                        onClick={() => {
                            postUserWeightUnit(newWeightUnit)
                                .then(res => {
                                    successNotification('Change Weight Unit Success');

                                    closeWeightUnitModal();

                                    auth.fetchCurrentUser((user) => {
                                        appDispatch({ type: SET_USER, payload: user });
                                    }, (err) => {
                                        errorNotification('Refresh User Info Failed');
                                    });
                                })
                                .catch(err => {
                                    errorNotification('Change Weight Unit Failed', err.error.message);
                                });
                        }}
                    >
                        Save
                    </Button>
                ]}
            >
                <Row justify="space-around">
                    <div>Weight Unit :</div>
                    <Radio.Group
                        onChange={(e) => setNewWeightUnit(e.target.value)}
                        value={newWeightUnit}>
                        <Radio.Button value={4}>Kg</Radio.Button>
                        <Radio.Button value={3}>Pound</Radio.Button>
                    </Radio.Group>
                </Row>
            </Modal>

            <Modal
                title="Distance Unit"
                visible={distanceUnitModalVisible}
                onOk={() => closeDistanceUnitModal()}
                onCancel={() => closeDistanceUnitModal()}
                footer={[
                    <Button
                        onClick={() => closeDistanceUnitModal()}>
                        Close
                        </Button>,
                    <Button
                        disabled={getDistanceUnitId() === newDistanceUnit}
                        onClick={() => {
                            postUserDistanceUnit(newDistanceUnit)
                                .then(res => {
                                    successNotification('Change Distance Unit Success');
                                    closeDistanceUnitModal();
                                    auth.fetchCurrentUser((user) => {
                                        appDispatch({ type: SET_USER, payload: user });
                                    }, (err) => {
                                        errorNotification('Refresh User Info Failed');
                                    });
                                })
                                .catch(err => {
                                    errorNotification('Change Distance Unit Failed', err.error.message);
                                });
                        }}
                    >
                        Save
                    </Button>
                ]}
            >
                <Row justify="space-around">
                    <div>Distance Unit :</div>
                    <Radio.Group
                        onChange={(e) => setNewDistanceUnit(e.target.value)}
                        value={newDistanceUnit}>
                        <Radio.Button value={6}>Km</Radio.Button>
                        <Radio.Button value={5}>Mi</Radio.Button>
                    </Radio.Group>
                </Row>
            </Modal>
        </div>
    )
}

export default User;
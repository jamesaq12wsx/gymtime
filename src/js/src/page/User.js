import React, { useContext, useEffect, useState } from 'react';
import { Button, Row, Avatar, Badge, List, Tooltip, Col, Modal, InputNumber, Tag, Typography, Radio } from 'antd';
import { UserOutlined, GoogleOutlined, FacebookFilled, ProfileOutlined } from '@ant-design/icons';
import { useHistory, useRouteMatch, Switch, Route } from 'react-router-dom';
import { AppContext } from '../context/AppContextProvider';
import { SET_BODY_STAT } from '../reducer/appContextReducer';
import { fetchUserBodyStat, changeUserHeight, newUserWeight, newUserBodyFat, deleteWeight, deleteBodyFat, postUserHeightUnit, postUserWeightUnit, postUserDistanceUnit } from '../api/client';
import { errorNotification, successNotification } from '../components/Notification';
import { convertUnit } from '../util';
import WeightChart from '../components/chart/WeightChart';
import BodyFatChart from '../components/chart/BodyFatChart';
import DeleteIconButton from '../components/DeleteIconButton';


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

    const { currentUser } = auth;

    const getAuthLocgo = () => {

        if (currentUser) {
            switch (currentUser.authProvider.toLowerCase()) {
                case 'google':
                    return (
                        <Tooltip title="Login by Google">
                            <GoogleOutlined />
                        </Tooltip>
                    );
                case 'facebook':
                    return <FacebookFilled />;
            }
        }

    }

    const getUserData = () => {
        if (currentUser) {
            return (
                <React.Fragment>
                    <Row className="user-image-row" justify="center">
                        <Badge offset={[-20, 200]} count={currentUser.authProvider ? getAuthLocgo() : <React.Fragment />} >
                            {currentUser.imageUrl ? <Avatar size={200} src={currentUser.imageUrl} /> : <Avatar size={200} icon={<UserOutlined />} />}
                        </Badge>
                    </Row>
                    <Row className="user-name-row" justify="center">
                        <h2>{currentUser.name}</h2>
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
                        onClick={() => history.push('/user/stat')}
                    >
                        <List.Item.Meta
                            avatar={<Avatar icon={<UserOutlined />} />}
                            title="Body Stat"
                        />
                    </List.Item>
                    <List.Item
                        onClick={() => history.push('/user/unit')}
                    >
                        <List.Item.Meta
                            avatar={<Avatar icon={<ProfileOutlined />} />}
                            title="Unit Setting"
                        />
                    </List.Item>
                </List>
            );
        }
    }

    return (
        <div className="user">
            <h1>User Page</h1>
            {getUserData()}
            {getUserSetting()}
        </div>
    );
}

const UserBodyStat = () => {

    const appContext = useContext(AppContext);
    const { state: appState, dispatch: appDispatch } = appContext;

    const { userBodyStat, auth } = appState;

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

        if (userBodyStat && auth.currentUser) {
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
        if (userBodyStat && auth.currentUser) {
            const { height } = userBodyStat;
            if (height) {
                return convertUnit(auth.currentUser.userUnitSetting.heightUnit, height.measurementUnit, height.height);
            } else {
                return null;
            }
        }
    }

    const getHeightUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.heightUnit.alias;
        }
    }

    const getWeightValues = () => {
        if (userBodyStat && auth.currentUser) {
            const { weights } = userBodyStat;
            if (weights && weights.length !== 0) {
                return weights.map(w => {
                    return {
                        id: w.id,
                        date: new Date(w.date + ' 00:00'),
                        value: convertUnit(auth.currentUser.userUnitSetting.weightUnit, w.measurementUnit, w.weight)
                    };
                }).sort((a, b) => b.date - a.date);
            } else {
                return [];
            }
        }
    }

    const getLastWeightValue = () => {
        const weights = getWeightValues();

        if (weights) {
            return weights[0].value;
        }
    }

    const getWeightUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.weightUnit.alias;
        }
    }

    const getBodyFatValues = () => {
        if (userBodyStat && auth.currentUser) {
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
                return null;
            }
        }
    }

    const getLastBodyFatValue = () => {
        const bodyFats = getBodyFatValues();

        if (bodyFats) {
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
            if (bodyFats && bodyFats.length != 0) {

                const bf = getLastBodyFatValue();

                return (
                    <React.Fragment>
                        <h4>{bf} %</h4>
                    </React.Fragment>
                );
            } else {
                return (
                    <p>Add Height Data</p>
                )
            }
        }
    }

    const getHeightUnit = () => {
        if (auth) {
            if (auth.currentUser) {
                return auth.currentUser.userUnitSetting.heightUnit.alias
            }
        }
    }

    const newWeightBtnDisable = (userBodyStat && userBodyStat.weights && auth.currentUser) ? getWeightValues()[0].date.toLocaleDateString() === new Date().toLocaleDateString() : false;

    const newBodyFatBtnDisable = (userBodyStat && userBodyStat.bodyFats && auth.currentUser) ? getBodyFatValues()[0].date.toLocaleDateString() === new Date().toLocaleDateString() : false;

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

    const { userBodyStat, auth } = appState;

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

        getUnitAliases();

    }, [auth.currentUser]);

    const getUnitAliases = () => {
        setHeightUnitAlias(getHeightUnitAlias());
        setWeightUnitAlias(getWeightUnitAlias());
        setDistanceUnitAlias(getDistanceUnitAlias());
    }

    const getHeightUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.heightUnit.alias;
        }
    }

    const getHeightUnitId = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.heightUnit.id;
        }
    }

    const getWeightUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.weightUnit.alias;
        }
    }

    const getWeightUnitId = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.weightUnit.id;
        }
    }

    const getDistanceUnitAlias = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.distanceUnit.alias;
        }
    }

    const getDistanceUnitId = () => {
        if (auth.currentUser) {
            return auth.currentUser.userUnitSetting.distanceUnit.id;
        }
    }

    return (
        <div className="user-unit-setting">
            <h2>Unit Setting</h2>
            <List>
                <List.Item
                >
                    <List.Item.Meta
                        title={<h3 className="height-unit-lable" style={{ width: 100 }}>Height Unit</h3>}
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
                // onClick={() => openWeightModal()}
                >
                    <List.Item.Meta
                        title={<h3 style={{ width: 100 }}>Weight Unit</h3>}
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
                // onClick={() => opentBodyFatModal()}
                >
                    <List.Item.Meta
                        title={<h3 style={{ width: 100 }}>Distance Unit</h3>}
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
                                    auth.fetchCurrentUser();
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
                                    auth.fetchCurrentUser();
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
                                    auth.fetchCurrentUser();
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
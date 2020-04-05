import React, { useContext, useState, useEffect, Component, useCallback } from 'react';
import './App.css';
import Container from './components/Container';
import { getAllClubsWithLocation, getUserIpInfo, getCountryItems, getAllExercise, getAllClubs, signUp } from './api/client';
import ClubList from './components/list/ClubList';
import LoadingList from './components/list/LoadingList';
import 'antd/dist/antd.css';
import { successNotification, errorNotification } from './components/Notification';
import { BrowserRouter as Router, Switch, Route, Link, useHistory, Redirect } from "react-router-dom";
import Clubs from './page/Clubs';
import ClubDetail from './page/ClubDetail';
import User from './page/User';
import AppContextProvider from './context/AppContextProvider';
import { Modal, Row, Col, Drawer, Button, Layout, Menu } from 'antd';
import { LoginOutlined, UserOutlined, SettingOutlined } from '@ant-design/icons';
import LoginModal from './components/LoginModal';
import { AppContext } from './context/AppContextProvider';
import { InfoContext } from './context/InfoContextProvider';
import Login from './page/Login';
import SignUp from './page/SignUp.page';
import LogoutPage from './page/Logout.page';
import ExercisePage from './page/Exercise.page';
import NavBarIcon from './components/NavBarIcon';
import { ClubContext } from './context/ClubContextProvider';
import { clubContextReducerType } from './reducer/clubContextReducer';
import { FaRunning, FaUserFriends, FaCalendarCheck } from "react-icons/fa";
import { IoIosFitness } from "react-icons/io";
import { GiJumpAcross } from "react-icons/gi";
var _ = require('lodash');


const { Header, Content, Footer } = Layout;


const AuthRoute = ({ component: Component, ...rest }) => {

  const appContext = useContext(AppContext);

  const { state } = appContext;

  return <Route {...rest} render={props => (
    state.authenticated ?
      (
        <Component {...props} />
      ) : (
        <Redirect
          to={{
            pathname: '/login',
            state: {
              from: props.location
            }
          }} />)
  )}
  />;
}

const App = (props) => {

  const appContext = useContext(AppContext);
  const clubContext = useContext(ClubContext);
  const infoContext = useContext(InfoContext);

  const { state: appState, dispatch: appDispatch } = appContext;
  const { state: clubState, dispatch: clubDispatch } = clubContext;
  const { state: infoState, dispatch: infoDispatch } = infoContext;

  const { auth, authenticated, location } = appState;

  const [loginModalVisible, setLoginModalVisible] = useState(false);
  const [settingSideBarVisible, setSettingSideBarVisible] = useState(false);

  const openLoginModal = () => setLoginModalVisible(true);
  const closeLoginModal = () => setLoginModalVisible(false);

  const openSideBar = () => setSettingSideBarVisible(true);
  const closeSideBar = () => setSettingSideBarVisible(false);

  useEffect(() => {

    if (auth.isAuthenticated()) {
      appDispatch({ type: 'LOGIN', payload: auth.getToken() });
    }

    getLocation(positionHandler, positionErrorHandler);

    getCountryItems()
      .then(res => res.json())
      .then(countries => {
        infoDispatch({ type: 'SET_COUNTRIES', payload: countries })
      })
      .catch(err => console.error('Cannot get countries', err));

    getAllExercise()
      .then(res => res.json())
      .then(exercises => {
        console.log(groupByExercise(exercises));
        infoDispatch({ type: 'SET_EXERCISES', payload: groupByExercise(exercises) });
      })
      .catch(err => console.error('Cannot get exercises', err));

    clubDispatch({ type: clubContextReducerType.FETCHING });

    getAllClubs()
      .then(res => res.json())
      .then(clubs => {
        infoDispatch({ type: 'SET_CLUBS', payload: clubs });
        clubDispatch({ type: clubContextReducerType.FETCHED });
      })
      .catch(err => {
        console.error("Cannot get clubs", err);
        errorNotification(err.message, err.message);
        clubDispatch({ type: clubContextReducerType.FETCHED });
      })

  }, []);

  const groupBy = (xs, key) => {
    return xs.reduce((rv, x) => {
      (rv[x[key]] = rv[x[key]] || []).push(x);
      return rv;
    }, {});
  };

  const groupByExercise = (exs) => {
    return exs.reduce((acc, cur) => {
      if(cur.category){
        cur.category.forEach(cat => {
          (acc[cat.categoryName] = acc[cat.categoryName] || []).push(cur);
        });
      }
      return acc;
    }, {});
  }

  // useEffect(() => {

  //   fetchingClubs();

  // }, [location]);

  // useEffect(() => {
  //   getUserIpInfo()
  //     .then(res => res.json())
  //     .then(info => appDispatch({type:'SET_IP_INFO', payload: info}))
  //     .catch(err => console.error(err));
  // }, []);

  const positionHandler = (position) => {

    // setLocation({ ...location, lat: position.coords.latitude, lon: position.coords.longitude });

    appDispatch({ type: 'SET_LOCATION', payload: { lat: position.coords.latitude, lng: position.coords.longitude } });

  }

  /**
   * Cannot get browser geoloaction
   * use ip info to get geolocation
   */
  const positionErrorHandler = () => {

    getUserIpInfo()
      .then(res => res.json())
      .then(info => {
        appDispatch({ type: 'SET_LOCATION', payload: { lat: info.latitude, lng: info.longitude } })
        // appDispatch({type:'SET_IP_INFO', payload: info})
      })
      .catch(err => console.error(err));

  };

  const getLocation = (successHandler, errorHandler) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(successHandler);
    } else {
      errorHandler();
    }
  }

  // getLocation();

  const fetchingClubs = () => {

    // setFetching(true);

    console.log('fetching clubs', location);

    if (location.lat && location.lng) {
      clubDispatch({ type: 'FETCHING_NEAR_CLUBS' });

      getAllClubsWithLocation(location.lat, location.lng)
        .then(r => r.json())
        .then(clubs => {
          // setClubs(clubs);

          clubDispatch({ type: 'FETCHED_NEAR_CLUBS', payload: clubs });

        }).catch(err => {
          errorNotification('CANNOT GET CLUBS', err.message);
          clubDispatch({ type: 'FETCHED_NEAR_CLUBS', payload: [] });
        })
    }

  }

  const getHeader = () => {
    return (
      <div className="header" style={{ margin: '5px' }}>
        {getHeaderItems()}
      </div>
    )
  }

  const getLogo = () => {
    return (
      <Link to='/clubs'>
        <h3>GymTime</h3>
      </Link>
    )
  }

  const getHeaderItems = () => {

    if (authenticated) {
      return (
        <Row>
          <Col span={4}>
            {getLogo()}
          </Col>
          <Col
            span={2}
            offset={4}
          >
            <Link style={{ color: 'rgba(89,89,89)' }} to='/clubs'>
              <NavBarIcon>
                <IoIosFitness size='2rem' />
              </NavBarIcon>
            </Link>
          </Col>
          <Col
            span={2}
            offset={1}
          >
            <Link style={{ color: 'rgba(89,89,89)' }} to='/user/post'>
              <NavBarIcon>
                <FaCalendarCheck size='2rem' />
              </NavBarIcon>
            </Link>
          </Col>
          <Col
            span={2}
            offset={1}
          >
            <Link style={{ color: 'rgba(89,89,89)' }} to='/exercise'>
              <NavBarIcon>
                <GiJumpAcross size='2rem' />
              </NavBarIcon>
            </Link>
          </Col>
          {/* <Col
            span={2}
            offset={1}
          >
            <Link style={{ color: 'rgba(89,89,89)' }} to='/posts'>
              <NavBarIcon>
                <FaUserFriends size='2rem' />
              </NavBarIcon>
            </Link>
          </Col> */}
          <Col span={2} offset={6} >
            <NavBarIcon>
              <SettingOutlined
                onClick={() => {
                  console.log('setting clicked');
                  openSideBar();
                }}
                style={{ fontSize: '20px', marginTop: '7px' }}
              />
            </NavBarIcon>
          </Col>
        </Row>
      );
    } else {
      return (
        <Row>
          <Col span={8} offset={9}>
            {getLogo()}
          </Col>
          <Col span={1} offset={6}>
            <LoginOutlined
              onClick={() => openLoginModal()}
              style={{ fontSize: '20px', marginTop: '7px' }}
            />
          </Col>
        </Row>
      );
    }
  }

  const listMarkOnClickHandler = (e, club) => {
    // console.log(`打卡 ${clubUuid}`);
  }

  const listDetailOnClickHander = (e, club) => {
    // console.log(`詳細資料 ${clubUuid}`);

    if (club) {
      // setSelectClub(club);
      clubDispatch({ type: 'SELECT_CLUB', payload: club })
    }
  }

  // const getClubList = () => {
  //   if (fetching) {
  //     return <LoadingList />;
  //   }

  //   return <ClubList clubs={clubs} markOnClick={listMarkOnClickHandler} detailOnClick={listDetailOnClickHander} />;
  // }

  return (
    <Container>
      {/* <div className="App">
        <h1>GYM TIME</h1>
        {getClubList()}
      </div> */}

      <Drawer
        title="Setting"
        placement="right"
        closable={false}
        onClose={() => closeSideBar()}
        visible={settingSideBarVisible}
      >
        <p>Some contents...</p>
        <p>Some contents...</p>
        <p>Gym Time V 1.0.0</p>
        <Button danger onClick={() => {
          auth.logout(() => {
            appDispatch({ type: 'LOGOUT' });
            closeSideBar();
          });
        }} >Logout</Button>
      </Drawer>

      <Router>
        <div className="App">

          <LoginModal
            visible={loginModalVisible}
            onSuccess={() => {
              closeLoginModal();
              successNotification('Login Success');
            }}
            onFailure={(err) => {

              console.error(err);

              const message = err.error.message;
              const description = err.error.httpStatus;
              console.log(JSON.stringify(err));
              errorNotification(message, description);
            }}
            onOk={() => closeLoginModal()}
            onCancel={() => closeLoginModal()}
            toSignUp={() => {
              closeLoginModal();
            }}
          />
          {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
          {getHeader()}
          <Switch>
            <Route exact path="/login" component={Login} />
            <Route exact path='/signup' component={SignUp} />
            <Route exact path={['', "/clubs"]}>
              <Clubs markOnClick={listMarkOnClickHandler} detailOnClick={listDetailOnClickHander} />
            </Route>
            <Route exact path="/exercise" component={ExercisePage} />
            <Route exact path={`/club/:clubUuid`} render={props => <ClubDetail currentPosition={location} {...props} />} />
            <AuthRoute path='/user' component={User} />
            <AuthRoute exact path='/logout' component={LogoutPage} />
            <Route path="*" component={() => "404 NOT FOUND"} />
          </Switch>
        </div>
      </Router>
    </Container>
  );
}

export default App;

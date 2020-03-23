import React, { useContext, useState, useEffect, Component, useCallback } from 'react';
import './App.css';
import Container from './components/Container';
import { getAllClubs, getAllClubsWithLocation } from './api/client';
import ClubList from './components/list/ClubList';
import LoadingList from './components/list/LoadingList';
import 'antd/dist/antd.css';
import { successNotification, errorNotification } from './components/Notification';
import { BrowserRouter as Router, Switch, Route, Link, useHistory, Redirect } from "react-router-dom";
import Clubs from './page/Clubs';
import ClubDetail from './page/ClubDetail';
import User from './page/User';
import AppContextProvider from './context/AppContextProvider';
import { Modal, Row, Col } from 'antd';
import {
  LoginOutlined,
  UserOutlined,
  SettingOutlined
} from '@ant-design/icons';
import LoginModal from './components/LoginModal';
import { AppContext } from './context/AppContextProvider';
import Login from './page/Login';
import auth from './components/Auth';
import LogoutPage from './page/Logout.page';

const AuthRoute = ({ component: Component, ...rest }) => {

  const authenticated = auth.isAuthenticated();

  return <Route {...rest} render={props => (
    authenticated ?
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
  const { state, dispatch } = appContext;

  const [fetching, setFetching] = useState(false);
  const [clubs, setClubs] = useState([]);
  const [location, setLocation] = useState({ lat: null, lon: null });
  const [selectClub, setSelectClub] = useState(null);
  const [loginModalVisible, setLoginModalVisible] = useState(false);

  const openLoginModal = () => setLoginModalVisible(true);
  const closeLoginModal = () => setLoginModalVisible(false);

  const positionHandler = (position) => {

    setLocation({ ...location, lat: position.coords.latitude, lon: position.coords.longitude });

    getAllClubsWithLocation(position.coords.latitude, position.coords.longitude)
      .then(r => r.json())
      .then(clubs => {
        setClubs(clubs);
      }).finally(() => {
        setFetching(false);
      });
  }

  const getHeader = () => {

    if (auth.isAuthenticated()) {
      return (
        <Row>
          <Col span={4}>
            <Link to='/clubs'>
              <h3>GYM TIME</h3>
            </Link>
          </Col>
          <Col span={1} offset={6}>
            <SettingOutlined style={{ fontSize: '16px', marginTop: '7px' }} />
          </Col>
        </Row>
      );
    } else {
      return (
        <Row>
          <Col span={8} offset={9}>
            <Link to='/clubs'>
              <h3>GYM TIME</h3>
            </Link>
          </Col>
          <Col span={1} offset={6}>
            <LoginOutlined onClick={() => openLoginModal()} style={{ fontSize: '16px', marginTop: '7px' }} />
          </Col>
        </Row>
      );
    }
  }

  const positionErrorHandler = () => {

    getAllClubs()
      .then(r => r.json())
      .then(clubs => {
        // console.log(clubs);
        setClubs(clubs);
      }).finally(() => {
        setFetching(false);
      });
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

    setFetching(true);

    getLocation(positionHandler, positionErrorHandler);

    setFetching(false);

  }

  useEffect(() => {

    if (!state.nearClubs || state.nearClubs.length === 0) {
      fetchingClubs();
    }

  }, [state.nearClubs]);

  const listMarkOnClickHandler = (e, club) => {
    // console.log(`打卡 ${clubUuid}`);
  }

  const listDetailOnClickHander = (e, club) => {
    // console.log(`詳細資料 ${clubUuid}`);

    if (club) {
      setSelectClub(club);
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
      <LoginModal
        visible={loginModalVisible}
        onSuccess={() => {

          closeLoginModal();

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
      />

      <Router>
        <div className="App">
          {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
          {getHeader()}
          <Switch>
            <Route exact path="/login" component={Login} />
            <Route exact path={['', "/clubs"]}>
              <Clubs fetching={fetching} clubs={clubs} markOnClick={listMarkOnClickHandler} detailOnClick={listDetailOnClickHander} />
            </Route>
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

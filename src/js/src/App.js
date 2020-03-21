import React, { useContext, useState, useEffect } from 'react';
import './App.css';
import Container from './components/Container';
import { getAllClubs, getAllClubsWithLocation } from './api/client';
import ClubList from './components/list/ClubList';
import LoadingList from './components/list/LoadingList';
import 'antd/dist/antd.css';
import { successNotification } from './components/Notification';
import { BrowserRouter as Router, Switch, Route, Link, useHistory } from "react-router-dom";
import Clubs from './page/Clubs';
import ClubDetail from './page/ClubDetail';
import User from './page/User';

const App = (props) => {

  const [fetching, setFetching] = useState(false);
  const [clubs, setClubs] = useState([]);
  const [location, setLocation] = useState({ lat: null, lon: null });
  const [selectClub, setSelectClub] = useState(null);

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
    console.log('useEffect');

    fetchingClubs();
  }, []);

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

      <Router>
        <div className="App">
          {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
          <Link to='/clubs'>
            <h1>GYM TIME</h1>
          </Link>
          <Switch>
            <Route path="/clubs">
              <Clubs fetching={fetching} clubs={clubs} markOnClick={listMarkOnClickHandler} detailOnClick={listDetailOnClickHander} />
            </Route>
            <Route path={`/club/:clubUuid`} render={props => <ClubDetail currentPosition={location} {...props} />} />
            <Route path={`/user`}>
              <User />
            </Route>
          </Switch>
        </div>
      </Router>
    </Container>
  );
}

export default App;

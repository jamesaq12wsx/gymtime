// import React from 'react';
// import { BrowserRouter as Router, Switch, Route, Link, useHistory } from "react-router-dom";
// import Clubs from './page/Clubs';
// import ClubDetail from './page/ClubDetail';
// import User from './page/User';

// const Main = (props) => {

    

//     return (
//         <Router>
//             {/* A <Switch> looks through its children <Route>s and
//             renders the first one that matches the current URL. */}
//             <h1>GYM TIME</h1>
//             <Switch>
//                 <Route path="/clubs">
//                     <Clubs fetching={fetching} clubs={clubs} markOnClick={listMarkOnClickHandler} detailOnClick={listDetailOnClickHander} />
//                 </Route>
//                 <Route path={`/club/:clubUuid`} render={props => <ClubDetail currentPosition={location} {...props} />} />
//                 <Route path={`/user`}>
//                     <User />
//                 </Route>
//             </Switch>
//         </Router>
//     );
// }

// export default Main;
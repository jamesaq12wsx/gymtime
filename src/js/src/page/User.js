import React from 'react';
import { Button } from 'antd';
import auth from '../components/Auth';
import { useHistory, useRouteMatch, Switch, Route } from 'react-router-dom';
import UserPost from './UserPost.page';
import ExerciseEdit from './PostEdit.page';

const User = () => {

    let history = useHistory();

    let match = useRouteMatch();

    return (
        <div className="user">
            <Switch>
                {/* <Route exact path={`${match.path}/post`} component={UserPost}></Route> */}
                {/* <Route exact path={`${match.path}/post/:postId`} component={ExerciseEdit}></Route> */}
                <Route exact path={`${match.path}/*`} component={() => <h1>User Detail</h1>}></Route>
            </Switch>
        </div>
        // <div className="user">
        //     <h1>User</h1>
        //     <Button
        //         onClick={() => {
        //             auth.logout();
        //             history.push('/');
        //         }}
        //         danger
        //     >
        //         Logout
        //     </Button>
        // </div>
    );
};

export default User;
import React, { useContext } from 'react';
import auth from '../components/Auth';

const Header = () => {

    const authenticated = auth.isAuthenticated();

    return (
        authenticated ?
        <Row>
            <Col span={8} offset={8}>
              <Link to='/clubs'>
                <h2>GYM TIME</h2>
              </Link>
            </Col>
            <Col span={2} offset={6}>
                <LoginOutlined onClick={() => openLoginModal()} style={{ fontSize: '16px', marginTop: '7px' }} />
            </Col>
        </Row>
    );
}

export default Header;
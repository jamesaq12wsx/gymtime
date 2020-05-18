import React, { useContext } from 'react';
import { Button, Input, Tag, Row, Col, Divider } from 'antd';
import { Formik } from 'formik';
import { Link, useHistory } from 'react-router-dom';
import { AppContext } from '../../context/AppContextProvider';
import { FACEBOOK_AUTH_URL, GOOGLE_AUTH_URL } from '../constants';
import { createFromIconfontCN } from '@ant-design/icons';
import { FacebookOutlined, GoogleOutlined } from '@ant-design/icons';

const IconFont = createFromIconfontCN({
    scriptUrl: '//at.alicdn.com/t/font_8d5l8fzk5b87iudi.js',
});


const inputBottomStyle = { marginBottom: '5px' };

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const LoginForm = ({onSuccess, onFailure}) => {

    const history = useHistory();

    const appContext = useContext(AppContext);
    const { state, dispatch } = appContext;

    return (
        <React.Fragment>
            <Formik
                initialValues={{ email: '', password: '' }}
                validate={values => {
                    const errors = {};

                    if (!values.email) {
                        errors.email = 'Email is Required';
                    }

                    if (!values.password) {
                        errors.password = 'Password is Required';
                    }

                    return errors;
                }}
                onSubmit={(values, { setSubmitting }) => {

                    console.log("Login", values);

                    const { auth } = state;

                    auth.login(values, (token) => {

                        setSubmitting(false);

                        dispatch({ type: 'LOGIN', payload: token });

                        if (onSuccess) {
                            onSuccess()
                        }

                    }, (err) => {
                        setSubmitting(false);

                        if (onFailure) {
                            onFailure(err)
                        }
                    });

                }}

            >
                {({
                    values,
                    errors,
                    touched,
                    handleChange,
                    handleBlur,
                    handleSubmit,
                    isSubmitting,
                    submitForm,
                    isValid
                    /* and other goodies */
                }) => (
                        <form onSubmit={handleSubmit}>
                            <Input
                                size="large"
                                style={inputBottomStyle}
                                type="email"
                                name="email"
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.email}
                                placeholder="Your Email"
                            />
                            {errors.email && touched.email &&
                                <Tag style={errorTagStyle}> {errors.email}</Tag>}
                            <Input
                                size="large"
                                style={inputBottomStyle}
                                type="password"
                                name="password"
                                onChange={handleChange}
                                onBlur={handleBlur}
                                value={values.password}
                                placeholder="Password"
                            />
                            {errors.password && touched.password && <Tag style={errorTagStyle}> {errors.password}</Tag>}

                            <Button
                                block
                                size="large"
                                style={{ marginTop: 20, backgroundColor: 'rgb(223, 123, 46)', color: 'white' }}
                                onClick={submitForm}
                                type="submit"
                                disabled={isSubmitting | (touched && !isValid)}>
                                Login
                            </Button>

                            <br />
                            <p>
                                Haven't have an account? <Link to={`/signup`} >Sign Up</Link>
                            </p>
                        </form>
                    )}
            </Formik>
        </React.Fragment>
    );
}

export default LoginForm;
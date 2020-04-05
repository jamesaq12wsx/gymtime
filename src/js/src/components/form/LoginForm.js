import React, {useContext} from 'react';
import { Button, Input, Tag, Row, Col } from 'antd';
import { Formik } from 'formik';
import {Link, useHistory} from 'react-router-dom';
import { AppContext } from '../../context/AppContextProvider';

const inputBottomStyle = { marginBottom: '5px' };

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const LoginForm = (props) => {

    const history = useHistory();

    const appContext = useContext(AppContext);
    const {state, dispatch} = appContext;

    return (
        <Formik
            initialValues={{ username: '', password: '' }}
            validate={values => {
                const errors = {};


                if (!values.username) {
                    errors.username = 'Username is Required';
                }

                if (!values.password) {
                    errors.password = 'Password is Required';
                }

                return errors;
            }}
            onSubmit={(values, { setSubmitting }) => {

                console.log("Login", values);

                const {auth} = state;

                auth.login(values, (token) => {
                    
                    setSubmitting(false);

                    dispatch({type:'LOGIN', payload: token});

                    if(props.onSuccess){
                        props.onSuccess()
                    }

                }, (err) => {
                    setSubmitting(false);

                    if(props.onFailure){
                        props.onFailure(err)
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
                            style={inputBottomStyle}
                            type="username"
                            name="username"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.username}
                            placeholder="Username"
                        />
                        {errors.username && touched.username &&
                            <Tag style={errorTagStyle}> {errors.username}</Tag>}
                        <Input
                            style={inputBottomStyle}
                            type="password"
                            name="password"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.password}
                            placeholder="Password"
                        />
                        {errors.password && touched.password && <Tag style={errorTagStyle}> {errors.password}</Tag>}

                        <Row>
                            <Col span={2} offset={20}>
                                <Button
                                    onClick={submitForm}
                                    type="submit"
                                    disabled={isSubmitting | (touched && !isValid)}>
                                    Login
                                </Button>
                            </Col>
                        </Row>

                        <Row>
                            <Col>
                                Haven't have an account? <Link onClick={() => {
                                    props.toSignUp();
                                    history.push('/signup');
                                }} to={`/signup`} >Sign Up</Link>
                            </Col>
                        </Row>
                    </form>
                )}
        </Formik>
    );
}

export default LoginForm;
import React, {useContext} from 'react';
import { Button, Input, Tag, Row, Col } from 'antd';
import { Formik } from 'formik';
import { login } from '../../api/client';

const inputBottomStyle = { marginBottom: '5px' };

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const LoginForm = (props) => {

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

                login(values)
                    .then(res => {
                        // console.log(res.headers);

                        // console.log(res.headers.get("Authorization"));

                        // dispatch({
                        //     type: 'LOGIN',
                        //     {
                        //         jwt: res.headers.get("Authorization")
                        //     }
                        // })

                        props.onSuccess(res);

                    })
                    .catch((err) => {
                        console.error(err);

                        props.onFailure(err);
                    })
                    .finally(() => {
                        setSubmitting(false);
                    });

                // TODO:
                // login(values)
                //     .then((res) => {
                //         console.log(res);

                //         props.onSuccess();

                //     })
                //     .catch((err) => {
                //         props.onFailure(err);
                //     })
                //     .finally(() => {
                //         setSubmitting(false);
                //     });
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
                    </form>
                )}
        </Formik>
    );
}

export default LoginForm;
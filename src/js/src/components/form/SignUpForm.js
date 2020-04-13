import React, { useContext } from 'react';
import { Button, Input, Tag, Row, Col } from 'antd';
import { Formik } from 'formik';
import { AppContext } from '../../context/AppContextProvider';

const inputBottomStyle = { marginBottom: '5px' };

const passwordReg = /(?=.*[a-z])(?=.*[@#$%!])(?=.*[A-Z])(?=.*d).{6,20}/g;

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const SignUpForm = (props) => {

    const appContext = useContext(AppContext);
    const { state, dispatch } = appContext;

    return (
        <Formik
            initialValues={{ username: '', email: '', password: '', passwordConfirm: '' }}
            validate={values => {
                const errors = {};

                if (!values.username) {
                    errors.username = 'Username is Required';
                }

                if (!values.password) {
                    errors.password = 'Password is Required';
                } else {
                    const res = values.password.match(passwordReg);
                    if (!res) {
                        errors.password = 'Password format is not valid';
                    } else {
                        if (res[0].localeCompare(values.password) !== 0) {
                            errors.password = 'Email format is not valid';
                        }
                    }
                }

                if (!values.passwordConfirm) {
                    errors.passwordConfirm = 'Confirm password is Required';
                } else {
                    if (values.passwordConfirm.localeCompare(values.password) !== 0) {
                        errors.passwordConfirm = 'Must as same as password';
                    }
                }

                if (!values.email) {
                    errors.email = 'Email is Required';
                } else {
                    let emailReg = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/g;

                    const regResult = values.email.match(emailReg);

                    if (!regResult) {
                        errors.email = 'Email format is not valid';
                    } else {
                        if (regResult[0].localeCompare(values.email) !== 0) {
                            errors.email = 'Email format is not valid';
                        }
                    }
                }

                return errors;
            }}
            onSubmit={props.onSubmit}
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

                        <h2>Sign up GymTime</h2>

                        <Input
                            size="large"
                            style={inputBottomStyle}
                            type="name"
                            name="name"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.username}
                            placeholder="Your Name"
                        />
                        {errors.name && touched.usernamename && <Tag style={errorTagStyle}> {errors.name}</Tag>}

                        <br />

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
                        {errors.email && touched.email && <Tag style={errorTagStyle}> {errors.email}</Tag>}

                        <br />

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

                        <br />

                        <Input
                            size="large"
                            style={inputBottomStyle}
                            type="passwordConfirm"
                            name="passwordConfirm"
                            onChange={handleChange}
                            onBlur={handleBlur}
                            value={values.passwordConfirm}
                            placeholder="Password Confirm"
                        />
                        {errors.passwordConfirm && touched.passwordConfirm && <Tag style={errorTagStyle}> {errors.passwordConfirm}</Tag>}

                        <Button
                            block
                            size="large"
                            style={{backgroundColor: 'rgb(223, 123, 46)', color: 'white', marginTop: 30}}
                            onClick={submitForm}
                            type="submit"
                            disabled={isSubmitting | (touched && !isValid)}>
                            SignUp
                        </Button>
                    </form>
                )}
        </Formik>
    );
}

export default SignUpForm;
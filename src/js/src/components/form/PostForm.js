import React, { useContext } from 'react';
import { Button, Input, Tag, Row, Col, TimePicker, Tooltip } from 'antd';
import { PlusCircleOutlined } from '@ant-design/icons';
import { Formik } from 'formik';
import { Link, useHistory } from 'react-router-dom';
import { AppContext } from '../../context/AppContextProvider';
import ClubSelect from '../ClubSelect';
import moment from 'moment';
import { InfoContext } from '../../context/InfoContextProvider';
import ExerciseSelect from '../ExerciseSelect';
var _ = require('lodash');

const inputBottomStyle = { marginBottom: '5px' };

const errorTagStyle = { backgroundColor: '#fc88a1', color: 'white', ...inputBottomStyle };

const timeFormat = 'HH:mm';

const PostForm = (props) => {

    const history = useHistory();

    const { initPost } = props;

    const initValues = {
        postUuid: initPost.postUuid || '',
        country: _.get(initPost, 'club.brand.country.alphaTwoCode', ''),
        club: _.get(initPost, 'club', {}),
        exerciseTime: moment(_.get(initPost, 'exerciseTime', moment().format())),
        exercises: _.get(initPost, 'exercises', []),
        privacy: _.get(initPost, 'privacy', 'PRIVATE')
    };

    const infoContext = useContext(InfoContext);
    const { state: infoState, dispatch } = infoContext;

    return (
        <Formik
            enableReinitialize
            initialValues={initValues}
            validate={values => {
                const errors = {};

                return errors;
            }}
            onSubmit={(values, { setSubmitting }) => {

                if(props.onSubmit){
                    props.onSubmit(values);
                }

                setSubmitting(false);

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
                isValid,
                setFieldValue
                /* and other goodies */
            }) => (
                    <form onSubmit={handleSubmit}>
                        <ClubSelect
                            clubs={infoState.clubs}
                            name="club"
                            value={values.club}
                            onChange={(value) => { values.club = value }}
                        />
                        {/* {errors.password && touched.password && <Tag style={errorTagStyle}> {errors.password}</Tag>} */}

                        <br />

                        <TimePicker
                            name="exerciseTime"
                            defaultValue={moment(values.exerciseTime)}
                            onChange={(value) => {
                                setFieldValue('exerciseTime', value.format());
                                // values.exerciseTime = value.format()
                            }}
                        />

                        <br />

                        {
                            values.exercises.map((ex, i) => {
                                // console.log('exercise input', ex);
                                return (
                                    <React.Fragment key={i}>
                                        <br />
                                        <Input.Group>
                                            <Row gutter={8}>
                                                <ExerciseSelect 
                                                    value={ex.name} 
                                                    onChange={(value) => values.exercises[i].name = value} />
                                                <Input
                                                    style={{ width: 200 }}
                                                    value={ex.description}
                                                    onChange={(e) => {
                                                        console.log(e.target.value);
                                                        values.exercises[i].description = e.target.value;
                                                        setFieldValue('exercises', values.exercises);
                                                    }} />
                                            </Row>
                                        </Input.Group>
                                    </React.Fragment>
                                );
                            })
                        }

                        <br />

                        <Tooltip title="Add Exercise">
                            <Button
                                onClick={() => {
                                    let exs = values.exercises.slice(0);

                                    exs.push({ name: '', description: '' });

                                    setFieldValue('exercises', exs);
                                }}
                                type="primary"
                                shape="circle"
                                icon={<PlusCircleOutlined />} />
                        </Tooltip>

                        <Row>
                            <Col span={2} offset={20}>
                                <Button
                                    onClick={submitForm}
                                    type="submit"
                                    disabled={isSubmitting | (touched && !isValid)}>
                                    Save
                                </Button>
                            </Col>
                        </Row>
                    </form>
                )}
        </Formik>
    );
}

export default PostForm;
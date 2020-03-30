import React, { useState, useContext, useEffect } from 'react';
import { Select, Row, Col, TimePicker, List, Tooltip, Button, Input, TreeSelect } from 'antd';
import moment from 'moment';
import { PostContext } from '../context/PostContextProvider';
import { InfoContext } from '../context/InfoContextProvider';
import { MdLocationOn, MdTimer } from "react-icons/md";
import { IoIosFitness } from "react-icons/io";
import { PlusCircleOutlined, DeleteTwoTone } from '@ant-design/icons';
var _ = require('lodash');


const { Option } = Select;
const { TreeNode } = TreeSelect;

const timeFormat = 'HH:mm';

const PostEdit = () => {

    const postContext = useContext(PostContext);
    const { state: postState, dispatch: postDispatch } = postContext;
    const { editingPost } = postState;

    const changed = _.cloneDeep(editingPost);
    const [editingPostChanged, setEditingPostChanged] = useState(changed);

    const infoContext = useContext(InfoContext);
    const { state: infoState } = infoContext;
    const { clubs, countries, exercises: systemExercises } = infoState;

    const [selectedCountry, setSelectedCountry] = useState('');
    const [selectedClub, setSelectedClub] = useState('');

    useEffect(() => {
        if (editingPost && clubs) {

            const postClubCountry = clubs.filter(c => c.uuid === editingPost.clubUuid)[0].country;

            setSelectedCountry(postClubCountry);

            setSelectedClub(editingPost.clubUuid);


        }
    }, [editingPost]);

    useEffect(() => {

        postDispatch({ type: 'SET_EDITING_CHANGE', payload: editingPostChanged });

    }, [editingPostChanged]);

    const onChange = (value) => {
        setSelectedCountry(value);
        setSelectedClub(null);
    }

    const onClubChange = (value) => {

        setSelectedClub(value);

        setEditingPostChanged({ ...editingPostChanged, clubUuid: value });

    }

    const onBlur = () => {
        console.log('blur');
    }

    const onFocus = () => {
        console.log('focus');
    }

    const onSearch = (val) => {
        console.log('search:', val);
    }

    const getCountryOptions = () => {

        if (countries) {
            return countries.filter(c => c.alphaTwoCode === 'TW' || c.alphaTwoCode === 'US').map(c => {
                return <Option key={c.name} value={c.name}>{c.name}</Option>
            });
        }

    }

    const getClubsOptions = () => {
        if (clubs && selectedCountry) {
            return clubs.sort().filter(c => c.country === selectedCountry).map(c => {
                return <Option key={c.uuid} value={c.uuid}>{c.name}</Option>
            })
        }
    }

    const getExerciseSelect = (i) => {
        if (systemExercises) {

            return (
                <TreeSelect
                    showSearch
                    style={{ width: '200px' }}
                    value={editingPostChanged.exercises[i].name}
                    dropdownStyle={{ maxHeight: 400, overflow: 'auto' }}
                    placeholder="Select Exercise"
                    allowClear
                    onChange={(value) => {

                        if(!value){
                            value = '';
                        }

                        console.log(value);
                        
                        var exs =  [...editingPostChanged.exercises ];

                        exs[i].name = value;

                        setEditingPostChanged({ ...editingPostChanged, exercises: exs });
                    }}
                // treeDefaultExpandAll
                // onChange={this.onChange}
                >
                    {getExerciseTreeNodeSelect(systemExercises)}
                </TreeSelect>
            );
        }
    }

    const getExerciseTreeNodeSelect = (exercises) => {
        const keys = Object.keys(exercises);

        return keys.map((category, i) => {
            return (
                <TreeNode selectable={false} key={i} title={category}>
                    {exercises[category].map((ex, j) => {
                        return <TreeNode key={ex.name} value={ex.name} title={ex.name} />
                    })}
                </TreeNode>
            )
        })

    }

    const getExerciseRows = () => {
        const { exercises } = editingPostChanged;

        // console.log('Get exercise row', exercises);

        if (exercises) {
            return exercises.map((ex, i) => {
                return (
                    <React.Fragment key={i}>
                        <br />
                        <Row key={i} justify='space-between'>
                            <Col>
                                <IoIosFitness fontSize='2rem' />
                            </Col>
                            <Col>
                                {getExerciseSelect(i)}
                            </Col>
                            <Col>
                                <Input
                                    value={editingPostChanged.exercises[i].description} 
                                    placeholder="Description"
                                    onChange={({ target: { value } }) => {

                                        if(!value){
                                            value = '';
                                        }
                
                                        console.log(value);
                                        
                                        var exs =  [...editingPostChanged.exercises ];
                
                                        exs[i].description = value;
                
                                        setEditingPostChanged({ ...editingPostChanged, exercises: exs });

                                    }}
                                />
                            </Col>
                            <Col>
                                <DeleteTwoTone
                                    onClick={() => {
                                        // postDispatch({ type: 'REMOVE_EXERCISE', payload: i });

                                        const { exercises } = editingPostChanged;

                                        exercises.splice(i, 1);

                                        setEditingPostChanged({ ...editingPostChanged, exercises: exercises });

                                    }}
                                    fontSize="2rem"
                                    twoToneColor="red"
                                />
                            </Col>
                        </Row>
                    </React.Fragment>
                );
            });
        }

    }

    return (
        <div className="post-edit">
            <Row justify='space-around'>
                <Col>
                    <MdLocationOn fontSize='2rem' />
                </Col>
                <Col>
                    <Select
                        value={selectedCountry}
                        showSearch
                        style={{ width: 200 }}
                        placeholder="Select country"
                        optionFilterProp="children"
                        onChange={onChange}
                        onFocus={onFocus}
                        onBlur={onBlur}
                        onSearch={onSearch}
                        filterOption={(input, option) =>
                            option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                        }
                    >
                        {getCountryOptions()}
                    </Select>
                </Col>
                <Col offset={1}>
                    <Select
                        value={selectedClub}
                        showSearch
                        style={{ width: 200 }}
                        placeholder="Select Club"
                        optionFilterProp="children"
                        onChange={onClubChange}
                        onFocus={onFocus}
                        onBlur={onBlur}
                        onSearch={onSearch}
                        filterOption={(input, option) =>
                            option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                        }
                    >
                        {getClubsOptions()}
                    </Select>
                </Col>
            </Row>
            <br />
            <Row justify='space-between'>
                <MdTimer fontSize='2rem' />
                <Col>
                    <TimePicker
                        defaultValue={moment(editingPostChanged.postTime)}
                        value={moment(editingPostChanged.postTime)}
                        onChange={(value) => {
                            // console.log('change time', value.format());
                            // postDispatch({ type: 'CHANGE_EXERCISE_TIME', payload: value.format() });
                            setEditingPostChanged({ ...editingPostChanged, postTime: value.format('YYYY-MM-DDTHH:mm:ss') });
                        }}
                        format={timeFormat}
                    />
                </Col>
            </Row>
            {getExerciseRows()}
            <br />
            <Tooltip title="Add Exercise">
                <Button
                    onClick={() => {
                        // postDispatch({ type: 'ADD_EXERCISE' });
                        const { exercises } = editingPostChanged;

                        var newExs = [...exercises, { name: '', description: '' }];

                        setEditingPostChanged({ ...editingPostChanged, exercises: newExs });
                    }}
                    shape="circle"
                    icon={<PlusCircleOutlined />}
                />
            </Tooltip>
        </div>
    )

}

export default PostEdit;
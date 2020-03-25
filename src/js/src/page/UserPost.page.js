import React, { useState, useEffect } from 'react';
import { Calendar, Badge, Select, Radio, Col, Row, Button } from 'antd';
import moment from 'moment';

const { Group, Button: RadioButton } = Radio;

function onPanelChange(value, mode) {
    console.log(value, mode);
}

const UserPost = () => {

    const [today, setToday] = useState(moment())
    const [selectedDate, setSelectedDate] = useState(moment());
    const [posts, setPosts] = useState([]);

    const dateCellRender = (date) => {
        return (
            <Badge status={'warning'} />
        );
    }

    const onPanelChange = (value) => {
        setToday(value);
    };

    return (
        <div className="user-post">
            <h3>Daily Workout</h3>
            <div className="site-calendar-demo-card">
                <Calendar
                    headerRender={({ value, type, onChange, onTypeChange }) => {
                        const start = 0;
                        const end = 12;
                        const monthOptions = [];

                        const current = value.clone();
                        const localeData = value.localeData();
                        const months = [];
                        for (let i = 0; i < 12; i++) {
                            current.month(i);
                            months.push(localeData.monthsShort(current));
                        }

                        for (let index = start; index < end; index++) {
                            monthOptions.push(
                                <Select.Option className="month-item" key={`${index}`}>
                                    {months[index]}
                                </Select.Option>,
                            );
                        }
                        const month = value.month();

                        const year = value.year();
                        const options = [];
                        for (let i = year - 10; i < year + 10; i += 1) {
                            options.push(
                                <Select.Option key={i} value={i} className="year-item">
                                    {i}
                                </Select.Option>,
                            );
                        }
                        return (
                            <div style={{ padding: 10 }}>
                                {/* <div style={{ marginBottom: '10px' }}>Custom header </div> */}
                                <Row style={{ flexWrap: 'nowrap' }} gutter={8}>
                                    <Col style={{ flex: 'none' }}>
                                        <Group size="small" onChange={e => onTypeChange(e.target.value)} value={type}>
                                            <RadioButton value="month">Month</RadioButton>
                                            <RadioButton value="year">Year</RadioButton>
                                        </Group>
                                    </Col>
                                    <Col style={{ flex: 'auto' }}>
                                        <Select
                                            size="small"
                                            dropdownMatchSelectWidth={false}
                                            className="my-year-select"
                                            onChange={newYear => {
                                                const now = value.clone().year(newYear);
                                                onChange(now);
                                            }}
                                            value={String(year)}
                                        >
                                            {options}
                                        </Select>
                                    </Col>
                                    <Col
                                        style={{ flex: 'auto' }}
                                    >
                                        <Select
                                            size="small"
                                            dropdownMatchSelectWidth={false}
                                            value={String(month)}
                                            onChange={selectedMonth => {
                                                const newValue = value.clone();
                                                newValue.month(parseInt(selectedMonth, 10));
                                                onChange(newValue);
                                            }}
                                        >
                                            {monthOptions}
                                        </Select>
                                    </Col>
                                    <Col>
                                        <Button
                                            onClick={() => {
                                                const newDate = selectedDate.clone().add(-1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                                // onChange(selectedDate.add(-1, 'month'))
                                            }}
                                        >-</Button>
                                        <Button
                                            onClick={() => {
                                                const newDate = selectedDate.clone.add(1, 'month');
                                                setToday(newDate);
                                                setSelectedDate(newDate);
                                            }}
                                        >+</Button>
                                    </Col>
                                </Row>
                            </div>
                        );
                    }}
                    value={today}
                    selectedDate={selectedDate}
                    onSelect={(date) => {
                        setSelectedDate(date);
                        setToday(date);
                    }}
                    onPanelChange={onPanelChange}
                    dateCellRender={dateCellRender}
                    fullscreen={false}
                    onPanelChange={onPanelChange}
                />
            </div>
            <div className="day-post">
                {`Selected Date ${selectedDate.format()}`}
            </div>
        </div>
    );
}

export default UserPost;
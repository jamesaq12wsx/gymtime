import React from 'react';
import { Select } from 'antd';

const { Option } = Select;


const CountryDropdown = ({ country, placeholder, defaultValue, value, showSearch, filterOption, onChange, style }) => {

    const onChangeHandler = (value) => {
        
        console.log('country change', value);

        if(onChange){
            onChange(value);
        }

    }

    const getCountryOptions = () => {
        return country.map(c => {
            return (
                <Option key={c.alphaTwoCode} value={c.alphaTwoCode}>{c.alphaTwoCode}</Option>
            )
        })
    }

    return (
        <Select 
            placeholder={placeholder ? placeholder : ''}
            showSearch={showSearch}
            filterOption={filterOption ? filterOption : (input, option) =>option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
            onChange={onChangeHandler} 
            defaultValue={defaultValue ? defaultValue : ''}
            value={value}
            style={style ? style : null}>
            {getCountryOptions()}
        </Select>
    );

}

export default CountryDropdown;
import React, { useState, useEffect, useContext } from 'react';
import { Select } from 'antd'
import { InfoContext } from '../context/InfoContextProvider';
var _ = require('lodash');

const { Option } = Select;

const ClubSelect = ({ defaultValue, value, showSearch, filterOption, onChange, style }) => {

    const infoContext = useContext(InfoContext);
    const { state: infoState } = infoContext;

    const groupClubByCountry = () => {
        return infoState.clubs.reduce((acc, c) => {
            if (c.brand && c.brand.country) {
                const brandName = _.get(c, 'brand.brandName');
                const countryCode = _.get(c, 'brand.country.alphaTwoCode');
                acc[countryCode] = acc[countryCode] || {};
                acc[countryCode][brandName] = acc[countryCode][brandName] || [];
                acc[countryCode][brandName].push(c);
                // acc[c.brand.country.alphaTwoCode]['clubs'].push(c);
            }
            return acc;
        }, {});
    }

    const [groupClubs, setGroupClubs] = useState(groupClubByCountry());
    const [selectCountry, setSelectCountry] = useState(_.get(value, 'brand.country.alphaTwoCode', ''));
    const [selectBrand, setSelectBrand] = useState(_.get(value, 'brand.brandName', ''));
    const [selectClub, setSelectClub] = useState(_.get(value, 'clubUuid', ''));

    useEffect(() => {
        setGroupClubs(groupClubByCountry());
    }, []);

    useEffect(() => {
        if (onChange) {
            onChange(infoState.clubs.find(c => c.clubUuid === selectClub) || {});
        }
    }, [selectClub]);

    const getCountryOptions = () => {
        // console.log('getCountryOptions', groupClubs);
        const countries = Object.keys(groupClubs);
        return countries.map(c => {
            return <Option key={c} value={c}>{c}</Option>
        })
    }

    const getBrandOptions = () => {
        if (groupClubs && selectCountry && selectCountry !== '') {
            const countryBrands = Object.keys(groupClubs[selectCountry]);
            return countryBrands.map((b, i) => {
                return <Option key={i} value={b}>{b}</Option>
            })
        }
    }

    const getClubOptions = () => {

        if (selectBrand && selectBrand !== '') {
            return groupClubs[selectCountry][selectBrand]
                .sort((a, b) => (a.clubName > b.clubName) ? 1 : -1)
                .map(c => {
                    return <Option key={c.clubUuid} value={c.clubUuid}>{`${c.clubName} - ${c.brand.brandName}`}</Option>
                });
        }
    }

    const countrySelectHandler = (value) => {
        setSelectCountry(value);
        setSelectBrand('');
        setSelectClub('');
    }

    const brandSelectHandler = (value) => {
        setSelectBrand(value);
        setSelectClub('');
    }

    const clubSelectHandler = (value) => {
        setSelectClub(value);

        if (onChange) {
            onChange(value);
        }
    }

    return (
        <div className="club-select">
            <Select
                placeholder="Select Country"
                showSearch
                defaultValue={selectCountry}
                value={selectCountry}
                style={{ width: 100 }}
                filterOption={(input, option) => {
                    return option.children.toLowerCase().includes(input.toLowerCase());
                }}
                onChange={countrySelectHandler}
            >
                {getCountryOptions()}
            </Select>
            <Select
                placeholder="Select Brand"
                showSearch
                defaultValue={selectBrand}
                value={selectBrand}
                style={{ width: 150 }}
                onChange={brandSelectHandler}
            >
                {getBrandOptions()}
            </Select>
            <Select
                placeholder='Select Club'
                showSearch
                // filterOption={filterOption ? filterOption : (input, option) => option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0}
                onChange={clubSelectHandler}
                defaultValue={selectClub}
                value={selectClub}
                style={{ width: 200 }}
            >
                {getClubOptions()}
            </Select>
        </div>
    );

}

export default ClubSelect;
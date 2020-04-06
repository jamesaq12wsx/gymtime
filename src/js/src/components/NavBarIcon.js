import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';

const hoverStyle = {
    backgroundColor: 'rgba(163, 163, 163, 0.5)',
    borderRadius: '5px',
};

const iconStyle = {
    width: '40px',
    height: '40px',
    padding: '4px'
}

const selectStyle = {
    color: 'rgb(223, 123, 46)'
}

const NavBarIcon = ({ pathName, children }) => {

    const location = useLocation();

    console.log('nav bar icon location', location);

    const [hover, setHover] = useState(false);

    const [style, setStyle] = useState(iconStyle);

    const onHover = () => setHover(true);

    const hoverMove = () => setHover(false)

    useEffect(() => {
        let newStyle = iconStyle;
        if(hover){
            newStyle = {...newStyle, ...hoverStyle};
        }
        if(location.pathname === pathName){
            newStyle = {...newStyle, ...selectStyle}
        }

        setStyle(newStyle);

    },[location, hover])

    return (
        <div
            className="nav-bar-icon"
            onMouseEnter={() => onHover()}
            onMouseLeave={() => hoverMove()}
            style={style}
        >
            {children}
        </div>
    )
}

NavBarIcon.defaultProps = {
    pathName: ''
}

export default NavBarIcon;
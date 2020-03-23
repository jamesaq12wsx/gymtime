import React, { useState } from 'react';

const hoverStyle = {
    square: {
        backgroundColor: 'rgba(163, 163, 163, 0.5)',
        borderRadius: '5px'
    },
    circle: {
        backgroundColor: 'rgba(163, 163, 163, 0.5)',
        borderRadius: '30px'
    }
};

const NavBarIcon = (props) => {

    const {style='square'} = props;

    const [hover, setHover] = useState(false);

    const onHover = () => setHover(true);

    const hoverMove = () => setHover(false)

    return (
        <div
            onMouseEnter={() => onHover()}
            onMouseLeave={() => hoverMove()}
            style={hover ? hoverStyle[style] : {}}
        >
            {props.children}
        </div>
    )
}

export default NavBarIcon;
import React from 'react';

const Container = props => {
    return (
        <div style={{textAlign:'center'}}>
            {props.children}
        </div>
    );
}

export default Container;
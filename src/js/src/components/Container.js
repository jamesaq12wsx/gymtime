import React from 'react';

const Container = props => {
    return (
        <div style={{textAlign:'center', padding: '10px'}}>
            {props.children}
        </div>
    );
}

export default Container;
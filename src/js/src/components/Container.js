import React from 'react';

const Container = props => {
    return (
        <div style={{width:'95%', margin:'0 auto', textAlign:'center', padding: '10px'}}>
            {props.children}
        </div>
    );
}

export default Container;
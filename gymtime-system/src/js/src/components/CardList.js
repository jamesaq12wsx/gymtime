import React from 'react';
import { List } from 'antd';

const CardList = ({ cards, header, grid, className, locale }) => {

    return (
        <List
            className={className}
            locale={locale}
            size='large'
            header={header ? header : ''}
            grid={grid ? grid :
                {
                    gutter: 16,
                    xs: 1,
                    sm: 1,
                    md: 2,
                    lg: 2,
                    xl: 4,
                    xxl: 3,
                }}
            dataSource={cards}
            renderItem={item => <List.Item>{item}</List.Item>}
        >
        </List>
    );
}

export default CardList;
import React from 'react';
import { Line } from 'react-chartjs-2';

let hours = [];

for(let i =0; i<=24; i++){
    hours.push(i);
}

const state = {
    labels: hours,
    datasets: [
        {
            label: 'Rainfall',
            fill: false,
            lineTension: 0.5,
            backgroundColor: 'rgba(75,192,192,1)',
            borderColor: 'rgba(0,0,0,1)',
            borderWidth: 2,
            data: [65, 59, 80, 81, 56]
        }
    ]
};

const PostChart = (props) => {

    const { posts } = props;

    console.log(new Date(posts[0]['dateTime']));

    

    return <Line
        data={state}
        options={{
            title: {
                display: true,
                text: 'People work out Today',
                fontSize: 20
            },
            // legend: {
            //     display: true,
            //     position: 'right'
            // }
        }}
    />;

}

export default PostChart;
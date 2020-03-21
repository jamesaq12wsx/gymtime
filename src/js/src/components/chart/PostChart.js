import React from 'react';
import { Line } from 'react-chartjs-2';

let hours = [];

for(let i =0; i<24; i++){
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

    const { today, lastWeek } = props;

    console.log(today.sort((a,b) => {
        if(new Date(a.dateTime) < new Date(b.dateTime)){
            return -1;
        }else if(new Date(a.dateTime) > new Date(b.dateTime)){
            return 1;
        }
        return 0;
    }));

    const chartSet = {
        labels: hours,
        datasets: [
            {
                label: 'Workout',
                fill: false,
                lineTension: 0.5,
                backgroundColor: 'rgba(75,192,192,1)',
                borderColor: 'rgba(75,192,192,1)',
                borderWidth: 2,
                data: today.sort((a,b) => {
                    if(new Date(a.dateTime) < new Date(b.dateTime)){
                        return -1;
                    }else if(new Date(a.dateTime) > new Date(b.dateTime)){
                        return 1;
                    }
                    return 0;
                }).map(p => p.count),
                borderDash: [10,5]
            },
            {
                label: 'Last Week',
                fill: true,
                lineTension: 0.5,
                backgroundColor: 'rgba(255,0,0,0.5)',
                borderColor: 'rgba(255,0,0,0.5)',
                data: lastWeek.sort((a,b) => {
                    if(new Date(a.dateTime) < new Date(b.dateTime)){
                        return -1;
                    }else if(new Date(a.dateTime) > new Date(b.dateTime)){
                        return 1;
                    }
                    return 0;
                }).map(p => {
                    return p.count-5 < 0 ? 0 : p.count -5;
                })
            }
        ]
    };

    return <Line
        data={chartSet}
        options={{
            title: {
                display: true,
                text: `People Workout Today`,
                fontSize: 20
            },
            elements: {
                point: {
                    radius: 1
                }
            }
            // legend: {
            //     display: true,
            //     position: 'right'
            // }
        }}
    />;

}

export default PostChart;
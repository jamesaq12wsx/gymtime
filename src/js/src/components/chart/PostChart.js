import React from 'react';
import { Line } from 'react-chartjs-2';

let hours = [];

for(let i =0; i<24; i++){
    hours.push(i);
}

const PostChart = (props) => {

    const { today, lastWeek } = props;

    // console.log(today.sort((a,b) => {
    //     if(new Date(a.dateTime) < new Date(b.dateTime)){
    //         return -1;
    //     }else if(new Date(a.dateTime) > new Date(b.dateTime)){
    //         return 1;
    //     }
    //     return 0;
    // }));

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
                fontSize: 16
            },
            elements: {
                point: {
                    radius: 1
                }
            },
            scales: {
                yAxes: [{
                    gridLines: {
                        drawBorder: false
                    },
                    ticks: {
                        max: 200,
                        min: 0,
                        // stepSize: 10
                    }
                }],
                xAxes: [
                    {
                        gridLines: {
                            display: false
                        }
                    }
                ]
            }
            // legend: {
            //     display: true,
            //     position: 'right'
            // }
        }}
    />;

}

export default PostChart;
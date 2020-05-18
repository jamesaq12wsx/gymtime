import React from 'react';
import { Line } from 'react-chartjs-2';

const BodyFatChart = ({ bodyFats }) => {

    const getChart = () => {
        if (bodyFats) {
            const chartSet = {
                labels: bodyFats.map(w => w.date.toLocaleDateString()),
                datasets: [
                    {
                        label: 'Body Fat (%)',
                        fill: false,
                        lineTension: 0.5,
                        backgroundColor: 'rgba(75,192,192,1)',
                        borderColor: 'rgba(75,192,192,1)',
                        borderWidth: 2,
                        data: bodyFats.map(bf => bf.value),
                        borderDash: [10, 5]
                    }
                ]
            };

            return (
                <Line
                    data={chartSet}
                    options={{
                        title: {
                            display: true,
                            text: "Body Fat (%)",
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
                                    min: 0
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
                    }}
                />
            );
        }
    }

    return (
        <div className="body-fat-chart">
            {getChart()}
        </div>
    )
}

export default BodyFatChart;
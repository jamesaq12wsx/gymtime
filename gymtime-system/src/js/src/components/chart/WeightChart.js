import React from 'react';
import { Line } from 'react-chartjs-2';

const WeightChart = ({ weights }) => {

    const getChart = () => {
        if (weights) {
            const chartSet = {
                labels: weights.map(w => w.date.toLocaleDateString()),
                datasets: [
                    {
                        label: 'weight',
                        fill: false,
                        lineTension: 0.5,
                        backgroundColor: 'rgba(75,192,192,1)',
                        borderColor: 'rgba(75,192,192,1)',
                        borderWidth: 2,
                        data: weights.map(w => w.value),
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
                            text: "Your Weights",
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
        <div className="weight-chart">
            {getChart()}
        </div>
    )
}

export default WeightChart;
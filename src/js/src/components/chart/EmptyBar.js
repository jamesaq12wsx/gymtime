import React, { useEffect } from 'react';
import { Bar } from 'react-chartjs-2';

const EmptyBar = () => {

    // let data = {
    //     labels: ["January", "February", "March", "April"],
    //     datasets: [{
    //         backgroundColor: 'rgb(255, 99, 132)',
    //         borderColor: 'rgb(255, 99, 132)',
    //         data: [20, 30, 14, 58]
    //     }]
    // };

    return (
        <div>
            < Bar
                // data={data}
            />
        </div>
    );

}

export default EmptyBar;
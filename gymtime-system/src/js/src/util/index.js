export const convertUnit = (toUnit, fromUnit, value) => {
    if(fromUnit.measurementType === toUnit.measurementType){
        if(toUnit.id === fromUnit.id){
            return value;
        }

        /**
         * pound to kg
         * kg to pound
         */
        if(fromUnit.id === 3){
            return (0.453592 * value).toFixed(1);
        }
        if(fromUnit.id === 4){
            return (value * 2.20462).toFixed(1);
        }

        /**
         * inch to cm
         * cm to inch
         */
        if(fromUnit.id === 2){
            return (2.54 * value).toFixed(1);
        }
        if(fromUnit.id === 1){
            return (value * 0.393701).toFixed(1);
        }

        /**
         * mile to km
         * km to mile
         */
        if(fromUnit.id === 5){
            return (1.60934 * value).toFixed(1);
        }
        if(fromUnit.id === 6){
            return (value * 0.621371).toFixed(1);
        }


    }else{
        return null;
    }
}
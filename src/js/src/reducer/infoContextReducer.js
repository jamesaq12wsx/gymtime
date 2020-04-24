export const infoContextReducer = (state, action) => {

    switch (action.type) {

        case 'SET_COUNTRIES':

            state = { ...state, countries: action.payload };

            break;

        case 'CLEAR_COUNTRIES':

            state = { ...state, countries: [] };

            break;

        case 'SET_EXERCISES':

            state = { ...state, exercises: action.payload };

            break;

        case 'CLEAR_EXERCISES':

            state = { ...state, exercises: [] };

            break;

    }

    return state;
}
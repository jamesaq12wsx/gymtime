export const clubContextReducer = (state, action) => {

    switch (action.type) {

        case 'FETCHING_NEAR_CLUBS':

            state = {...state, fetching: true};
            
            break;

        case 'FETCHED_NEAR_CLUBS':

            state = {...state, nearClubs: action.payload, fetching: false};

            break;
    }

    return state;
}
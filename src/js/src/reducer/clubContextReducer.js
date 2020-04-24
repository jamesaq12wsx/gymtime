export const clubContextReducer = (state, action) => {

    console.log('clubContextReducer', action);

    switch (action.type) {

        case clubContextReducerType.FETCHING:

            state = { ...state, fetching: true, fetchFailed: false };

            break;

        case clubContextReducerType.SET_CLUBS:

            state = { ...state, fetching: false, fetchFailed: false, nearClubs: action.payload };

            break;

        case clubContextReducerType.FETCH_FAILED:

            state = { ...state, fetching: false, fetchFailed: true };

            break;

    }

    return state;
}

export const clubContextReducerType = {
    FETCHING: 'FETCHING',
    FETCHED: 'FETCHED',
    SET_CLUBS: 'SET_CLUBS',
    FETCH_FAILED: 'FETCH_FAILED'
}
export const clubContextReducer = (state, action) => {

    switch (action.type) {

        case clubContextReducerType.FETCHING:

            state = {...state, fetching: true};
            
            break;

        case clubContextReducerType.FETCHED:

            state = {...state, fetching: false};

            break;
        
    }

    return state;
}

export const clubContextReducerType = {
    FETCHING: 'FETCHING',
    FETCHED: 'FETCHED'
}
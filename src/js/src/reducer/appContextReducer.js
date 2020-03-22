export const appContextReducer = (state, action) => {

    switch(action.type){
        case 'LOGIN':
            state = {...state, login: true, jwt: action.value};
            break;
        case 'LOGOUT':
            state = {...state, login: false, jwt: null};
            break;
    }

    return state;
}
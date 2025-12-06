import {createAsyncThunk, createSlice} from '@reduxjs/toolkit';
import axios from 'axios';
import {getResults, logoutClearResults} from './ResultAction';

export const login = createAsyncThunk('auth/login', async ({ username, password }, { rejectWithValue, dispatch }) => {
    if (!username ||!password) {
        return rejectWithValue('Username and password are required');
    }
    try {
        const response = await axios.post('http://localhost:8080/back/api/users/login', {username, password}, { withCredentials: true });
        dispatch(getResults({}, undefined));
        console.log(response.data)
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

export const register = createAsyncThunk('auth/register', async ({ username, password },  { rejectWithValue}) => {
    if (!username ||!password) {
        return rejectWithValue('Username and password are required');
    }
    try {
        const response = await axios.post('http://localhost:8080/back/api/users/register', { username, password }, { withCredentials: true });
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message);
        }
        return rejectWithValue('An error occurred during the request.');
    }
});

export const logout = createAsyncThunk('auth/logout', async (_, { rejectWithValue, dispatch }) => {
    try {
        const response = await axios.post('http://localhost:8080/back/api/users/logout', {},{ withCredentials: true });
        dispatch(logoutClearResults());
        return response.data;
    } catch (error) {
        if (error.response) {
            return rejectWithValue(error.response.data.message || 'Logout failed');
        }
        return rejectWithValue('An error occurred during logout.');
    }
});

export const checkLoginStatus = createAsyncThunk('auth/checkLoginStatus',
    async (_, { rejectWithValue }) => {
        try {
            const response = await axios.get('http://localhost:8080/back/api/users/check-login', { withCredentials: true });
            console.log(response.data)
            return response.data;
        } catch (err) {
            return rejectWithValue(err.response.data);
        }
    }
);

const authSlice = createSlice({
    name: 'auth',
    initialState: {
        isAuthenticated: false,
        username: null,
        info: null,
        error: null
    },
    extraReducers: (builder) => {
        builder
            .addCase(login.fulfilled, (state, action) => {
                if (action.payload.status === "200") {
                    state.isAuthenticated = true;
                    state.token = action.payload.token;
                    state.username = action.payload.username;
                    state.error = null;
                } else {
                    state.error = action.payload.message;
                }
            })
            .addCase(login.rejected, (state, action) => {
                state.error = action.payload;
            })
            .addCase(register.fulfilled, (state, action) => {
                state.info = action.payload.message;
                state.error = null;
            })
            .addCase(register.rejected, (state, action) => {
                state.info = null;
                state.error = action.payload;
            })
            .addCase(logout.fulfilled, (state, action) => {
                state.isAuthenticated = false;
                state.token = null;
                state.username = null;
                state.info = action.payload.message || 'Logout successful';
                state.error = null;
            })
            .addCase(logout.rejected, (state, action) => {
                state.error = action.payload;
            })
            .addCase(checkLoginStatus.fulfilled, (state, action) => {
                state.isAuthenticated = action.payload.isAuthenticated;
                state.username = action.payload.username;
                state.error = null;
            })
            .addCase(checkLoginStatus.rejected, (state, action) => {
                state.isAuthenticated = false;
                state.username = null;
                state.error = action.payload || 'Session expired';
            });
    }
});

export default authSlice.reducer;
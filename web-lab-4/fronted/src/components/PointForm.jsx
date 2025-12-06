import React, { useEffect, useState } from 'react';
import {
    Alert,
    Box,
    Button,
    Container,
    FormControl,
    InputLabel,
    Snackbar,
    Stack,
    Slider,
    Typography,
    TextField
} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { check, clear, getResults } from "../action/ResultAction";

const PointForm = ({ r = 1, setR }) => {
    const { username } = useSelector((state) => state.auth);
    const { error, info } = useSelector((state) => state.results);
    const dispatch = useDispatch();

    const [x, setX] = useState(0);
    const [y, setY] = useState(0);

    const handleXChange = (e) => {
        const inputValue = e.target.value.trim();
        if (!inputValue) {
            setX(0);
            return;
        }
        const numValue = parseFloat(inputValue);
        const intValue = Number.isNaN(numValue) ? 0 : Math.round(numValue);
        const clampedValue = Math.max(-5, Math.min(3, intValue));
        setX(clampedValue);
    };

    const handleYChange = (value) => {
        const numValue = parseFloat(value);
        if (numValue >= -3 && numValue <= 3) {
            setY(numValue);
        }
    };

    const handleRChange = (e) => {
        const inputValue = e.target.value.trim();
        if (!inputValue) {
            setR(1);
            return;
        }
        const numValue = parseFloat(inputValue);
        let intValue = Number.isNaN(numValue) ? 1 : Math.round(numValue);
        intValue = intValue > 0 ? intValue : 1;
        const clampedValue = Math.max(1, Math.min(3, intValue));
        setR(clampedValue);
    };

    const checkPoint = () => {
        dispatch(check({ x, y, r, username }));
        dispatch(getResults({ username }));
        console.log(`X: ${x}, Y: ${y}, R: ${r}`);
    };

    const clearPoint = () => {
        dispatch(clear({ username }));
    };

    const [openErrorSnackbar, setOpenErrorSnackbar] = useState(false);
    const [openInfoSnackbar, setOpenInfoSnackbar] = useState(false);

    useEffect(() => {
        if (error) {
            setOpenErrorSnackbar(true);
            const timer = setTimeout(() => {
                setOpenErrorSnackbar(false);
            }, 1000);
            return () => clearTimeout(timer);
        }
    }, [error]);

    useEffect(() => {
        if (info) {
            setOpenInfoSnackbar(true);
            const timer = setTimeout(() => {
                setOpenInfoSnackbar(false);
            }, 1000);
            return () => clearTimeout(timer);
        }
    }, [info]);

    return (
        <div className="card">
            <Container>
                <Snackbar
                    open={openErrorSnackbar}
                    autoHideDuration={5000}
                    onClose={() => setOpenErrorSnackbar(false)}
                >
                    <Alert severity="error">{error}</Alert>
                </Snackbar>
                <Snackbar
                    open={openInfoSnackbar}
                    autoHideDuration={5000}
                    onClose={() => setOpenInfoSnackbar(false)}
                >
                    <Alert severity="success">{info}</Alert>
                </Snackbar>
                <Stack alignItems="center" justifyContent="center">
                    <Box>
                        <h1>Point check</h1>
                    </Box>
                </Stack>
            </Container>
            <form id="point-form">
                <Stack direction="row" spacing={3} sx={{ width: '100%', alignItems: 'center' }}>
                    <FormControl sx={{ width: 120 }}>
                        <TextField
                            id="x-select"
                            type="number"
                            label={"X"}
                            value={x}
                            onChange={handleXChange}
                            InputProps={{
                                inputProps: {
                                    min: -5,
                                    max: 3,
                                    step: "any"
                                }
                            }}
                            sx={{ width: 120 }}
                        />
                    </FormControl>

                    <Stack direction="column" spacing={1} sx={{ width: 200 }}>
                        <InputLabel id="y-label">Y</InputLabel>
                        <Slider
                            id="y-slider"
                            value={y}
                            min={-5}
                            max={5}
                            step={0.1}
                            onChange={(_, value) => handleYChange(value)}
                            sx={{ width: '100%' }}
                        />
                        <Typography variant="body2" textAlign="center">
                            {y.toFixed(1)}
                        </Typography>
                    </Stack>

                    <FormControl sx={{ width: 120 }}>
                        <TextField
                            id="r-select"
                            type="number"
                            label={"R"}
                            value={r}
                            onChange={handleRChange}
                            sx={{ width: 120 }}
                            InputProps={{
                                inputProps: {
                                    min: 1,
                                    max: 3,
                                    step: "any"
                                }
                            }}
                        />
                    </FormControl>

                    <Stack direction="row" spacing={2} sx={{ flexGrow: 1, justifyContent: 'center' }}>
                        <Button variant="contained" onClick={checkPoint}>
                            Check
                        </Button>
                        <Button variant="contained" onClick={clearPoint}>
                            Clear
                        </Button>
                    </Stack>
                </Stack>
            </form>
        </div>
    );
};

export default PointForm;
import React, {useState} from 'react';
import {Step, StepLabel, Stepper, TextField} from "@mui/material";
import "./Register.css"
import Button from "@mui/material/Button";
import axios from "axios";

const steps = ['Input your name and surname', 'Input your credentials and date of birth']

const Register = () => {
    const [data, setData] = useState({
        username: '',
        email: '',
        password: '',
    });

    const handleSetData = (e) => {
        setData((prevData) => ({ ...prevData, [e.target.id]: e.target.value }));
    }

    const handleRegister = async () => {
        await axios.post('http://localhost:8080/api/auth/signup', data)
            .then((response) => {
                console.log(response);
            })
    }


    return (
        <div className={"register-class"}>

            <div className="input-group">
                <TextField
                    id="username"
                    label="Username"
                    defaultValue=""
                    help-value="string"
                    onChange={handleSetData}
                />

                <TextField
                    id="email"
                    label="Email"
                    defaultValue=""
                    help-value="string"
                    onChange={handleSetData}
                    // error
                    // helperText="Incorrect entry."
                />

                <TextField
                    id="password"
                    label="Password"
                    defaultValue=""
                    help-value="string"
                    type="password"
                    onChange={handleSetData}
                    // error
                    // helperText="Incorrect entry."
                />
            </div>

            <div className="controls">

                <Button
                    color="inherit"
                    onClick={handleRegister}
                    sx={{mr: 1}}
                >
                    Register
                </Button>
            </div>

        </div>
    );
};

export default Register;
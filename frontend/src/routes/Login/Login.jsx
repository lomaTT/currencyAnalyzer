import "./Login.css";
import {Alert, Checkbox, TextField} from "@mui/material";
import {useEffect, useState} from "react";
import {AccountCircle} from "@mui/icons-material";
import Box from "@mui/material/Box";
import LockIcon from '@mui/icons-material/Lock';
import Button from "@mui/material/Button";
import {Navigate} from 'react-router-dom'

const logIn = async (username, password) => {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Headers': "*" },
        body: JSON.stringify({ username: username, password: password })
    };
    return await fetch('http://localhost:8080/api/login', requestOptions)
        .then(response => response.json())
}


const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [rememberMe, setRememberMe] = useState(false);
    const [correctCredentials, setCorrectCredentials] = useState({});

    return (
        <div className="login">
            Hello there! Please, Log in.

            <Box sx={{ display: 'flex', alignItems: 'flex-end', mt: '10px' }}>
                <AccountCircle sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField
                    id="login-textfield"
                    label="Username"
                    variant={"standard"}
                    value={username}
                    onChange={(event) => {setUsername(event.target.value);}}
                />
            </Box>

            <Box sx={{ display: 'flex', alignItems: 'flex-end', mt: '10px' }}>
                <LockIcon sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField
                    id="password-textfield"
                    label="Password"
                    variant={"standard"}
                    value={password}
                    onChange={(event) => {setPassword(event.target.value);}}
                />
            </Box>

            <div className="remember-box">
                <Checkbox label="Remember me"
                          onChange={(event) => {setRememberMe(!rememberMe)}}
                          checked={rememberMe}
                /> Remember me
            </div>

            <Button variant="outlined"
                    onClick={() => logIn(username, password).then(r => setCorrectCredentials(r))}
            >Log in</Button>
            <div className="login-error">
                {(correctCredentials.status === "incorrectCredentials") ?
                    <Alert severity="error">Please, provide correct credentials!</Alert> : ""}
                {(correctCredentials.status === "ok") ? <Navigate to='/home' /> : ""}

            </div>
        </div>
    );
};

export default Login;
import "./Login.css";
import {Alert, Checkbox, TextField} from "@mui/material";
import {useEffect, useState} from "react";
import {AccountCircle} from "@mui/icons-material";
import Box from "@mui/material/Box";
import LockIcon from '@mui/icons-material/Lock';
import Button from "@mui/material/Button";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {useAuth} from "../../providers/auth-provider";

const Login = ({}) => {
    const Auth = useAuth();
    const navigate = useNavigate();
    const [rememberMe, setRememberMe] = useState(false);
    const [correctCredentials, setCorrectCredentials] = useState();

    const [data, setData] = useState({
        username: '',
        password: '',
    });

    const handleSetData = (e) => {
        setData((prevData) => ({ ...prevData, [e.target.id]: e.target.value }));
    }

    const handleLogin = async () => {
        axios.post('http://localhost:8080/api/auth/signin', data, {withCredentials: true})
            .then((response) => {
                setCorrectCredentials(true);

                const { id, username, roles, email } = response.data;
                const authData = window.btoa(data.username + ':' + data.password)
                const authenticatedUser = { id, username, roles, email, authData }

                setTimeout(() => {
                    Auth.userLogin(authenticatedUser);
                    navigate('/dashboard');
                }, 5000);
            }).catch((error) => {
            setCorrectCredentials(false);
        })
    }

    return (
        <div className="login">
            Hello there! Please, Log in.

            <Box sx={{ display: 'flex', alignItems: 'flex-end', mt: '10px' }}>
                <AccountCircle sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField
                    id="username"
                    label="Username"
                    variant={"standard"}
                    value={data.username}
                    onChange={handleSetData}
                />
            </Box>

            <Box sx={{ display: 'flex', alignItems: 'flex-end', mt: '10px' }}>
                <LockIcon sx={{ color: 'action.active', mr: 1, my: 0.5 }} />
                <TextField
                    id="password"
                    label="Password"
                    variant={"standard"}
                    value={data.password}
                    onChange={handleSetData}
                />
            </Box>

            <div className="remember-box">
                <Checkbox label="Remember me"
                          onChange={(event) => {setRememberMe(!rememberMe)}}
                          checked={rememberMe}
                /> Remember me
            </div>

            <Button variant="outlined"
                    onClick={handleLogin}
            >Log in</Button>
            <div className="login-error">
                {(correctCredentials === false) ?
                    <Alert severity="error">Please, provide correct credentials!</Alert>
                    : (correctCredentials === true) ?
                        <Alert severity="success">You successfully logged in. Redirecting....</Alert>
                            : <Alert severity="info">Provide your credentials.</Alert>
                        }

            </div>
        </div>
    );
};

export default Login;
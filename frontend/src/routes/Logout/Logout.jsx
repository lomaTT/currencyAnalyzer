import React, {useEffect} from 'react';
import {useAuth} from "../../providers/auth-provider";
import {redirect, useNavigate} from "react-router-dom";
import './Logout.css';

const Logout = () => {
    const { userLogout } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        setTimeout(() => {
            userLogout();
            navigate('/');
        }, 5000);
    }, []);


    return (
        <div className="main-div">
            <div>You successfully logged out! Redirecting...</div>
        </div>
    );
};

export default Logout;
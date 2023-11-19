import React, {useEffect} from 'react';
import {useAuth} from "../../providers/auth-provider";
import {redirect, useNavigate} from "react-router-dom";

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
        <div>
            You successfully logged out! Redirecting...
        </div>
    );
};

export default Logout;
import React from 'react';
import {useAuth} from "../../providers/auth-provider";
import {Navigate, useNavigate} from "react-router-dom";
import RedirectProvider from "../../providers/redirect-provider";

const Dashboard = () => {
    const Auth = useAuth()

    return (
        <div>
            <RedirectProvider />
            dashboard
        </div>
    );
};

export default Dashboard;
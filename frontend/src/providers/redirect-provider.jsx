import React from 'react';
import {useAuth} from "./auth-provider";
import {Navigate} from "react-router-dom";

const RedirectProvider = () => {
    const Auth = useAuth()
    return (
        <div>
            {!Auth.userCheck() ? (<Navigate to={'/'} />) : <></>}
        </div>
    );
};

export default RedirectProvider;
import React from 'react';
import {useAuth} from "../../providers/auth-provider";
import {Link, Navigate} from "react-router-dom";
import Button from "@mui/material/Button";
import RedirectProvider from "../../providers/redirect-provider";

const Profile = () => {
    const Auth = useAuth();

    return (
        <div>
            <RedirectProvider />
            <div className="main-description">
                Your profile
                <div>
                    <Button variant="contained" component={Link} to={"/profile/add-currency"}>Add currency</Button>
                </div>
                {/* Table of currencies */}

            </div>
        </div>
    );
};

export default Profile;
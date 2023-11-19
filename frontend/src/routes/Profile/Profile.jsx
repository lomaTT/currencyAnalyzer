import React from 'react';
import {useAuth} from "../../providers/auth-provider";

const Profile = () => {
    const Auth = useAuth()
    const user = Auth.getUser()
    const isUser = user.roles

    return (
        <div>
            Profile component!
        </div>
    );
};

export default Profile;
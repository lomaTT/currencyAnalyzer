import React, {createContext, useContext, useEffect, useState} from 'react'

const AuthContext = createContext()

function AuthProvider({children}) {
    const [user, setUser] = useState(null)

    useEffect(() => {
        const storedUser = JSON.parse(localStorage.getItem('user'))
        setUser(storedUser)
    }, [])

    const getUser = () => {
        // console.log(user);
        return JSON.parse(localStorage.getItem('user'))
    }

    const userIsAuthenticated = () => {
        return localStorage.getItem('user') !== null
    }

    const userLogin = user => {
        localStorage.setItem('user', JSON.stringify(user))
        setUser(user)
    }

    const userLogout = () => {
        localStorage.removeItem('user')
        setUser(null)
    }

    const userCheck = () => {
        if (typeof localStorage.getItem('user') !== null && localStorage.getItem('user') !== null) {
            const userDataSession = JSON.parse(localStorage.getItem('user'));
            let convertedUserData = []
            try {
                convertedUserData = (window.atob(JSON.parse(localStorage.getItem('user')).authData).split(':'));
            } catch (e) {
                setUser(null);
                localStorage.removeItem('user')
                return false;
            }
            
            if (typeof userDataSession !== null && convertedUserData !== null) {

                if (String(userDataSession['id']) === convertedUserData[0]
                    && userDataSession['username'] === convertedUserData[1]) {
                    return true;
                } else {
                    localStorage.removeItem('user')
                    return false;
                }
            } else {
                localStorage.removeItem('user')
                return false;
            }
        } else {
            localStorage.removeItem('user')
            return false;
        }
    }

    const contextValue = {
        user,
        getUser,
        userIsAuthenticated,
        userLogin,
        userLogout,
        userCheck
    }

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContext

export function useAuth() {
    return useContext(AuthContext)
}

export {AuthProvider}
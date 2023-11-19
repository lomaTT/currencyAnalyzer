import React, {useEffect, useState} from 'react';
import "./HeaderComponent.css";
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AdbIcon from '@mui/icons-material/Adb';
import theme from "../../public/help-js/palette";
import {ThemeProvider} from "@mui/material/styles";
import {Link, useLocation} from "react-router-dom";
import {useAuth} from "../../providers/auth-provider";

const pages = ['currency-rates', '2', '3'];


const HeaderComponent = ({}) => {
    const [anchorElNav, setAnchorElNav] = React.useState(null);
    const [anchorElUser, setAnchorElUser] = React.useState(null);
    const [settings, setSettings] = useState([]);
    const { getUser, userIsAuthenticated, userLogout } = useAuth();


    const currentPathName = useLocation().pathname;

    useEffect(() => {

        if (!userIsAuthenticated()) {
            setSettings(['Login', 'Register']);
        } else {
            setSettings(['Profile', 'Dashboard', 'Settings', 'Logout']);
        }

    }, [currentPathName]);


    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget);
    };
    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget);
    };

    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    return (
        <div className="header">
            <ThemeProvider theme={theme}>
                <AppBar position="static">
                    <Container maxWidth="xl">
                        <Toolbar disableGutters>
                            <AdbIcon sx={{display: {xs: 'none', md: 'flex'}, mr: 1}}/>
                            <Typography
                                variant="h6"
                                noWrap
                                component="a"
                                href="#app-bar-with-responsive-menu"
                                sx={{
                                    mr: 2,
                                    display: {xs: 'none', md: 'flex'},
                                    fontFamily: 'monospace',
                                    fontWeight: 700,
                                    letterSpacing: '.3rem',
                                    color: 'inherit',
                                    textDecoration: 'none',
                                }}
                            >
                                LOGO
                            </Typography>


                            <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
                                <IconButton
                                    size="large"
                                    aria-label="account of current user"
                                    aria-controls="menu-appbar"
                                    aria-haspopup="true"
                                    onClick={handleOpenNavMenu}
                                    color="inherit"
                                >
                                    <MenuIcon/>
                                </IconButton>
                                <Menu
                                    id="menu-appbar"
                                    anchorEl={anchorElNav}
                                    anchorOrigin={{
                                        vertical: 'bottom',
                                        horizontal: 'left',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'left',
                                    }}
                                    open={Boolean(anchorElNav)}
                                    onClose={handleCloseNavMenu}
                                    sx={{
                                        display: {xs: 'block', md: 'none'},
                                    }}
                                >
                                    {pages.map((page) => (
                                        <MenuItem key={page} onClick={handleCloseNavMenu}>
                                            <Typography textAlign="center">{page}</Typography>
                                        </MenuItem>
                                    ))}
                                </Menu>
                            </Box>
                            <AdbIcon sx={{display: {xs: 'flex', md: 'none'}, mr: 1}}/>
                            <Typography
                                variant="h5"
                                noWrap
                                component="a"
                                href="#app-bar-with-responsive-menu"
                                sx={{
                                    mr: 2,
                                    display: {xs: 'flex', md: 'none'},
                                    flexGrow: 1,
                                    fontFamily: 'monospace',
                                    fontWeight: 700,
                                    letterSpacing: '.3rem',
                                    color: 'inherit',
                                    textDecoration: 'none',
                                }}
                            >
                                LOGO
                            </Typography>
                            <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                                {pages.map((page) => (
                                    <Button
                                        key={page}
                                        component={Link}
                                        to={page}
                                        onClick={handleCloseNavMenu}
                                        sx={{my: 2, color: 'white', display: 'block'}}
                                    >
                                        {page}
                                    </Button>
                                ))}
                            </Box>

                            {userIsAuthenticated() ? (<Box sx={{flexGrow: 0}}>

                                <Tooltip title="Open settings">
                                    <IconButton onClick={handleOpenUserMenu} sx={{p: 0}}>
                                        <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg"/>
                                    </IconButton>
                                </Tooltip>

                                <Menu
                                    sx={{mt: '45px'}}
                                    id="menu-appbar"
                                    anchorEl={anchorElUser}
                                    anchorOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    keepMounted
                                    transformOrigin={{
                                        vertical: 'top',
                                        horizontal: 'right',
                                    }}
                                    open={Boolean(anchorElUser)}
                                    onClose={handleCloseUserMenu}
                                >
                                    {settings.map((setting) =>
                                        (<MenuItem key={setting} onClick={handleCloseUserMenu}>
                                            <Link to={setting.toLowerCase()} className={"link"}>{setting}</Link>
                                        </MenuItem>))
                                    }
                                </Menu>
                            </Box>) : (
                                <div className="login-register-box">
                                    {settings.map((setting) =>
                                        (<div className="login-register-item">
                                            <Link to={setting.toLowerCase()} className={"link"}>{setting}</Link>
                                        </div>))
                                    }
                                </div>)}
                        </Toolbar>
                    </Container>
                </AppBar>
            </ThemeProvider>
        </div>
    );
};

export default HeaderComponent;
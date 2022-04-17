import React, { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { styled } from '@mui/system'
import { Span } from 'app/components/Typography'
import { MatxMenu } from 'app/components'
import { ShoppingCart } from '../base'
import { themeShadows } from 'app/components/MatxTheme/themeColors'
import {
    Icon,
    MenuItem,
    Menu,
    Button,
    Hidden,
    Box
} from '@mui/material'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import { CategoryService, localStorageService } from 'app/services'
import { topBarHeight } from 'app/utils/constant'

const TopbarRoot = styled('div')(() => ({
    position: 'sticky',
    top: 0,
    zIndex: 100,
    background: '#fff',
    transition: 'all 0.3s ease',
    boxShadow: themeShadows[8],
    height: topBarHeight,
}))
const TopbarContainer = styled(Box)(({ theme }) => ({
    padding: '8px',
    paddingLeft: 18,
    paddingRight: 20,
    height: '100%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    maxWidth: '1300px',
    margin: 'auto',
    [theme.breakpoints.down('sm')]: {
        paddingLeft: 16,
        paddingRight: 16,
    },
    [theme.breakpoints.down('xs')]: {
        paddingLeft: 14,
        paddingRight: 16,
    },
}))
const UserMenu = styled(Box)(() => ({
    display: 'flex',
    alignItems: 'center',
    cursor: 'pointer',
    borderRadius: 24,
    minHeight: 40,
    padding: 4,
    '& span': {
        margin: '0 8px',
    },
}))
const StyledItem = styled(MenuItem)(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    minWidth: 185,
    '& a': {
        width: '100%',
        display: 'flex',
        alignItems: 'center',
        textDecoration: 'none',
    },
    '& span': {
        marginRight: '10px',
        color: theme.palette.text.primary,
    },
}))
const ButtonCustom = styled(Button)(({ theme }) => ({
    marginLeft: '10px',
    color: theme.palette.text.primary,
    fontSize: '16px',
    textTransform: 'uppercase',
    fontWeight: '500',
}))
const MenuButton = styled(Box)(({ theme }) => ({
    display: 'inline-block',
    color: theme.palette.text.primary,
    '& div:hover': {
        backgroundColor: theme.palette.action.hover,
    },
}))
const Header = () => {
    const [categoriesParent, setCategoriesParent] = useState([])
    const [auth, setAuth] = useState({})
    const navigate = useNavigate()

    useEffect(() => {
        CategoryService.getCategoryParent().then((response) => {
            setCategoriesParent(response.data.data)
        }).catch(error => {
            console.log(error)
        })
        setAuth(localStorageService.getItem('user'))
    }, [])

    const [anchorEl, setAnchorEl] = useState(null);
    const handleCategory = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const ShowButton = () => {
        if (auth) {
            return (
                <MatxMenu
                    menuButton={
                        <UserMenu>
                            <AccountCircleIcon />
                            <Hidden xsDown>
                                <Span>
                                    Xin chào <strong>{auth.username}</strong>
                                </Span>
                            </Hidden>
                        </UserMenu>
                    }
                >
                    <StyledItem>
                        <Link to={"/profile/"+auth.username}>
                            <Icon> person </Icon>
                            <Span> Tài khoản của tôi </Span>
                        </Link>
                    </StyledItem>
                    <StyledItem>
                        <Link to="/admin">
                            <Icon> person </Icon>
                            <Span> Trang Quản lý </Span>
                        </Link>
                    </StyledItem>
                    <StyledItem >
                        <Link to="/login">
                            <Icon> power_settings_new </Icon>
                            <Span> Đăng xuất </Span>
                        </Link>
                    </StyledItem>
                </MatxMenu>
            )
        } else {
            return (
                <MenuButton onClick={() => navigate("/login")}>
                    <UserMenu>
                        <AccountCircleIcon />
                        <Hidden xsDown>
                            <Span>
                                <strong>Đăng nhập</strong>
                            </Span>
                        </Hidden>
                    </UserMenu>
                </MenuButton>
            )
        }
    }

    return (
        <TopbarRoot>
            <TopbarContainer>
                <Box display="flex" width='30%' justifyContent='flex-start' alignItems="center">
                    <img
                        src='/assets/images/logos/canifa.jpg'
                        alt='LOGO'
                        height='40px'
                    />
                    <ButtonCustom onClick={() => navigate("/")}>
                        Trang chủ
                    </ButtonCustom>
                    <ButtonCustom
                        id="show-category"
                        onClick={handleCategory}
                        endIcon={<KeyboardArrowDownIcon />}
                    >
                        Sản phẩm
                    </ButtonCustom>
                    <Menu
                        id="menu-categories"
                        anchorEl={anchorEl}
                        open={Boolean(anchorEl)}
                        onClose={handleClose}
                        MenuListProps={{
                            'aria-labelledby': 'show-category',
                        }}
                        sx={{ '& ul': { minWidth: '150px' } }}
                    >
                        {categoriesParent.map((category) => (
                            <MenuItem key={category.seo} onClick={() => navigate("/category/" + category.seo)}>{category.name}</MenuItem>
                        ))}
                    </Menu>
                </Box>
                <Box display="flex" width='30%' justifyContent='flex-end' alignItems="center">
                    <ShoppingCart />
                    <ShowButton />
                </Box>
            </TopbarContainer>
        </TopbarRoot>
    )
}
export default React.memo(Header)

import React,{useState, useEffect} from 'react'
import { Grid, Card, Icon, IconButton, Tooltip } from '@mui/material'
import { Box, styled } from '@mui/system'
import { Small } from 'app/components/Typography'
import { HomeService } from 'app/services'

const StyledCard = styled(Card)(({ theme }) => ({
    display: 'flex',
    flexWrap: 'wrap',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: '24px !important',
    background: theme.palette.background.paper,
    [theme.breakpoints.down('sm')]: {
        padding: '16px !important',
    },
}))

const ContentBox = styled('div')(({ theme }) => ({
    display: 'flex',
    flexWrap: 'wrap',
    alignItems: 'center',
    '& small': {
        color: theme.palette.text.secondary,
    },
    '& .icon': {
        opacity: 0.6,
        fontSize: '44px',
        color: theme.palette.primary.main,
    },
}))

const Heading = styled('h6')(({ theme }) => ({
    margin: 0,
    marginTop: '4px',
    fontWeight: '500',
    fontSize: '14px',
    color: theme.palette.primary.main,
}))

const StatCards = () => {
    const [userNumber, setUserNumber] = useState(0);
    const [orderNumber, setOrderNumber] = useState(0);
    const [productNumber, setProductNumber] = useState(0);
    const sumUser = () => {
        HomeService.getUserSum('users').then((response) => {
            setUserNumber(response.data);
        })
        HomeService.getUserSum('orders').then((response) => {
            setOrderNumber(response.data);
        })
        HomeService.getUserSum('products').then((response) => {
            setProductNumber(response.data);
        })
    }

    useEffect(() => {
        sumUser();

    }, [])
    return (
        <Grid container spacing={3} sx={{ mb: '24px' }}>
            <Grid item xs={12} md={6}>
                <StyledCard elevation={6}>
                    <ContentBox>
                        <Icon className="icon">group</Icon>
                        <Box ml="12px">
                            <Small>Người dùng</Small>
                            <Heading>{userNumber}</Heading>
                            
                        </Box>
                    </ContentBox>
                    
                </StyledCard>
            </Grid>
            <Grid item xs={12} md={6}>
                <StyledCard elevation={6}>
                    <ContentBox>
                        <Icon className="icon">attach_money</Icon>
                        <Box ml="12px">
                            <Small sx={{ lineHeight: 1 }}>
                                This week Sales
                            </Small>
                            <Heading>{orderNumber}</Heading>
                        </Box>
                    </ContentBox>
                    
                </StyledCard>
            </Grid>
            <Grid item xs={12} md={6}>
                <StyledCard elevation={6}>
                    <ContentBox>
                        <Icon className="icon">store</Icon>
                        <Box ml="12px">
                            <Small>Sản phẩm</Small>
                            <Heading>{productNumber}</Heading>
                        </Box>
                    </ContentBox>
                    
                </StyledCard>
            </Grid>
            <Grid item xs={12} md={6}>
                <StyledCard elevation={6}>
                    <ContentBox>
                        <Icon className="icon">shopping_cart</Icon>
                        <Box ml="12px">
                            <Small>Đơn đặt hàng</Small>
                            <Heading>{orderNumber}</Heading>
                        </Box>
                    </ContentBox>
                    
                </StyledCard>
            </Grid>
        </Grid>
    )
}

export default StatCards

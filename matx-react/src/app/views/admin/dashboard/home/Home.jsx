import React, { Fragment } from 'react'
import StatCards from '../shared/StatCards'
import { Grid } from '@mui/material'
import { styled } from '@mui/system'

const ContentBox = styled('div')(({ theme }) => ({
    margin: '30px',
    [theme.breakpoints.down('sm')]: {
        margin: '16px',
    },
}))

const Home = () => {
    return (
        <Fragment>
            <ContentBox className="home">
                <Grid container spacing={3}>
                    <Grid item lg={12} md={12} sm={12} xs={12}>
                        <StatCards />
                    </Grid>
                </Grid>
            </ContentBox>
        </Fragment>
    )
}

export default Home

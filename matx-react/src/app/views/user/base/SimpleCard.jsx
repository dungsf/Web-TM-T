import React from 'react'
import { Card } from '@mui/material'
import { styled, Box } from '@mui/system'

const CardRoot = styled(Card)(({height}) => ({
    height: height,
    padding: '20px 24px',
}))

const CardTitle = styled('div')(({ subtitle }) => ({
    fontSize: '1.5rem',
    fontWeight: '500',
    textTransform: 'capitalize',
    marginBottom: !subtitle && "16px",
}))

const SimpleCard = ({ children, title, subtitle, height }) => {

    if(!height){
        height = '100%'
    }

    return (
        <CardRoot height={height} elevation={6}>
            <CardTitle subtitle={subtitle}>
                {title}
            </CardTitle>
            {subtitle && <Box sx={{ mb: 2 }}>{subtitle}</Box>}
            {children}
        </CardRoot>
    )
}

export default SimpleCard

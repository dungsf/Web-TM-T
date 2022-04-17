import React from 'react'
import { styled, useTheme } from '@mui/system'
import { Icon, Breadcrumbs } from '@mui/material'
import { NavLink } from 'react-router-dom'

const BreadcrumbRoot = styled('div')(() => ({
    display: 'flex',
    flexWrap: 'wrap',
    alignItems: 'center',
}))

const SubName = styled('span')(({ theme }) => ({
    textTransform: 'capitalize',
    color: theme.palette.text.secondary,
}))

const StyledIcon = styled(Icon)(() => ({
    marginLeft: 8,
    marginBottom: '4px',
    verticalAlign: 'middle',
}))

const Breadcrumb = ({ routeSegments }) => {
    const theme = useTheme()
    const hint = theme.palette.text.hint

    return (
        <BreadcrumbRoot>
            <Breadcrumbs
                separator={<Icon sx={{ color: hint }}>navigate_next</Icon>}
                sx={{
                    display: 'flex',
                    alignItems: 'center',
                    position: 'relative'
                }}
            >
                <NavLink to="/">
                    <StyledIcon color="primary">
                        home
                    </StyledIcon>
                </NavLink>
                {routeSegments
                    ? routeSegments.map((route, index) => {
                        return index !== routeSegments.length - 1 ? (
                            <SubName>
                                 {route.name}
                            </SubName>
                        ) : (
                            <SubName key={index}>
                                {route.name}
                            </SubName>
                        )
                    })
                    : null}
            </Breadcrumbs>
        </BreadcrumbRoot>
    )
}

export default Breadcrumb

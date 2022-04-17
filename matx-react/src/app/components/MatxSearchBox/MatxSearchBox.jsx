import React from 'react'
import { styled } from '@mui/system'
import { Icon, IconButton } from '@mui/material'

const SearchContainer = styled('div')(({ theme }) => ({
    position: 'relative',
    top: 0,
    left: 0,
    zIndex: 9,
    width: '50%',
    display: 'flex',
    alignItems: 'center',
    boxShadow: '0px 0px 4px 1px #ccc',
    borderRadius: '25px',
    overflow: 'auto',
    height: 50,
    color: 'black',
    '&::placeholder': {
        color: 'color',
    },
}))

const SearchInput = styled('input')(({ theme }) => ({
    width: '100%',
    border: 'none',
    outline: 'none',
    fontSize: '1rem',
    paddingLeft: '20px',
    height: 'calc(100% - 5px)',
    color: 'black',
    '&::placeholder': {
        color: '#333',
    },
}))

const MatxSearchBox = () => {

    return (
        <React.Fragment>
            <SearchContainer>
                <SearchInput
                    type="text"
                    placeholder="Search here..."
                    autoFocus
                />
                <IconButton sx={{ mx: 2, verticalAlign: 'middle', }}>
                    <Icon sx={{ color: 'black' }}>search</Icon>
                </IconButton>
            </SearchContainer>
        </React.Fragment>
    )
}

export default MatxSearchBox

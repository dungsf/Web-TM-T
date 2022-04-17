import { styled } from '@mui/system'

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
    background: 'white',
    '&::placeholder': {
        color: 'color',
    },
}))

export default SearchContainer

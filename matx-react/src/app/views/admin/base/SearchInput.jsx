import { styled } from '@mui/system'

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

export default SearchInput

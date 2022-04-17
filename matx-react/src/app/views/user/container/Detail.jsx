import React, { useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { CardProduct, Container } from '../base'
import {
    Grid,
    Paper,
    styled,
    Box,
    InputBase,
    Pagination,
    Stack
} from '@mui/material'
import SearchIcon from '@mui/icons-material/Search';
import { ProductService, CategoryService } from 'app/services'

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    minHeight: '200px',
    color: '#000',
}));

const Search = styled('div')(({ theme }) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    border: '1px solid #ccc',
    backgroundColor: 'white',
    marginLeft: 0,
    width: '100%'
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            width: '12ch',
            '&:focus': {
                width: '20ch',
            },
        },
    },
}));

const AppUser = () => {
    const navigate = useNavigate()
    const [content, setContent] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState()
    const [totalPages, setTotalPages] = useState()
    const [keyword, setKeyword] = useState('')
    const { categorySeo } = useParams();

    const handleChangePage = (event, value) => {
        setPage(value - 1)
        getDataBySearch(value - 1, keyword)
    };

    const handleChangeSearch = (event) => {
        const keyword = event.target.value
        setKeyword(keyword)
        getDataBySearch(page,keyword)
    }
    const getDataBySearch = () => {
        ProductService.getProductByKeyword().then((response) => {
            const data = response.data
            setContent(data.content)
            setPage(data.page)
            setSize(data.size)
            setTotalPages(data.totalPages)
        }).catch(error => {
            console.log(error)
        })
    }

    const getDataByCategorySeo = (seo) => {
        CategoryService.getProductsByCategory(seo).then((response) => {
            const data = response.data
            setContent(data.content)
            setPage(data.page)
            setSize(data.size)
            setTotalPages(data.totalPages)
        }).catch(error => {
            console.log(error)
        })
    }

    useEffect(() => {
        getDataByCategorySeo(categorySeo)
    }, [])
    return (
        <Container>
            <Grid container maxWidth='1300px' margin='auto' spacing={2} paddingBottom={7.5} paddingTop={2}>
                <Grid item xs={3}>
                    <Box sx={{ flexGrow: 1 }}>
                        <Grid container spacing={1.2} columns={{ xs: 12, sm: 0, md: 0 }}>
                            <Grid item xs={12} sm={0} md={0}>
                                <Search>
                                    <SearchIconWrapper>
                                        <SearchIcon />
                                    </SearchIconWrapper>
                                    <StyledInputBase
                                        placeholder="Search…"
                                        inputProps={{ 'aria-label': 'search' }}
                                    />
                                </Search>
                            </Grid>
                            <Grid item xs={12} sm={0} md={0}>
                                <Item>xs=2</Item>
                            </Grid>
                        </Grid>
                    </Box>
                </Grid>
                <Grid item xs={9}>
                    <Box sx={{ flexGrow: 1 }}>
                        <Grid container spacing={1.5} columns={{ xs: 4, sm: 8, md: 12 }}>
                        {content.map((product) => (
                            <Grid key={product.seo} item xs={2} sm={4} md={4}>    
                                <CardProduct data={product} />
                            </Grid>
                          ))}
                        </Grid>
                        <Stack spacing={2} paddingTop={3} paddingBottom={1}>
                            <Box my={2} display="flex" justifyContent="center">
                                <Pagination
                                    count={10}
                                    page={1}
                                    // onChange={handleChangePage} 
                                    variant="outlined"
                                    color="primary"
                                    showFirstButton
                                    showLastButton
                                />
                            </Box>
                        </Stack>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AppUser
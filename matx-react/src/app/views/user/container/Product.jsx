import React, { useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { CardProduct, Container, TextField } from '../base'
import {
    Grid,
    styled,
    Box,
    Card,
    InputBase,
    Pagination,
    Stack,
    Autocomplete
} from '@mui/material'
import { ValidatorForm } from 'react-material-ui-form-validator'
import SearchIcon from '@mui/icons-material/Search';
import { ProductService, CategoryService } from 'app/services'


const Item = styled(Card)(({ theme }) => ({
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
        width: '120%'
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
        getDataBySearch(page, keyword)
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

    const [value, setValue] = useState([100000, 10000000]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };
    return (
        <Container>
            <Grid container maxWidth='1300px' margin='auto' spacing={2} paddingBottom={7.5} paddingTop={2}>
                <Grid item xs={3}>
                    <Box sx={{ flexGrow: 1 }}>
                        <Grid container spacing={1.2} columns={{ xs: 12, sm: 0, md: 0 }}>
                            <Grid item xs={12} sm={0} md={0}>
                                <Item>
                                    <ValidatorForm onSubmit={() => null} onError={() => null}>
                                        <Grid container spacing={6}>
                                            <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                                                <Search sx={{marginBottom: "10px"}}>
                                                    <SearchIconWrapper>
                                                        <SearchIcon />
                                                    </SearchIconWrapper>
                                                    <StyledInputBase
                                                        placeholder="Từ khóa…"
                                                        inputProps={{ 'aria-label': 'search' }}
                                                    />
                                                </Search>
                                                <Autocomplete
                                                    disablePortal
                                                    size='small'
                                                    // options={categories}
                                                    // getOptionLabel={(categories) => categories.name}
                                                    // onChange={(event, categories) => { setParent(categories) }}
                                                    // value={parent}
                                                    sx={{ width: '100%' }}
                                                    renderInput={(params) => <TextField {...params} label="Chọn danh mục con" />}
                                                />
                                                <TextField
                                                    size='small'
                                                    type="number"
                                                    name="name"
                                                    // onChange={(e) => setName(e.target.value)}
                                                    // value={name}
                                                    label="Giá bắt đầu"
                                                />
                                                <TextField
                                                    size='small'
                                                    type="number"
                                                    name="name"
                                                    // onChange={(e) => setName(e.target.value)}
                                                    // value={name}
                                                    label="Giá kết thúc"
                                                />
                                            </Grid>
                                        </Grid>
                                    </ValidatorForm>
                                </Item>
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
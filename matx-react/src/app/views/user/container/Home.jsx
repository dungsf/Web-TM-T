import React, { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { CardProduct, Container } from '../base'
import {
    Grid,
    Paper,
    styled,
    Box,
    InputBase,
} from '@mui/material'
import { ProductService } from 'app/services'

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    minHeight: '200px',
    color: '#000',
}));


const AppUser = () => {
    const navigate = useNavigate()
    const [listProducts, setListProducts] = useState([])
    
    const getData = () => {
        ProductService.getAllProductsHot().then((response) => {
            setListProducts(response.data.data)
        }).catch(error => {
            console.log(error)
        })
    }
    useEffect(() => {
        getData()
    }, [])
    return (
        <Container>
            <Grid container maxWidth='1300px' margin='auto' spacing={2} paddingBottom={7.5} paddingTop={2}>
                <Grid item xs={12}>
                    <Item sx={{ flexGrow: 1 }} height="300px">
                        Carousel
                    </Item>
                </Grid>
                <Grid item xs={12}>
                    <Box sx={{ flexGrow: 1 }}>
                        <Grid container spacing={1.5} columns={{ xs: 3, sm: 8, md: 12 }}>
                        {listProducts.map((product) => (
                            <Grid key={product.seo} item xs={2} sm={3} md={3}>    
                                <CardProduct data={product}/>
                            </Grid>
                          ))}
                        </Grid>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    )
}

export default AppUser
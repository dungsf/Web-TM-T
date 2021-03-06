import React, { useState, useEffect } from 'react'
import {
    Grid,
    styled,
    Card,
    Button,
    Autocomplete,
    IconButton,
    Fab,
    Icon,
    Box,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Link,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle
} from '@mui/material'
import { TextField, SimpleCard, StyledTableCell, StyledTableRow, Container } from '../base'
import { ValidatorForm } from 'react-material-ui-form-validator'
import { localStorageService, URL_IMG } from 'app/services'
import { hasProductInList, createListCart } from 'app/views/action'
import dataVietNam from 'app/db/db.vietnam.json'

// const Item = styled(Card)(({ theme }) => ({
//     backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
//     ...theme.typography.body2,
//     padding: theme.spacing(1),
//     minHeight: '200px',
//     color: '#000',
// }));
const StyledIconButton = styled(IconButton)(({ theme }) => ({
    '& span': {
        color: theme.palette.text.primary,
    },
    '& #disable': {
        color: theme.palette.text.disabled,
    },
}))
const ProductBox = styled('div')(() => ({
    display: 'flex',
    alignItems: 'center',
    transition: 'background 300ms ease',
    '&:hover': {
        background: 'rgba(0,0,0,0.01)',
    },
}))
const IMG = styled('img')(() => ({
    height: 75,
}))
const Color = styled('div')(({ color }) => ({
    width: 14,
    height: 14,
    background: color,
    borderRadius: "50%",
    marginLeft: 5,
    display: "inline-block",
}))
const Detail = styled('div')(() => ({
    marginBottom: 4,
    display: "flex",
    alignItems: "center",
}))
const ProductDetails = styled('div')(() => ({
    marginRight: '8',
    textAlign: 'center',
    flexGrow: 1,
    display: 'flex',
    flexDirection: 'column',
    '& h6': {
        whiteSpace: 'nowrap',
        overflow: 'hidden',
        textOverflow: 'ellipsis',
        display: 'block',
        width: 120,
        marginBottom: '4px',
    },
}))
const AppUser = () => {
    const [listCart, setListCart] = useState([])
    // const [listPromotion, setListPromotion] = useState([])
    const [totalCost, setTotalCost] = useState(0)
    const [totalOrder, setTotalOrder] = useState(0)
    const [discount, setDiscount] = useState(0)

    const listCity = dataVietNam.city
    const [listDistrict, setListDistrict] = useState(dataVietNam.district[0])
    const [listWard, setListWard] = useState([])
    const [city, setCity] = useState()
    const [district, setDistrict] = useState()
    const [ward, setWard] = useState()
    const [detailAddress, setDetailAddress] = useState()
    const [stateAddress, setStateAddress] = useState("Vui l??ng nh???p ??ia ch???")
    const [openAddress, setOpenAddress] = useState(false);

    const [stateOrder, setStateOrder] = useState({})
    
    const handleChangeOrder = ({ target: { name, value } }) => {
        setStateOrder({
            ...stateOrder,
            [name]: value,
        })
    }

    const handleOpenAddress = () => {
        setOpenAddress(true);
    };

    const handleCreateAddress = () => {
        const stateAddress = detailAddress + ", " + ward.name + ", " + district.name + ", " + city.name
        setStateAddress(stateAddress)
        setOpenAddress(false);
    };

    useEffect(() => {
        getData()
    }, [])

    const getData = () => {
        const list = localStorageService.getItem('listCart')
        if (list) {
            setListCart(list)
            let total = 0
            list.forEach((p) => {
                total += p.price * p.quantity
            })
            setTotalCost(total)
            setTotalOrder(list.length)
        }
    }

    const updateCartQuantity = (productId, colorId, sizeId, quantity) => {
        listCart.forEach(i => {
            if (hasProductInList(productId, colorId, sizeId, i)) {
                i.quantity = quantity
            }
        })
        createListCart(listCart)
        getData()
    }

    const deleteProduct = (productId, colorId, sizeId) => {
        listCart.shift(p => p.productId === productId && p.colorId === colorId && p.sizeId === sizeId)
        createListCart(listCart)
        getData()
    }

    const setAddress = (city, district, ward) => {
        if (city) {
            setListDistrict(dataVietNam.district.filter(d => d.idCity === city.idCity))
            setCity(city)
        }
        if (district) {
            setListWard(dataVietNam.ward.filter(w => w.idDistrict === district.idDistrict))
            setDistrict(district)
        }
        if (ward) { setWard(ward) }
    }
    const createOrder = () =>{
        //L??u tr??n database
        console.log(stateOrder)
    }

    return (
        <Container>
            <Grid container maxWidth='1300px' margin='auto' paddingBottom={7.5} paddingTop={2}>
                <ValidatorForm onSubmit={createOrder} onError={() => null}>
                    <Grid container spacing={2}>
                        <Grid item xs={8}>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <SimpleCard title={"(" + totalOrder + ") S???n ph???m"}>
                                        <Box width="100%" overflow="auto">
                                            <TableContainer component={Paper}>
                                                <Table sx={{ minWidth: 700 }} aria-label="customized table">
                                                    <TableHead>
                                                        <TableRow>
                                                            <StyledTableCell width="20px"></StyledTableCell>
                                                            <StyledTableCell align="left">S???N PH???M</StyledTableCell>
                                                            <StyledTableCell align="right" width="100px">GI?? B??N</StyledTableCell>
                                                            <StyledTableCell align="center" width="120px">S??? L?????NG</StyledTableCell>
                                                            <StyledTableCell align="right" width="120px">T???NG TI???N</StyledTableCell>
                                                            <StyledTableCell width="70px"></StyledTableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                        {listCart.map((p) => (
                                                            <StyledTableRow key={p.id}>
                                                                <StyledTableCell width="20px"></StyledTableCell>
                                                                <StyledTableCell align="left">
                                                                    <ProductBox>
                                                                        <Box mr={1}>
                                                                            <IMG src={URL_IMG + p.avatar} />
                                                                        </Box>
                                                                        <ProductDetails>
                                                                            <Link textAlign="left" underline="hover" color="black" href="/admin">
                                                                                {p.productName}
                                                                            </Link>
                                                                            <Detail sx={{ display: "flex", alignItems: "center" }}>
                                                                                {p.sizeName + " / "}
                                                                                <Color color={p.colorCode} />
                                                                            </Detail>
                                                                        </ProductDetails>
                                                                    </ProductBox>
                                                                </StyledTableCell>
                                                                <StyledTableCell align="right">
                                                                    {p.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}
                                                                </StyledTableCell>
                                                                <StyledTableCell align="center">
                                                                    <Box display="flex" alignItems='center' justifyContent='center'>
                                                                        <StyledIconButton
                                                                            disabled={!(p.quantity - 1)}
                                                                            size="small"
                                                                            onClick={() => updateCartQuantity(p.productId, p.colorId, p.sizeId, p.quantity - 1)}
                                                                        >
                                                                            <Icon id={!(p.quantity - 1) && 'disable'}> keyboard_arrow_down </Icon>
                                                                        </StyledIconButton>
                                                                        <Box paddingLeft={1} paddingRight={1}> {p.quantity} </Box>
                                                                        <StyledIconButton
                                                                            size="small"
                                                                            onClick={() => updateCartQuantity(p.productId, p.colorId, p.sizeId, p.quantity + 1)}
                                                                        >
                                                                            <Icon sx={{ cursor: 'pinter' }}> keyboard_arrow_up </Icon>
                                                                        </StyledIconButton>
                                                                    </Box>
                                                                </StyledTableCell>
                                                                <StyledTableCell align="right">
                                                                    {(p.quantity * p.price).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}
                                                                </StyledTableCell>
                                                                <StyledTableCell align="center">
                                                                    <Fab
                                                                        size="small"
                                                                        aria-label="Delete"
                                                                        className="button"
                                                                        onClick={() => deleteProduct(p.productId, p.colorId, p.sizeId)}
                                                                    >
                                                                        <Icon>delete</Icon>
                                                                    </Fab>
                                                                </StyledTableCell>
                                                            </StyledTableRow>
                                                        ))}
                                                    </TableBody>
                                                </Table>
                                            </TableContainer>
                                        </Box>
                                    </SimpleCard>
                                </Grid>
                                <Grid item xs={12}>
                                    <SimpleCard title="Th??ng tin giao h??ng">
                                        <Grid container xs={12} spacing={1.5} >
                                            <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                                <TextField
                                                    type="text"
                                                    onChange={handleChangeOrder}
                                                    name="customerName"
                                                    value={stateOrder.customerName}
                                                    label="H??? t??n"
                                                />
                                            </Grid>
                                            <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                                <TextField
                                                    type="text"
                                                    onChange={handleChangeOrder}
                                                    name="customerPhone"
                                                    value={stateOrder.customerPhone}
                                                    label="S??? ??i???n tho???i"
                                                />
                                            </Grid>
                                            <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                                <TextField
                                                    type="text"
                                                    onChange={handleChangeOrder}
                                                    name="customerEmail"
                                                    value={stateOrder.customerEmail}
                                                    label="?????a ch??? email"
                                                />
                                            </Grid>
                                            <Grid item xs={12} sx={{ mt: 2, margin: 0 }} display="flex" alignItems="center">
                                                <Button variant="contained" color='primary' sx={{ width: "120px", marginBottom: "16px" }} onClick={handleOpenAddress}>
                                                    Nh???p ?????a ch???
                                                </Button>
                                                <TextField
                                                    sx={{ width: "655px", marginLeft: "16px" }}
                                                    type="text"
                                                    name="stateAddress"
                                                    value={stateAddress}
                                                    label="?????a ch???"
                                                    disabled
                                                />
                                            </Grid>
                                            <Dialog
                                                open={openAddress}
                                                onClose={handleCreateAddress}
                                                sx={{
                                                    "& .MuiDialog-container": {
                                                        "& .MuiPaper-root": {
                                                            width: "100%",
                                                            maxWidth: "500px",
                                                        },
                                                    },
                                                }}
                                            >
                                                <DialogTitle>Ch???n ?????a ch??? nh???n h??ng</DialogTitle>
                                                <DialogContent>
                                                    <Autocomplete
                                                        disablePortal
                                                        options={listCity}
                                                        getOptionLabel={(listCity) => listCity.name}
                                                        onChange={(event, city) => { setAddress(city, null, null) }}
                                                        value={city}
                                                        sx={{ width: '100%', marginTop: "16px" }}
                                                        renderInput={(params) => <TextField {...params} label="T???nh/Th??nh ph???" />}
                                                    />
                                                    <Autocomplete
                                                        disablePortal
                                                        options={listDistrict}
                                                        getOptionLabel={(listDistrict) => listDistrict.name}
                                                        onChange={(event, district) => { setAddress(null, district, null) }}
                                                        value={district}
                                                        sx={{ width: '100%' }}
                                                        renderInput={(params) => <TextField {...params} label="Qu???n/Huy???n" />}
                                                    />
                                                    <Autocomplete
                                                        disablePortal
                                                        options={listWard}
                                                        getOptionLabel={(listWard) => listWard.name}
                                                        onChange={(event, ward) => { setAddress(null, null, ward) }}
                                                        value={ward}
                                                        sx={{ width: '100%' }}
                                                        renderInput={(params) => <TextField {...params} label="Ph?????ng/X??" />}
                                                    />
                                                    <TextField
                                                        type="text"
                                                        value={detailAddress}
                                                        onChange={(e) => { setDetailAddress(e.target.value) }}
                                                        name="detailAddress"
                                                        label="?????a ch???"
                                                    />
                                                </DialogContent>
                                                <DialogActions>
                                                    <Button variant="contained" color='success' onClick={handleCreateAddress}>Ho??n t???t</Button>
                                                </DialogActions>
                                            </Dialog>
                                            <Grid item xs={12} sx={{ mt: 2, margin: 0 }}>
                                                <TextField
                                                    type="text"
                                                    onChange={handleChangeOrder}
                                                    name="note"
                                                    value={stateOrder.note}
                                                    label="Ghi ch??"
                                                />
                                            </Grid>
                                        </Grid>
                                    </SimpleCard>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid item xs={4}>
                            <SimpleCard height='auto' title="????n h??ng">
                                <Box width="100%" overflow="auto">
                                    <TableContainer component={Paper}>
                                        <Table>
                                            <TableBody sx={{ '& td': { border: 0 } }}>
                                                <TableRow>
                                                    <TableCell align='left'>T???ng ti???n h??ng:</TableCell>
                                                    <TableCell align="right">
                                                        {totalCost.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}
                                                    </TableCell>
                                                </TableRow>
                                                <TableRow>
                                                    <TableCell align='left'>Gi???m gi??:</TableCell>
                                                    <TableCell align="right">
                                                        {discount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}
                                                    </TableCell>
                                                </TableRow>
                                                <TableRow>
                                                    <TableCell align='left'>T???ng thanh to??n:</TableCell>
                                                    <TableCell align="right">
                                                        {(totalCost - discount).toLocaleString('vi-VN', { style: 'currency', currency: 'VND' })}
                                                    </TableCell>
                                                </TableRow>
                                                <TableRow>
                                                    <TableCell colSpan={2} align="right">
                                                        <Autocomplete
                                                            disablePortal
                                                            size="small"
                                                            // options={categories}
                                                            // getOptionLabel={(categories)=>categories.name}
                                                            // onChange={(event, categories)=>{setParentId(categories.id)}}
                                                            sx={{ width: '100%', '& .MuiFormControl-root': { marginBottom: 0 } }}
                                                            renderInput={(params) => <TextField {...params} label="Ch???n m?? gi???m gi??" />}
                                                        />
                                                    </TableCell>
                                                </TableRow>
                                                <TableRow>
                                                    <TableCell colSpan={2} align="right">
                                                        <Button
                                                            fullWidth
                                                            color="success"
                                                            variant="contained"
                                                            size="large"
                                                            type="submit"
                                                        >
                                                            ?????T H??NG
                                                        </Button>
                                                    </TableCell>
                                                </TableRow>
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                </Box>
                            </SimpleCard>
                        </Grid>
                    </Grid>
                </ValidatorForm>
            </Grid>
        </Container>
    )
}

export default AppUser
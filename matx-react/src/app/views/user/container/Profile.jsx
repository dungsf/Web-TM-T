import React, { useState, useEffect } from 'react'
import PropTypes from 'prop-types'
import { TextValidator, ValidatorForm } from 'react-material-ui-form-validator'
import { useNavigate, useParams } from 'react-router-dom'
import { UserService } from 'app/services'
import dataVietNam from 'app/db/db.vietnam.json'
import { TextField, SimpleCard, StyledTableCell, StyledTableRow, Container } from '../base'
import {
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableContainer,
    Paper,
    Tab,
    Tabs,
    Typography,
    Autocomplete,
    Button,
    Select,
    InputLabel,
    Box,
    Grid,
    Radio,
    FormControlLabel,
    RadioGroup,
    FormControl,
    FormLabel,
    MenuItem
} from '@mui/material'
import { OrderDetail } from 'app/views/user/base'

function TabPanel(props) {
    const { children, value, index, ...other } = props

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
            style={{ width: '100%' }}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    )
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
}

function a11yProps(index) {
    return {
        id: `vertical-tab-${index}`,
        'aria-controls': `vertical-tabpanel-${index}`,
    }
}

export default function VerticalTabs() {
    const [value, setValue] = React.useState(0)

    const handleChange = (event, newValue) => {
        setValue(newValue)
        switch (newValue) {
            case 3:
                getOrderByUserName(username, stateStatus);
                break;
        }
    }

    const navigate = useNavigate()
    // const [listCart, setListCart] = useState([])
    // const [totalCost, setTotalCost] = useState(0)
    // const [totalOrder, setTotalOrder] = useState(0)

    const [listCity, setListCity] = useState(dataVietNam.city)
    const [listDistrict, setListDistrict] = useState(dataVietNam.district[0])
    const [listWard, setListWard] = useState([])
    const [city, setCity] = useState()
    const [district, setDistrict] = useState()
    const [ward, setWard] = useState()

    const { username } = useParams()
    const [statePassword, setStatePassword] = useState({})
    const [stateInfor, setStateInfor] = useState({})
    const [stateOrder, setStateOrder] = useState([])
    const [stateStatus, setStateStatus] = useState(4)
    /////////////////////////////////////////// th??ng tin

    const updateUser = (e) => {
        UserService.updateUser(username, stateInfor)
            .then((response) => {
                navigate('/admin/user-profile')
            })
            .catch((error) => {
                console.log(error)
            })
    }

    const inforHandleChange = ({ target: { name, value } }) => {
        setStateInfor({
            ...stateInfor,
            [name]: value,
        })
    }

    useEffect(() => {
        UserService.getUserByUsername(username)
            .then((response) => {
                const user = response.data.data
                setStateInfor({
                    firstName: user.firstName,
                    lastName: user.lastName,
                    gender: user.gender,
                    email: user.email,
                    phone: user.phone,
                })
            })
            .catch((error) => {
                console.log(error)
            })
    }, [])

    ///////////////////////////////////////////  ?????a ch???

    const setAddress = (city, district, ward) => {
        if (city) {
            setListDistrict(dataVietNam.district.filter(d => d.idCity === city.idCity))
            setCity(city)
        }

        if (district) {
            setListWard(dataVietNam.ward.filter(w => w.idDistrict === district.idDistrict))
            setDistrict(district)
        }

        if (ward) {
            setWard(ward)
        }
    }


    ///////////////////////////////////////////  ?????i m???t kh???u

    useEffect(() => {
        ValidatorForm.addValidationRule('isPasswordMatch', (value) => {
            console.log(value)

            if (value !== statePassword.newPassword) {
                return false
            }
            return true
        })
        return () => ValidatorForm.removeValidationRule('isPasswordMatch')
    }, [statePassword.newPassword])

    const formHandleChange = ({ target: { name, value } }) => {
        setStatePassword({
            ...statePassword,
            [name]: value,
        })
    }

    const updatePassword = (e) => {
        UserService.updatePassword(username, statePassword)
            .then((response) => {
                navigate('/admin/user-profile')
            })
            .catch((error) => {
                console.log(error)
            })
    }

    ///////////////////////////////////////////////////// ????n h??ng c???a t??i

    const statusHandleChange = (event) => {
        setStateStatus(event.target.value);
        const status = event.target.value;
        getOrderByUserName(username, status);
    };


    const getOrderByUserName = (username, stateStatus) => {
        UserService.getOrdersByUserName(username, stateStatus).then((response) => {
            setStateOrder(response.data.data)
        }).catch(error => {
            console.log(error)
        })
    }

    const updateStatus = (e) => {
        // e.preventDefault();
        getOrderByUserName(username, stateStatus);
    }

    const updateColor = (e) => {
        let color;
        switch (e) {
            case e = "Ch??? x??c nh???n":
                color = "aqua";
                break;
            case e = "??ang v???n chuy???n":
                color = "yellow";
                break;
            case e = "Giao h??ng th??nh c??ng":
                color = "yellow";
                break;
            case e = "???? h???y":
                color = "red";
                break;
            default:
                color = "";
        }
        return color;
    }

    return (
        <Container>
            <Grid container maxWidth='1300px' margin='auto' spacing={2} paddingBottom={7.5} >
                <Grid item xs={12}>
                    <Box
                        sx={{
                            flexGrow: 1,
                            bgcolor: 'background.paper',
                            display: 'flex',
                        }}
                    >
                        <Tabs
                            orientation="vertical"
                            variant="scrollable"
                            value={value}
                            onChange={handleChange}
                            aria-label="Vertical tabs example"
                            sx={{ paddingTop: 10, width: 250 }}
                        >
                            <Tab label="Th??ng tin" {...a11yProps(0)} />
                            <Tab label="?????a ch???" {...a11yProps(1)} />
                            <Tab label="?????i m???t kh???u" {...a11yProps(2)} />
                            <Tab label="????n h??ng c???a t??i" {...a11yProps(3)} />
                        </Tabs>
                        <TabPanel value={value} index={0}>
                            <SimpleCard title={'Th??ng tin c???a t??i'}>
                                <ValidatorForm onSubmit={updateUser} onError={() => null} >
                                    <Grid container spacing={6}>
                                        <Grid item xs={12} sx={{ mt: 2 }}>
                                            <TextField
                                                type="text"
                                                name="username"
                                                value={username}
                                                label="T??n t??i kho???n"
                                                disabled
                                            />

                                            <TextField
                                                name="firstName"
                                                type="text"
                                                onChange={inforHandleChange}
                                                value={stateInfor.firstName}
                                                label="H???"
                                            />

                                            <TextField
                                                type="text"
                                                name="lastName"
                                                onChange={inforHandleChange}
                                                value={stateInfor.lastName}
                                                label="T??n"
                                            />

                                            <FormControl>
                                                <FormLabel id="demo-row-radio-buttons-group-label">
                                                    Gi???i t??nh
                                                </FormLabel>
                                                <RadioGroup
                                                    row
                                                    aria-labelledby="demo-row-radio-buttons-group-label"
                                                    name="gender"
                                                    value={stateInfor.gender}
                                                    onChange={inforHandleChange}
                                                >
                                                    <FormControlLabel
                                                        value="0"
                                                        control={<Radio />}
                                                        label="Nam"
                                                    />
                                                    <FormControlLabel
                                                        value="1"
                                                        control={<Radio />}
                                                        label="N???"
                                                    />
                                                </RadioGroup>
                                            </FormControl>

                                            <TextField
                                                type="email"
                                                name="email"
                                                onChange={inforHandleChange}
                                                value={stateInfor.email}
                                                label="Email"
                                            />

                                            <TextField
                                                type="number"
                                                name="phone"
                                                onChange={inforHandleChange}
                                                value={stateInfor.phone}
                                                label="S??? ??i???n tho???i"
                                            />

                                            <Button
                                                color="success"
                                                variant="contained"
                                                size="large"
                                                type="submit"
                                                startIcon={'L??u'}
                                                style={{ width: '100%' }}
                                            >
                                            </Button>
                                        </Grid>
                                    </Grid>
                                </ValidatorForm>
                            </SimpleCard>
                        </TabPanel>
                        <TabPanel value={value} index={1}>
                            <SimpleCard title="Th??ng tin giao h??ng">
                                <ValidatorForm
                                    onSubmit={() => null}
                                    onError={() => null}
                                >
                                    {/* <Grid item xs={12}>
                                    <SimpleCard title="Th??ng tin giao h??ng"> */}
                                    <Grid container xs={12} spacing={1.5} >
                                        <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                            <TextField
                                                type="text"
                                                name="name"
                                                id="standard-basic"
                                                label="H??? t??n"
                                            />
                                        </Grid>
                                        <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                            <TextField
                                                type="text"
                                                name="name"
                                                id="standard-basic"
                                                label="S??? ??i???n tho???i"
                                            />
                                        </Grid>
                                        <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                            <TextField
                                                type="text"
                                                name="name"
                                                id="standard-basic"
                                                label="?????a ch??? email"
                                            />
                                        </Grid>
                                        <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                            <Autocomplete
                                                disablePortal
                                                options={listCity}
                                                getOptionLabel={(listCity) => listCity.name}
                                                onChange={(event, city) => { setAddress(city, null, null) }}
                                                sx={{ width: '100%' }}
                                                renderInput={(params) => <TextField {...params} label="T???nh/Th??nh ph???" />}
                                            />
                                        </Grid>
                                        <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                            <Autocomplete
                                                disablePortal
                                                options={listDistrict}
                                                getOptionLabel={(listDistrict) => listDistrict.name}
                                                onChange={(event, district) => { setAddress(null, district, null) }}
                                                sx={{ width: '100%' }}
                                                renderInput={(params) => <TextField {...params} label="Qu???n/Huy???n" />}
                                            />
                                        </Grid>
                                        <Grid item xs={4} sx={{ mt: 2, margin: 0 }}>
                                            <Autocomplete
                                                disablePortal
                                                options={listWard}
                                                getOptionLabel={(listWard) => listWard.name}
                                                onChange={(event, ward) => { setAddress(null, null, ward) }}
                                                sx={{ width: '100%' }}
                                                renderInput={(params) => <TextField {...params} label="Ph?????ng/X??" />}
                                            />
                                        </Grid>
                                        <Grid item xs={6} sx={{ mt: 2, margin: 0 }}>
                                            <TextField
                                                type="text"
                                                name="name"
                                                id="standard-basic"
                                                label="Nh???p ?????a ch???"
                                            />
                                        </Grid>
                                        <Grid item xs={6} sx={{ mt: 2, margin: 0 }}>
                                            <TextField
                                                type="textarea"
                                                name="name"
                                                id="standard-basic"
                                                label="Ghi ch??"
                                            />
                                        </Grid>
                                    </Grid>
                                    {/* </SimpleCard>
                                </Grid> */}

                                    <Button
                                        color="success"
                                        variant="contained"
                                        size="large"
                                        type="submit"
                                        startIcon={'L??u'}
                                        style={{ width: '100%' }}
                                    >
                                        {/* {name(id)} */}
                                    </Button>
                                </ValidatorForm>
                            </SimpleCard>
                        </TabPanel>
                        <TabPanel value={value} index={2}>
                            <SimpleCard title="?????i m???t kh???u">
                                <ValidatorForm
                                    onSubmit={updatePassword}
                                    onError={() => null}
                                >
                                    <Grid container spacing={6}>
                                        <Grid item lg={12} md={12} sm={12} xs={12} sx={{ mt: 2 }}>
                                            <TextValidator
                                                sx={{
                                                    mb: '16px',
                                                    width: '100%',
                                                }}
                                                label="M???t kh???u hi???n t???i"
                                                variant="outlined"
                                                onChange={formHandleChange}
                                                name="password"
                                                type="password"
                                                value={statePassword.password || ''}
                                                validators={['required']}
                                                errorMessages={[
                                                    'this field is required',
                                                ]}
                                            />
                                            <TextValidator
                                                sx={{
                                                    mb: '16px',
                                                    width: '100%',
                                                }}
                                                label="M???t kh???u m???i"
                                                variant="outlined"
                                                onChange={formHandleChange}
                                                name="newPassword"
                                                type="password"
                                                value={statePassword.newPassword || ''}
                                                validators={['required']}
                                                errorMessages={[
                                                    'this field is required',
                                                ]}
                                            />
                                            <TextValidator
                                                sx={{
                                                    mb: '16px',
                                                    width: '100%',
                                                }}
                                                label="X??c nh???n m???t kh???u"
                                                variant="outlined"
                                                onChange={formHandleChange}
                                                name="confirmPassword"
                                                type="password"
                                                value={statePassword.confirmPassword || ''}
                                                validators={[
                                                    'required',
                                                    'isPasswordMatch',
                                                ]}
                                                errorMessages={[
                                                    'this field is required',
                                                    "password didn't match",
                                                ]}
                                            />
                                        </Grid>
                                    </Grid>
                                    <Button
                                        color="success"
                                        variant="contained"
                                        size="large"
                                        type="submit"
                                        startIcon={'X??c nh???n'}
                                        style={{ width: '100%' }}
                                    >
                                    </Button>
                                </ValidatorForm>
                            </SimpleCard>
                        </TabPanel>
                        <TabPanel value={value} index={3}>
                            <SimpleCard title={'????n h??ng c???a t??i'}>
                                <ValidatorForm onSubmit={() => null} onError={() => null} >
                                    <Grid container spacing={6}>
                                        <Grid item xs={12} sx={{ mt: 2 }}>
                                            <Box width="100%" overflow="auto" minHeight="200px">

                                                <FormControl sx={{ m: 1, minWidth: 160 }} size="small">
                                                    <InputLabel id="demo-select-small">Tr???ng th??i</InputLabel>
                                                    <Select
                                                        labelId="demo-select-small"
                                                        id="demo-select-small"
                                                        value={stateStatus}
                                                        label="Tr???ng th??i"
                                                        onChange={statusHandleChange}
                                                    >
                                                        <MenuItem value={4}>T???t c???</MenuItem>
                                                        <MenuItem value={0}>Ch??? x??c nh???n</MenuItem>
                                                        <MenuItem value={1}>??ang v???n chuy???n</MenuItem>
                                                        <MenuItem value={2}>Giao th??nh c??ng</MenuItem>
                                                        <MenuItem value={3}>???? h???y</MenuItem>
                                                    </Select>
                                                </FormControl>

                                                <TableContainer component={Paper}>
                                                    <Table sx={{ minWidth: 700 }}>
                                                        <TableHead>
                                                            <TableRow >
                                                                <StyledTableCell align="center">M?? ????n h??ng</StyledTableCell>
                                                                <StyledTableCell align="center">Ng??y t???o</StyledTableCell>
                                                                <StyledTableCell align="center">Gi??</StyledTableCell>
                                                                <StyledTableCell align="center">Tr???ng th??i</StyledTableCell>
                                                                <StyledTableCell align="center" width="175px">H??nh ?????ng</StyledTableCell>
                                                            </TableRow>
                                                        </TableHead>
                                                        <TableBody>
                                                            {stateOrder.map((order) => (
                                                                <StyledTableRow key={order.id}>
                                                                    <StyledTableCell align="center">{order.code}</StyledTableCell>
                                                                    <StyledTableCell align="center">{order.createdAt}</StyledTableCell>
                                                                    <StyledTableCell align="center">{order.price}</StyledTableCell>
                                                                    <StyledTableCell align="center" style={{ color: updateColor(order.orderStatus) }}>{order.orderStatus}</StyledTableCell>

                                                                    <StyledTableCell align="center">
                                                                        <OrderDetail code={order.code} />
                                                                    </StyledTableCell>
                                                                </StyledTableRow>
                                                            ))}
                                                        </TableBody>
                                                    </Table>
                                                </TableContainer>
                                            </Box>

                                        </Grid>
                                    </Grid>
                                </ValidatorForm>
                            </SimpleCard>
                        </TabPanel>
                    </Box>
                </Grid>
            </Grid>
        </Container>
    )
}

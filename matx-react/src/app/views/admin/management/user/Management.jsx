import React, { useState, useEffect } from 'react'
import { SimpleCard } from 'app/components'
import { ValidatorForm } from 'react-material-ui-form-validator'
import { useNavigate, useParams } from 'react-router-dom'
import { Grid } from '@mui/material'
import { Container, ButtonForm, TextField, BreadcrumbCustom } from '../../base'
import { UserService } from 'app/services'
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import Notify from 'app/views/action/Notify'

const AppForm = () => {

    const [username, setUserName] = useState('')
    const [phone, setPhone] = useState('')
    const [email, setEmail] = useState('')
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [gender, setGender] = useState('0')
    const { id } = useParams();
    const navigate = useNavigate();
    const [notify, setNotify] = useState({ isOpen: false, message: '', type: '' })

    const createOrUpdate = (e) => {
        e.preventDefault();
        const user = { username, phone, email, firstName, lastName, gender }
        if (id !== "add") {
            UserService.updateUserAdmin(id, user).then((response) => {
                window.setTimeout(function () {
                    window.location.href = '/admin/user/list';
                }, 1000);
                setNotify({
                    isOpen: true,
                    message: 'Cập nhật thành công, vui lòng chờ!',
                    type: 'success'
                })
            }).catch(error => {
                console.log(error)
                setNotify({
                    isOpen: true,
                    message: 'Cập nhật thất bại!',
                    type: 'error'
                })
            })
        } else {
            
            UserService.createUserAdmin(user).then((response) => {
                window.setTimeout(function () {
                    window.location.href = '/admin/user/list';
                }, 1000);
                setNotify({
                    isOpen: true,
                    message: 'Tạo thành công, vui lòng chờ!',
                    type: 'success'
                })
            }).catch(error => {
                console.log(error)
                setNotify({
                    isOpen: true,
                    message: 'Cập nhật thất bại!',
                    type: 'error'
                })
            })
        }
    }

    useEffect(() => {
        UserService.getUserAdminById(id).then((response) => {
            setUserName(response.data.data.username)
            setPhone(response.data.data.phone)
            setEmail(response.data.data.email)
            setFirstName(response.data.data.firstName)
            setLastName(response.data.data.lastName)
            setGender(response.data.data.gender)
        }).catch(error => {
            console.log(error)
        })
    }, [])

    const title = (id) => {
        if (id === 'add') {
            return 'Tạo mới tài khoản'
        }

    }

    return (

        <Container>
            <div className="breadcrumb">
                <BreadcrumbCustom id={id} username='Tài khoản' />
            </div>
            <SimpleCard title={title(id)}>
                <div>
                    <ValidatorForm onSubmit={createOrUpdate} onError={() => null}>
                        <Grid container spacing={6}>
                            <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                                <TextField
                                    type="text"
                                    name=" name"
                                    onChange={(e) => { setUserName(e.target.value) }}
                                    value={username}
                                    label="Tên tài khoản"
                                    errorMessages={['this field is required']}
                                />

                                <TextField
                                    type="text"
                                    name=" FirstName"
                                    onChange={(e) => { setFirstName(e.target.value) }}
                                    value={firstName}
                                    label="Họ"
                                    errorMessages={['this field is required']}
                                />

                                <TextField
                                    type="text"
                                    name=" LastName"
                                    onChange={(e) => { setLastName(e.target.value) }}
                                    value={lastName}
                                    label="Tên"
                                    errorMessages={['this field is required']}
                                />

                                <FormControl>
                                    <FormLabel id="demo-row-radio-buttons-group-label">Giới tính</FormLabel>
                                    <RadioGroup
                                        row
                                        aria-labelledby="demo-row-radio-buttons-group-label"
                                        name="row-radio-buttons-group"
                                        value={gender}
                                        onChange={(e) => { setGender(e.target.value) }}
                                    >
                                        <FormControlLabel value="0" control={<Radio />} label="Nam" />
                                        <FormControlLabel value="1" control={<Radio />} label="Nữ" />
                                    </RadioGroup>
                                </FormControl>

                                <TextField
                                    type="text"
                                    name="email"
                                    onChange={(e) => { setEmail(e.target.value) }}
                                    value={email}
                                    label="Email"
                                    errorMessages={['this field is required']}
                                />

                                <TextField
                                    type="text"
                                    phone="phone"
                                    onChange={(e) => { setPhone(e.target.value) }}
                                    value={phone}
                                    label="Số điện thoại"
                                    errorMessages={['this field is required']}
                                />

                            </Grid>
                        </Grid>
                        <ButtonForm id={id} />
                    </ValidatorForm>
                </div>
            </SimpleCard>
            <>
                <Notify
                    notify={notify}
                    setNotify={setNotify}
                >
                </Notify>
            </>
        </Container>
    )
}
export default AppForm

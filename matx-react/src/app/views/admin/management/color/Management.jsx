import React, { useState, useEffect } from 'react'
import { SimpleCard } from 'app/components'
import { ValidatorForm } from 'react-material-ui-form-validator'
import { useNavigate, useParams } from 'react-router-dom'
import { Grid } from '@mui/material'
import { Container, ButtonForm, TextField, BreadcrumbCustom } from '../../base'
import { ColorService } from 'app/services'
import {Notify} from 'app/views/action'
const AppForm = () => {

    const [name, setName] = useState('')
    const [code, setCode] = useState('')
    const { id } = useParams();
    const navigate = useNavigate();
    const [notify, setNotify] = useState({ isOpen: false, message: '', type: '' })

    const title = (id) => {
        if (id === 'add') {
            return 'Tạo mới màu sắc'
        } else {
            return 'Cập nhật màu sắc'
        }
    }

    const createOrUpdate = (e) => {
        e.preventDefault();
        const color = { name, code }
        if (id !== "add") {
            ColorService.updateColorAdmin(id, color).then((response) => {
                window.setTimeout(function() {
                    window.location.href = '/admin/color/list';
                }, 1000);
                setNotify({
                    isOpen: true,
                    message: 'Cập nhật thành công, vui lòng chờ!',
                    type: 'success'
                })
            }).catch(error => {
                setNotify({
                    isOpen: true,
                    message: 'Cập nhật thất bại!',
                    type: 'error'
                })
                console.log(error)
            })

        } else {
            ColorService.createColorAdmin(color).then((response) => {
                window.setTimeout(function() {
                    window.location.href = '/admin/color/list';
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
                    message: 'Tạo thất bại!',
                    type: 'error'
                })
            })
        }
        setNotify({
            ...notify,
            isOpen: false
        })
    }

    useEffect(() => {
        ColorService.getColorAdminById(id).then((response) => {
            setName(response.data.data.name)
            setCode(response.data.data.code)
        }).catch(error => {
            console.log(error)
        })
    }, [])

    return (

        <Container>
            <div className="breadcrumb">
                <BreadcrumbCustom id={id} name='Màu sắc' />
            </div>
            <SimpleCard title={title()}>
                <div>
                    <ValidatorForm id="createOrUpdate" onSubmit={createOrUpdate} onError={() => null}>
                        <Grid container spacing={6}>
                            <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                                <TextField
                                    type="text"
                                    name="name"
                                    onChange={(e) => setName(e.target.value)}
                                    value={name}
                                    label="Tên màu sắc"
                                    errorMessages={['this field is required']}
                                />

                                <TextField
                                    type="color"
                                    name="code"
                                    onChange={(e) => setCode(e.target.value)}
                                    value={code}
                                    label="Mã màu sắc"
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

import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { SimpleCard } from 'app/components'
import { ValidatorForm } from 'react-material-ui-form-validator'
import { Grid, Box } from '@mui/material'
import { Container, ButtonForm, TextField, BreadcrumbCustom } from '../../base'
import { PromotionService } from 'app/services'
import { display } from '@mui/system'
import { DatePicker } from '@mui/lab'
import AdapterDateFns from '@mui/lab/AdapterDateFns'
import LocalizationProvider from '@mui/lab/LocalizationProvider'
import Notify from 'app/views/action/Notify'

const AppForm = () => {

    const [name, setName] = useState('')
    const [discountPercent, setDiscountPercent] = useState('')
    const [discountMax, setDiscountMax] = useState('')
    const [discountFrom, setDiscountFrom] = useState('')
    const [startDate, setStartDate] = useState(new Date())
    const [endDate, setEndDate] = useState(new Date())
    const navigate = useNavigate()
    const { id } = useParams();
    const [notify, setNotify] = useState({ isOpen: false, message: '', type: '' })

    const saveOrUpdate = (e) => {
        e.preventDefault();

        const promotion = { name, discountPercent, discountMax, discountFrom, startDate, endDate }

        if (id !== "add") {
            PromotionService.updatePromotionAdmin(id, promotion).then((response) => {
                window.setTimeout(function () {
                    window.location.href = '/admin/promotion/list';
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
            PromotionService.createPromotionAdmin(promotion).then((response) => {
                window.setTimeout(function () {
                    window.location.href = '/admin/promotion/list';
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
        PromotionService.getPromotionAdminById(id).then((response) => {
            setName(response.data.data.name)
            setDiscountPercent(response.data.data.discountPercent)
            setDiscountMax(response.data.data.discountMax)
            setDiscountFrom(response.data.data.discountFrom)
            setStartDate(response.data.data.startDate)
            setEndDate(response.data.data.endDate)
        }).catch(error => {
            console.log(error)
        })
    }, [])

    const title = (id) => {
        if (id === 'add') {
            return "Tạo mới khuyến mại"
        } else {
            return "Cập nhật khuyến mại"
        }
    }


    return (
        <Container>
            <div className="breadcrumb">
                <BreadcrumbCustom id={id} name='Khuyến mại' />
            </div>
            <SimpleCard title={title(id)}>
                <div>
                    <ValidatorForm onSubmit={saveOrUpdate} onError={() => null}>
                        <Grid container spacing={6}>
                            <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                                <TextField
                                    type="text"
                                    name="name"
                                    onChange={(e) => { setName(e.target.value) }}
                                    value={name}
                                    label="Tên khuyến mại"
                                />
                                <TextField
                                    type="number"
                                    name="DiscountPercent"
                                    onChange={(e) => { setDiscountPercent(e.target.value) }}
                                    value={discountPercent}
                                    label="Giảm giá (%)"
                                />
                                <TextField
                                    type="number"
                                    name="DiscountMax"
                                    onChange={(e) => { setDiscountMax(e.target.value) }}
                                    value={discountMax}
                                    label="Giảm tối đa"
                                />
                                <TextField
                                    type="number"
                                    name="DiscountFrom"
                                    onChange={(e) => { setDiscountFrom(e.target.value) }}
                                    value={discountFrom}
                                    label="Giảm cho đơn hàng từ"
                                />
                                <Box width="100%" display="flex" alignItems="center" marginBottom='10px' justifyContent="flex-start">
                                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                                        <DatePicker
                                            value={startDate}
                                            onChange={(newValue) => setStartDate(newValue)}
                                            mask="dd/MM/yyyy"
                                            renderInput={(props) => (
                                                <TextField
                                                    {...props}
                                                    id="start-date"
                                                    label="Ngày bắt đầu"
                                                    sx={{ mb: 2, width: '100%', marginRight: '60px', paddingRight: '10px' }}
                                                />
                                            )}
                                        />
                                    </LocalizationProvider>
                                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                                        <DatePicker
                                            value={endDate}
                                            onChange={(newValue) => setEndDate(newValue)}
                                            mask="dd/MM/yyyy"
                                            renderInput={(props) => (
                                                <TextField
                                                    {...props}
                                                    id="ensd-date"
                                                    label="Ngày kết thúc"
                                                    sx={{ mb: 2, width: '100%', marginRight: '55px' }}
                                                />
                                            )}
                                        />
                                    </LocalizationProvider>
                                </Box>

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

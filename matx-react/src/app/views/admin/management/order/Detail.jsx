import React, { useState, useEffect } from 'react'
import {
    useParams, useNavigate
} from 'react-router-dom';
import { SimpleCard } from 'app/components'
import SaveIcon from '@mui/icons-material/Save';
import { Box } from '@mui/system'
import { Grid } from '@mui/material'
import { ValidatorForm } from 'react-material-ui-form-validator'
import { Container, TextField, StyledTableRow, StyledTableCell, ButtonForm } from '../../base'
import { OrderService } from 'app/services'
import '../../../../css/Module.css'
import {
    Table,
    TableHead,
    TableBody,
    Fab,
    Icon,
    TableRow,
    TableContainer,
    Paper,
    
} from '@mui/material'
const AppForm = () => {
    const [status, setStatus] = useState('')
    const [orders, setOrders] = useState([])
    const [listOrders, setListOrders] = useState([])
    const navigate = useNavigate();
    const { id } = useParams();

    const getOrderByIdAdmin = (id) => {
        OrderService.getOrderByIdAdmin(id).then((response) => {
            setOrders(response.data.data)
            setListOrders(response.data.data.listProductOrders);
        }).catch(error => {
            console.log(error);
        })
    }
    const updateStatusAdmin = (e) => {
        e.preventDefault();
        OrderService.updateStatusAdmin(id, orders).then((response) => {

        }).catch(error => {
            console.log(error);
        })
    }
    useEffect(() => {
        getOrderByIdAdmin(id);
    }, [])

    const [isShow, setIsShow] = React.useState(false);

    const onClickk = () => setIsShow(true);
    const Text = () => <div>

        <ValidatorForm onSubmit={updateStatusAdmin} onError={() => null} className='Update-form'>
            <Grid container spacing={6}>
                <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                    <TextField
                        type="text"
                        id="standard-basic"
                        onChange={(e) => setStatus(e.target.value)}
                        label="Trạng thái"
                    />
                </Grid>
            </Grid>

            <ButtonForm id={id} />

        </ValidatorForm>
    </div>;
    return (
        <Container>

            <div className='Order-detail'>
                <div className='Order-detail-top'>
                    <Fab
                        size="small"
                        aria-label="arrow_back"
                        className="button"
                        onClick={() => navigate('/admin/order/list')}
                    >
                        <Icon>arrow_back</Icon>
                    </Fab>
                    <h2>Chi tiết đơn hàng
                        <span style={{ fontWeight: "200" }}>#{orders.code} </span>
                    </h2>
                    <h3 style={{ color: "#88cad8" }}>{orders.orderStatus}

                        <Fab
                            size="small"
                            aria-label="mode_edit"
                            className="but"
                            onClick={onClickk}
                        >
                            <Icon>mode_edit</Icon>
                            {isShow ? <Text /> : null}
                        </Fab>
                    </h3>
                    <hr></hr>
                    <div className='Order-detail-view'>
                        <div className="Order-detail-view-left">
                            <h3>Địa chỉ nhận hàng</h3>
                            <span>{orders.customerName}</span><br />
                            <span>{orders.customerPhone}</span><br />
                            <span>{orders.customerAddress}</span>
                        </div>

                    </div>
                </div>
            </div>

            <div className='detail'>
                <SimpleCard>
                    <Box width="100%" >
                        <TableContainer component={Paper} >
                            <Table sx={{ minWidth: 700 }} aria-label="customized table">
                                <TableHead>
                                    <TableRow>
                                        <StyledTableCell align="center" width="50px">STT</StyledTableCell>
                                        <StyledTableCell align="center">Tên sản phẩm</StyledTableCell>
                                        <StyledTableCell align="center">Size</StyledTableCell>
                                        <StyledTableCell align="center">Màu</StyledTableCell>
                                        <StyledTableCell align="center">Số lượng</StyledTableCell>
                                        <StyledTableCell align="center">Giá</StyledTableCell>


                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {listOrders.map((order, index) => (
                                        <StyledTableRow key={order}>
                                            <StyledTableCell align="center">{++index}</StyledTableCell>
                                            <StyledTableCell align="center">{order.name}</StyledTableCell>
                                            <StyledTableCell align="center">{order.size}</StyledTableCell>
                                            <StyledTableCell align="center">{order.color}</StyledTableCell>
                                            <StyledTableCell align="center">{order.quantity}</StyledTableCell>
                                            <StyledTableCell align="center">{order.price}</StyledTableCell>
                                            
                                        </StyledTableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Box>
                </SimpleCard>
            </div>
        </Container>
    )
}
export default AppForm

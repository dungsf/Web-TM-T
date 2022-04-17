import React, { useState, useEffect } from 'react'
import {
    Table,
    TableHead,
    TableBody,
    Fab,
    Icon,
    TableRow,
    TableContainer,
    Paper,
    Button,
    Pagination,
    Stack,
} from '@mui/material'
import DialogActions from '@mui/material/DialogActions'
import Dialog from '@mui/material/Dialog'
import DialogTitle from '@mui/material/DialogTitle'
import { Box } from '@mui/system'
import { TextField, SimpleCard, StyledTableCell, StyledTableRow } from '../base'
import { UserService } from 'app/services'

export default function OrderDetail(props) {
    const code = props.code
    const [open, setOpen] = useState(false)
    const [stateOrderDetai, setStateOrderDetail] = useState({})
    const [listProductOrders, setListProductOrders] = useState([])

    const handleClickOpen = () => {
        setOpen(true)
        getOrderByCode()
    }

    const handleClose = () => {
        setOpen(false)
    }

    const getOrderByCode = () => {
        UserService.getOrderDetailsByCode(code)
            .then((response) => {
                const d = response.data.data
                setStateOrderDetail(d)
                setListProductOrders(d.listProductOrders)
            })
            .catch((error) => {
                console.log(error)
            })
    }

    return (
        <div>
            <Button
                onClick={handleClickOpen}
                type="button"
                variant="contained"
                color="secondary"
                className="butMUI-update"
            >
                <Icon>visibility</Icon>
            </Button>
            <Dialog
                open={open}
                onClose={handleClose}
                sx={{
                    '& .MuiDialog-container': {
                        '& .MuiPaper-root': {
                            width: '100%',
                            maxWidth: '1000px',
                        },
                    },
                }}
            >
                <DialogTitle>Chi tiết đơn hàng</DialogTitle>
                <Box width="auto" margin='20px' overflow="auto">
                    {/* {Mot table} */}
                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 700 }}>
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell align="center">
                                        Sản phẩm
                                    </StyledTableCell>
                                    <StyledTableCell align="center">
                                        Giá bán
                                    </StyledTableCell>
                                    <StyledTableCell align="center">
                                        Số lượng
                                    </StyledTableCell>
                                    <StyledTableCell align="center">
                                        Tổng tiền
                                    </StyledTableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {listProductOrders.map((order) => (
                                    <StyledTableRow key={order.id}>
                                        <StyledTableCell align="center">
                                            {order.name}
                                        </StyledTableCell>
                                        <StyledTableCell align="center">
                                            {order.price}
                                        </StyledTableCell>
                                        <StyledTableCell align="center">
                                            {order.quantity}
                                        </StyledTableCell>
                                        <StyledTableCell align="center">
                                            {order.total}
                                        </StyledTableCell>
                                    </StyledTableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </Box>
            </Dialog>
        </div>
    )
}

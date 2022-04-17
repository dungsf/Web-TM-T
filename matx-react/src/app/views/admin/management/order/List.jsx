import React, { useState, useEffect } from 'react'
import { Box } from '@mui/system'
import { useNavigate, useParams } from 'react-router-dom'
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
    Stack
} from '@mui/material'
import { Container, SearchBox, SearchContainer, SearchInput ,StyledTableRow, StyledTableCell, Breadcrumb, SimpleCard } from '../../base'
import { OrderService } from 'app/services'
import '../../../../css/Module.css'
const List = () => {
    
    const navigate = useNavigate()
    const [content, setContent] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState()
    const [totalPages, setTotalPages] = useState()
    const [keyword,setKeyword] = useState('')
    // const [orders, setOrders] = useState([])
    const [orderStatus, setStatus] = useState('')
    const [id, setId] = useState('')
    
    useEffect(() => {
       getData(page, keyword);

    }, [])
    
    const handleChangePage = (event,value) => {
        setPage(value-1)
        getData(value-1,keyword)
    };

    const handleChangeSearch = (event) => {
        const keyword = event.target.value
        setKeyword(keyword)
        setPage(0)
        getData(0,keyword)
    }

    const getData = (page,keyword) => {
        OrderService.getOrdersPagingAdmin(page, keyword).then((response) => {
            const data = response.data
            setContent(data.content)
            setPage(data.page)
            setSize(data.size)
            setTotalPages(data.totalPages)
        }).catch(error =>{
            console.log(error)
        })
    }
    // const getAllOrdersAdmin = () => {
    //     OrderService.getAllOrder().then((response) => {
    //         setOrders(response.data.data)
            
    //     }).catch(error => {
    //         console.log(error);
    //     })
    // }
    const updateStatus = (e) => {
        e.preventDefault();
        const sta = { orderStatus }
        OrderService.updateStatus(id, sta).then((response) => {

        }).catch(error => {
            console.log(error);
        })
    }
 
    
    const updateStatusAdmin = (e) => {
        let color;
        switch (e) {
            case e = "Chờ xác nhận":
                color = "aqua";
                break;
            case e = "Đang vận chuyển":
                color = "yellow";
                break;
            case e = "Giao hàng thành công":
                color = "green";
                break;
            case e = "Đã hủy":
                color = "red";
                break;
            default:
                color = "";
        }
        return color;
    }
   
    
    return (
        <Container>
            <div className="breadcrumb">
                <Breadcrumb
                    routeSegments={[
                        { name: 'Đơn hàng' },
                        { name: 'Danh sách' },
                    ]}
                />
            </div>
            <Box width="100%" marginBottom='10px' display='flex' justifyContent='flex-end'>
                <SearchContainer>
                    <SearchInput type="text" placeholder="Nhập từ khóa cần tìm..." autoFocus onChange={handleChangeSearch} />
                    <Icon sx={{  mx: 2, verticalAlign: 'middle', color: 'black' }}>search</Icon>
                </SearchContainer>
            </Box>
            <SimpleCard title="Danh sách đơn hàng">
                <Box width="100%" overflow="auto">
                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 700 }} aria-label="customized table">
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell align="center" width="50px">STT</StyledTableCell>
                                    <StyledTableCell align="center">Mã đơn hàng</StyledTableCell>
                                    <StyledTableCell align="center">Ngày tạo</StyledTableCell>
                                    <StyledTableCell align="center">Tên khách hàng</StyledTableCell>
                                    <StyledTableCell align="center">Total</StyledTableCell>
                                    <StyledTableCell align="center">Trạng thái</StyledTableCell>
                                    <StyledTableCell align="center">Hành động</StyledTableCell>

                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {content.map((order, index) => (
                                    <StyledTableRow key={order.id}>
                                        <StyledTableCell align="left">{++index+((page) * size)}</StyledTableCell>
                                        <StyledTableCell align="center">{order.code}</StyledTableCell>
                                        <StyledTableCell align="center">{order.createAt}</StyledTableCell>
                                        <StyledTableCell align="center">{order.customerName}</StyledTableCell>
                                        <StyledTableCell align="center">{order.total}</StyledTableCell>
                                        <StyledTableCell align="center" style={{ color: updateStatusAdmin(order.orderStatus) }}>{order.orderStatus}</StyledTableCell>
                                        <StyledTableCell align="center">
                                            <Button
                                                type="submit"
                                                variant="contained"
                                                color="secondary"
                                                className='butMUI-update'
                                                onClick={() => navigate("/admin/order/detail/" + order.id)}
                                            >
                                                <Icon>visibility</Icon>
                                            </Button>                                       
                                            <form onSubmit={updateStatus}>  
                                                <Button
                                                    type="submit"
                                                    variant="contained"
                                                    color="success"
                                                    
                                                    className='butMUI-update'
                                                    onClick={(event) => {
                                                        setStatus(1);
                                                        setId(order.id);
                                                    }}
                                                >
                                                    Vận chuyển
                                                </Button>
                                                <Button type="submit"
                                                    variant="contained"
                                                    color="warning"
                                                    className='butMUI-update'
                                                    onClick={(event) => {
                                                        setStatus(3);
                                                        setId(order.id);   
                                                    }}>Hủy</Button>
                                            </form>
                                        </StyledTableCell>
                                    </StyledTableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <Stack spacing={2} paddingTop={3} paddingBottom={1}>
                        <Box my={2} display="flex" justifyContent="center">
                            <Pagination
                                count={totalPages}
                                page={page+1} 
                                onChange={handleChangePage} 
                                variant="outlined"
                                color="primary"
                                showFirstButton 
                                showLastButton
                            />  
                        </Box>
                    </Stack>
                </Box>
            </SimpleCard>
        </Container>
    )
}

export default List
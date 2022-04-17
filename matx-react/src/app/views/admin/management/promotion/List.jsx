import React, {useState, useEffect} from 'react'
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
    Pagination,
    Stack
} from '@mui/material'
import { Container, SearchContainer,SearchInput, StyledTableRow, StyledTableCell, Breadcrumb, SimpleCard } from '../../base'
import { PromotionService } from 'app/services'
import Notify from 'app/views/action/Notify'
import AlertDialog from 'app/views/action/Confirm'

const List = () => {

    const navigate = useNavigate()
    const [content, setContent] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState()
    const [totalPages, setTotalPages] = useState()
    const [keyword,setKeyword] = useState('')
    const [notify, setNotify] = useState({isOpen: false, message: '', type: ''})
    const [confirmDialog, setConfirmDialog] = useState({isOpen: false, title: '', subTitle: ''})

    useEffect(() => {
        getData(page,keyword)
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
        PromotionService.getPromotionsPagingAdmin(page,keyword).then((response) => {
            const data = response.data
            setContent(data.content)
            setPage(data.page)
            setSize(data.size)
            setTotalPages(data.totalPages)
        }).catch(error =>{
            console.log(error)
        })
    }

    const deletePromotion = (id) => {
        PromotionService.deletePromotionAdmin(id).then((response) =>{
            getData(page,keyword)
            setNotify({
                isOpen: true,
                message: 'Xóa thành công!',
                type: 'success'
            })
        }).catch(error =>{
            console.log(error);
            setNotify({
                isOpen: true,
                message: 'Xóa thất bại!',
                type: 'error'
            })
        })
        setNotify({
            ...notify,
            isOpen: false
        })
        setConfirmDialog({
            ...confirmDialog,
            isOpen: false
        })
    }

    return (
        <Container>
            <div className="breadcrumb">
                <Breadcrumb
                    routeSegments={[
                        { name: 'Khuyến mại' },
                        { name: 'Danh sách' },
                    ]}
                />
            </div>
            <Box width="100%" display="flex" alignItems="center" marginBottom= '10px' justifyContent="space-between">
                <Fab
                    size="medium"
                    color="primary"
                    aria-label="Add"
                    className="button"
                    onClick={() => navigate("/admin/promotion/management/add")}
                >
                    <Icon>add</Icon>
                </Fab>
                <SearchContainer>
                    <SearchInput type="text" placeholder="Nhập từ khóa cần tìm..." autoFocus onChange={handleChangeSearch} />
                    <Icon sx={{  mx: 2, verticalAlign: 'middle', color: 'black' }}>search</Icon>
                </SearchContainer>
            </Box>
            <SimpleCard title="Danh sách khuyến mại">
                <Box width="100%" overflow="auto">
                    <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 700 }} aria-label="customized table">
                        <TableHead>
                            <TableRow>
                                <StyledTableCell align="center" width="50px">STT</StyledTableCell>
                                <StyledTableCell align="center">Tên khuyến mại</StyledTableCell>
                                <StyledTableCell align="center">Giảm giá (%)</StyledTableCell>
                                <StyledTableCell align="center">Ngày bắt đầu</StyledTableCell>
                                <StyledTableCell align="center">Ngày kết thúc</StyledTableCell>

                                <StyledTableCell align="center" width="175px">Hành động</StyledTableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {content.map((promotion, index) => (
                                <StyledTableRow key={promotion.id}>
                                    <StyledTableCell align="center">{++index+((page) * size)}</StyledTableCell>
                                    <StyledTableCell align="center">{promotion.name}</StyledTableCell>
                                    <StyledTableCell align="center">{promotion.discountPercent}%</StyledTableCell>
                                    <StyledTableCell align="center">{promotion.startDate}</StyledTableCell>
                                    <StyledTableCell align="center">{promotion.endDate}</StyledTableCell>
                                    
                                    <StyledTableCell align="center">
                                        <Fab
                                            size="small"
                                            color="secondary"
                                            aria-label="Edit"
                                            className="button"
                                            onClick={() => navigate("/admin/promotion/management/"+promotion.id)}
                                        >
                                            <Icon>edit</Icon>
                                        </Fab>
                                        <Fab
                                            size="small"
                                            color="secondary"
                                            aria-label="Delete"
                                            className="button"
                                            onClick={() =>{
                                                setConfirmDialog({
                                                    isOpen: true,
                                                    title: "Bạn có chắc chắn xóa!",
                                                    subTitle: "Bạn sẽ không thể hoàn tác lại thao tác này!",
                                                    onConfirm:()=> {deletePromotion(promotion.id)}
                                                })
                                            }}

                                        >
                                            <Icon>delete</Icon>
                                        </Fab>
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
            <>
                    <Notify
                        notify = {notify}
                        setNotify = {setNotify}
                    >

                    </Notify>
                    <AlertDialog
                        confirmDialog ={confirmDialog}
                        setConfirmDialog = {setConfirmDialog}
                    >

                    </AlertDialog>
            </>
        </Container>
    )
}

export default List
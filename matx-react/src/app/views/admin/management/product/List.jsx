import React, { useState, useEffect } from 'react'
import { Box } from '@mui/system'
import { useNavigate } from 'react-router-dom'
import Notify from 'app/views/action/Notify'
import AlertDialog from 'app/views/action/Confirm'

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
import { Container, SearchBox, SearchContainer, SearchInput, StyledTableRow, StyledTableCell, Breadcrumb, SimpleCard } from '../../base'
import { ProductService } from 'app/services'

const List = () => {
    const navigate = useNavigate()
    const [content, setContent] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState()
    const [totalPages, setTotalPages] = useState()
    const [keyword, setKeyword] = useState('')
    const [notify, setNotify] = useState({ isOpen: false, message: '', type: '' })
    const [confirmDialog, setConfirmDialog] = useState({ isOpen: false, title: '', subTitle: '' })

    useEffect(() => {
        getData(page, keyword)
    }, [])

    const handleChangePage = (event, value) => {
        setPage(value - 1)
        getData(value - 1, keyword)
    };

    const handleChangeSearch = (event) => {
        const keyword = event.target.value
        setKeyword(keyword)
        getData(page, keyword)
    }

    const getData = (page, keyword) => {
        ProductService.getProductsPagingAdmin(page, keyword).then((response) => {
            const data = response.data
            setContent(data.content)
            setPage(data.page)
            setSize(data.size)
            setTotalPages(data.totalPages)
        }).catch(error => {
            console.log(error)
        })
    }
    const deleteProduct = (id) => {
        ProductService.deleteProduct(id).then((response) => {
            getData(page, keyword);
            setNotify({
                isOpen: true,
                message: 'Xóa thành công!',
                type: 'success'
            })
        }).catch(error => {
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
    const getCategoryParent = (cateParen) => {
        if (cateParen) {
            return cateParen.name
        }
        else {
            return
        }
    }
    const hotText = (e) => {
        let a = ""
        if (e) {
            a = "Có"
        }
        else {
            a = "Không"
        }
        return a
    }
    return (
        <Container>
            <div className="breadcrumb">
                <Breadcrumb
                    routeSegments={[
                        { name: 'Sản phẩm' },
                        { name: 'Danh sách' },
                    ]}
                />
            </div>
            <Box width="100%" display="flex" alignItems="center" marginBottom='10px' justifyContent="space-between">
                <Fab
                    size="medium"
                    color="primary"
                    aria-label="Add"
                    className="button"
                    onClick={() => navigate("/admin/product/management/add")}
                >
                    <Icon>add</Icon>
                </Fab>
                <SearchContainer>
                    <SearchInput type="text" placeholder="Nhập từ khóa cần tìm..." autoFocus onChange={handleChangeSearch} />
                    <Icon sx={{ mx: 2, verticalAlign: 'middle', color: 'black' }}>search</Icon>
                </SearchContainer>
            </Box>
            <SimpleCard title="Danh sách sản phẩm">

                <Box width="100%" overflow="auto">
                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 700 }} aria-label="customized table">
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell align="center" width="50px">STT</StyledTableCell>
                                    <StyledTableCell align="center">Hình ảnh</StyledTableCell>
                                    <StyledTableCell align="center">Tên sản phẩm</StyledTableCell>
                                    <StyledTableCell align="center">Danh mục</StyledTableCell>
                                    <StyledTableCell align="center">Giá bán</StyledTableCell>
                                    <StyledTableCell align="center">Giá khuyến mại</StyledTableCell>
                                    <StyledTableCell align="center" width="75px">HOT</StyledTableCell>
                                    <StyledTableCell align="center" width="175px">Hành động</StyledTableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {content.map((p, index) => (
                                    <StyledTableRow key={p.id}>
                                        <StyledTableCell align="center">{++index + ((page) * size)}</StyledTableCell>
                                        <StyledTableCell align="center">{p.avatar}</StyledTableCell>
                                        <StyledTableCell align="center">{p.name}</StyledTableCell>
                                        <StyledTableCell align="center">{getCategoryParent(p.category)}</StyledTableCell>
                                        <StyledTableCell align="center">{p.price}</StyledTableCell>
                                        <StyledTableCell align="center">{p.priceSale}</StyledTableCell>
                                        <StyledTableCell align="center">{hotText(p.hot)}</StyledTableCell>
                                        <StyledTableCell align="center">
                                            <Fab
                                                size="small"
                                                color="secondary"
                                                aria-label="Edit"
                                                className="button"
                                                onClick={() => navigate("/admin/product/management/" + p.id)}
                                            >
                                                <Icon>edit</Icon>
                                            </Fab>
                                            <Fab
                                                size="small"
                                                color="secondary"
                                                aria-label="Delete"
                                                className="button"
                                                onClick={() => {
                                                    setConfirmDialog({
                                                        isOpen: true,
                                                        title: "Bạn có chắc chắn xóa!",
                                                        subTitle: "Bạn sẽ không thể hoàn tác lại thao tác này!",
                                                        onConfirm: () => { deleteProduct(p.id) },
                                                        typeButton: 'button'
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
                                page={page + 1}
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
                    notify={notify}
                    setNotify={setNotify}
                >

                </Notify>
                <AlertDialog
                    confirmDialog={confirmDialog}
                    setConfirmDialog={setConfirmDialog}
                >
                </AlertDialog>
            </>
        </Container>
    )
}

export default List
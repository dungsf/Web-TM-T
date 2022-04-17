import React, { Fragment, PureComponent, useState, useEffect } from 'react'
import { styled } from '@mui/system'
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Container, TextField, StyledTableRow, StyledTableCell, SimpleCard } from '../../base'
import Select, { SelectChangeEvent } from '@mui/material/Select';
import {
    Table,
    TableHead,
    TableBody,
    Autocomplete,
    Box,
    TableRow,
    TableContainer,
    Paper,
    Pagination,
    Stack
} from '@mui/material'
import StatisTicalService from 'app/services/StatisTicalService';
import FormControl from '@mui/material/FormControl'
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import { Form } from 'formik';
import { getDate } from 'date-fns';

const ContentBox = styled('div')(({ theme }) => ({
    margin: '30px',
    [theme.breakpoints.down('sm')]: {
        margin: '16px',
    },
}))


const Analytics = () => {
    const [data, setData] = useState([])
    const [content, setContent] = useState([])
    const [page, setPage] = useState(0)
    const [size, setSize] = useState()
    const [totalPages, setTotalPages] = useState()
    const [year, setYear] = useState(2018)
    const [month, setMonth] = useState(2)

    const handleChangePage = (event, value) => {
        setPage(value - 1)
        getOrderByMonth(value - 1, year, month)
    };

    const getOrderByMonth = (page, year, month) => {
        StatisTicalService.getOrderByMonth(page, year, month).then((response) => {
            const d = response.data
            setContent(d.content)
            setPage(d.page)
            setSize(d.size)
            setTotalPages(d.totalPages)
        }).catch(error => {
            console.log(error)
        })
    }
    useEffect(() => {
        getRevenue(year);
    }, [])
    function demoOnClick(e) {
        alert(e);
    }
    const getData = (data) => {
        const month = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        let listMonth = []
        month.forEach((e) => {
            listMonth.push({ month: e, total: 0 })
        })

        if (data.length != 0) {
            data.forEach((e) => {
                listMonth.find(m => m.month == e.month).total = e.total
            })
        }
        setData(listMonth)

    }
    const getRevenue = (year) => {
        StatisTicalService.getYear(year).then((response) => {
            getData(response.data);
        })
    }
    const handleChange = (e) => {
        const y = e.target.value
        setYear(y)
        getRevenue(y);
    }
    return (
        <Container>
            <SimpleCard title="Thống kê">
                <FormControl sx={{ m: 1, minWidth: 160 }} size="small" style={{ marginBottom: '20px' }}>
                    <InputLabel id="demo-select-small">Năm</InputLabel>
                    <Select
                        labelId="demo-select-small"
                        id="demo-select-small"
                        value={year}
                        label="Trạng thái"
                        onChange={handleChange}
                    >
                        <MenuItem value={2018}>2018</MenuItem>
                        <MenuItem value={2019}>2019</MenuItem>
                        <MenuItem value={2020}>2020</MenuItem>
                        <MenuItem value={2021}>2021</MenuItem>
                        <MenuItem value={2022}>2022</MenuItem>
                        <MenuItem value={2022}>2023</MenuItem>

                    </Select>
                </FormControl>
                <ResponsiveContainer width="100%" height={400}>
                    <LineChart
                        width={500}
                        height={300}
                        data={data}
                        onClick={(e) => {
                            const values = e.activeLabel
                            setMonth(values)
                            getOrderByMonth(page, year, values)
                        }
                        }
                        margin={{
                            top: 5,
                            right: 30,
                            left: 20,
                            bottom: 5,
                        }}
                    >
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="month" label={"Tháng"}/>
                        <YAxis label={"R"} />
                        <Tooltip />
                        <Legend />
                        <Line type="monotone" dataKey="total" stroke="#8884d8" activeDot={{ r: 12 }} />
                    </LineChart>
                </ResponsiveContainer>
            </SimpleCard>
            <SimpleCard title="Danh sách danh mục">
                <Box width="100%" overflow="auto">
                    <TableContainer component={Paper} sx={{ minHeight: 230 }}>
                        <Table sx={{ minWidth: 700 }} aria-label="customized table">
                            <TableHead>
                                <TableRow>
                                    <StyledTableCell align="center" width="50px">STT</StyledTableCell>
                                    <StyledTableCell align="center">Mã đơn hàng</StyledTableCell>
                                    <StyledTableCell align="center">Ngày tạo</StyledTableCell>
                                    <StyledTableCell align="center">Tên khách hàng</StyledTableCell>
                                    <StyledTableCell align="center">Total (VNĐ)</StyledTableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {content.map((order, index) => (
                                    <StyledTableRow key={order.id}>
                                        <StyledTableCell align="center">{++index + ((page) * size)}</StyledTableCell>
                                        <StyledTableCell align="center">{order.code}</StyledTableCell>
                                        <StyledTableCell align="center">{order.createdAt}</StyledTableCell>
                                        <StyledTableCell align="center">{order.customerName}</StyledTableCell>
                                        <StyledTableCell align="center">{order.total}</StyledTableCell>
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

        </Container>

    )
}

export default Analytics

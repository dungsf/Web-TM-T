import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { SimpleCard } from 'app/components'
import { ValidatorForm } from 'react-material-ui-form-validator'
import { CKEditor } from '@ckeditor/ckeditor5-react'
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
import '../../../../css/Module.css'
import Notify from 'app/views/action/Notify'

import {
    Grid,
    Autocomplete,
    Checkbox,
    FormGroup,
    FormControlLabel,
    Box,
    Table,
    TableHead,
    TableBody,
    TableRow,
    TableContainer
} from '@mui/material'
import { StyledTableRow, StyledTableCell, Container, ButtonForm, TextField, BreadcrumbCustom } from '../../base'
import { ProductService, CategoryService, ColorService, SizeService } from 'app/services'

const AppForm = () => {
    const [avatar, setAvatar] = useState()
    const [images, setImages] = useState([])
    const [name, setName] = useState('')
    const [desciption, setDesciption] = useState('')
    const [material, setMaterial] = useState('')
    const [tutorial, setTutorial] = useState('')
    const [hot, setIsHot] = useState(true)
    const [price, setPrice] = useState('')
    const [discount, setDiscount] = useState(0)
    const [category, setCategory] = useState({ id, name })
    //Thông tin về màu sắc và kích thước
    const [sizes, setSizes] = useState([])
    const [listColors, setListColors] = useState([])
    const [changeColor, setChangeColor] = useState(false)

    const navigate = useNavigate()
    const { id } = useParams();
    const [categories, setCategoryies] = useState([])

    const [notify, setNotify] = useState({ isOpen: false, message: '', type: '' })

    const saveOrUpdate = (e) => {
        e.preventDefault();
        const product = { name, price, discount, desciption, material, tutorial, hot, categoryId: category.id }
        if (changeColor) {
            const listC = []

            listColors.forEach(c => {
                let listS = []
                c.listSizes.forEach(s => {
                    if (s.checked) {
                        listS.push(s.id)
                    }
                })
                if (listS.length != 0) {
                    listC.push({ id: c.id, listSizes: listS })
                }
            })
            product.listColors = listC
        }

        if (id !== 'add') {
            ProductService.updateProduct(id, product).then((response) => {
                if (avatar && images.length !== 0) {
                    ProductService.createOrUpdateImage(response.data.data.id, avatar, images).then((response) => {
                        window.setTimeout(function () {
                            window.location.href = '/admin/product/list';
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
                    navigate('/admin/product/list')
                }
            }).catch(error => {
                console.log(error)
                setNotify({
                    isOpen: true,
                    message: 'Cập nhật thất bại!',
                    type: 'error'
                })
            })
        } else {
            ProductService.createProduct(product).then((response) => {
                if (avatar && images) {
                    ProductService.createOrUpdateImage(response.data.data.id, avatar, images).then((response) => {
                        window.setTimeout(function () {
                            window.location.href = '/admin/product/list';
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
                } else {
                    navigate('/admin/product/list')
                }
            }).catch(error => {
                console.log(error)
                setNotify({
                    isOpen: true,
                    message: 'Tạo thất bại!',
                    type: 'error'
                })
            })
        }
    }

    const getAllCategories = () => {
        CategoryService.getAllCategoriesAdmin().then((response) => {
            setCategoryies(response.data.data)
        }).catch(error => {
            console.log(error);
        })
    }
    const getDataColor = (list) => {
        let colors = []
        let sizes = []
        ColorService.getAllColorsAdmin().then((response) => {
            colors = response.data.data
            SizeService.getAllSizes().then((response) => {
                sizes = response.data.data
                setSizes(sizes)
                let listColors = []
                //Khởi tạo bảng ban đầu
                colors.forEach(c => {
                    let listSizes = []
                    sizes.forEach(s => {
                        const size = { id: s.id, name: s.name, checked: false }
                        listSizes.push(size)
                    })
                    const color = { id: c.id, name: c.name, code: c.code, listSizes: listSizes }
                    listColors.push(color)
                })
                //Cập nhật lại bảng nếu là sửa sản phẩm
                if (list.length != 0) {
                    list.forEach(color => {
                        color.listSizes.forEach(size => {
                            listColors.find(c => c.id == color.id).listSizes.find(s => s.id == size.id).checked = true
                        })
                    })
                }
                setListColors(listColors)
            }).catch(error => {
                console.log(error);
            })
        }).catch(error => {
            console.log(error);
        })
    }
    useEffect(() => {
        ProductService.getProductById(id).then((response) => {
            const product = response.data.data
            setName(product.name)
            setDesciption(product.desciption)
            setMaterial(product.material)
            setTutorial(product.tutorial)
            setPrice(product.price)
            setDiscount(product.discount)
            setCategory(product.category)
            setAvatar(product.avatar)
            setIsHot(product.hot)
            getDataColor(product.listColors);
        }).catch(error => {
            getDataColor([]);
            console.log(error)
        })
        getAllCategories();
    }, [])
    const handleAvatar = (e) => {
        setAvatar(e.target.files[0])
    };
    const handleMutipleAvatar = (e) => {
        setImages(e.target.files)
    }
    const changeColorSize = (groupId, checked) => {
        const id = groupId.split('/')
        listColors.find(c => c.id == id[0]).listSizes.find(s => s.id == id[1]).checked = checked
        if (!changeColor) {
            setChangeColor(true)
        }
    }
    const title = (id) => {
        if (id === 'add') {
            return 'Thêm mới sản phẩm'
        } else {
            return 'Cập nhật sản phẩm'
        }
    }
    return (
        <Container>
            <div className="breadcrumb">
                <BreadcrumbCustom id={id} name='Sản phẩm' />
            </div>
            <SimpleCard title={title(id)}>
                <div>
                    <ValidatorForm onSubmit={saveOrUpdate} onError={() => null}>
                        <Grid container spacing={6}>
                            <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                                <Autocomplete
                                    disablePortal
                                    options={categories}
                                    getOptionLabel={(categories) => categories.name}
                                    onChange={(event, categories) => { setCategory(categories) }}
                                    value={category}
                                    sx={{ width: '100%' }}
                                    renderInput={(params) => <TextField {...params} label="Tên danh mục" required />}
                                />
                                <TextField
                                    type="text"
                                    name="name"
                                    onChange={(e) => setName(e.target.value)}
                                    value={name}
                                    label="Tên sản phẩm (*)"
                                    validators={['required']}
                                    errorMessages={['Vui lòng nhập tên sản phẩm']}
                                />
                                <p>Mô tả sản phẩm</p>
                                <CKEditor
                                    editor={ClassicEditor}
                                    data={desciption}
                                    onChange={(event, editor) => setDesciption(editor.getData())}
                                />
                                <p>Chất liệu</p>
                                <CKEditor
                                    editor={ClassicEditor}
                                    onChange={(event, editor) => setMaterial(editor.getData())}
                                />
                                <p>Hướng dẫn</p>
                                <CKEditor
                                    editor={ClassicEditor}
                                    data={tutorial}
                                    onChange={(event, editor) => setTutorial(editor.getData())}
                                />
                                <br />
                                <p>Chọn màu và kích thước</p>
                                <Box width="100%" marginBottom="16px" overflow="auto">
                                    <TableContainer>
                                        <Table sx={{ minWidth: 500 }} aria-label="customized table">
                                            <TableHead>
                                                <TableRow>
                                                    <StyledTableCell align="center" width="50px">STT</StyledTableCell>
                                                    <StyledTableCell align="center">Màu sắc</StyledTableCell>
                                                    {sizes.map((s) => (
                                                        <StyledTableCell align="center" width="65px">{s.name}</StyledTableCell>
                                                    ))}
                                                </TableRow>
                                            </TableHead>
                                            <TableBody>
                                                {listColors.map((c, index) => (
                                                    <StyledTableRow key={c.id}>
                                                        <StyledTableCell align="center">{++index}</StyledTableCell>
                                                        <StyledTableCell align="center">
                                                            <Box
                                                                sx={{
                                                                    width: "75%",
                                                                    height: "36px",
                                                                    backgroundColor: c.code,
                                                                    color: "#ccc",
                                                                    margin: "auto"
                                                                }}
                                                                borderRadius="4px"
                                                                display="flex"
                                                                alignItems="center"
                                                                justifyContent="center"
                                                            >
                                                                {c.name}
                                                            </Box>
                                                        </StyledTableCell>
                                                        {c.listSizes.map((s) => (
                                                            <StyledTableCell align="center">
                                                                <Checkbox defaultChecked={s.checked} id={c.id + "/" + s.id} onChange={(e) => changeColorSize(e.target.id, e.target.checked)} />
                                                            </StyledTableCell>
                                                        ))}
                                                    </StyledTableRow>
                                                ))}
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                </Box>
                                <TextField
                                    type="number"
                                    name="price"
                                    onChange={(e) => setPrice(e.target.value)}
                                    value={price}
                                    label="Giá bán (*)"
                                    validators={['required']}
                                    errorMessages={['Vui lòng nhập giá bán']}
                                />
                                <TextField
                                    type="number"
                                    name="discount"
                                    onChange={(e) => setDiscount(e.target.value)}
                                    value={discount}
                                    label="Giảm giá (%)"
                                // validators={['required']}
                                // errorMessages={['Giảm giá từ 1 -> 99 (%)']}
                                />
                                <input
                                    type="file"
                                    onChange={handleAvatar}
                                    src={avatar}
                                />
                                <input
                                    type="file"
                                    multiple
                                    onChange={handleMutipleAvatar}
                                />
                                <FormGroup>
                                    <FormControlLabel
                                        control={<Checkbox checked={hot} onChange={(e) => { setIsHot(e.target.checked) }} />}
                                        label="Là sản phẩm HOT ?" />
                                </FormGroup>
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
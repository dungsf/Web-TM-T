import React, { useState, useEffect } from 'react'
import {useParams, useNavigate
 } from 'react-router-dom';
import { SimpleCard } from 'app/components'
import { ValidatorForm } from 'react-material-ui-form-validator'
import Autocomplete from '@mui/material/Autocomplete';
import { Grid } from '@mui/material'
import { Container, ButtonForm, TextField, BreadcrumbCustom } from '../../base'
import { CategoryService } from 'app/services';
import Notify from 'app/views/action/Notify'

const AppForm = () => {
    const [name, setName] = useState('')
    const [categories, setCategoryies] = useState([])
    const [parent, setParent]=useState({id, name})
    const navigate = useNavigate();
    const {id} = useParams();
    const [notify, setNotify] = useState({ isOpen: false, message: '', type: '' })

    const tittle = () =>{
        if(id === "add"){
            return "Thêm mới danh mục"
        }
        else{
            return "Cập nhật danh mục"
        }
    }
    
    const createOrUpdate=(e) =>{
        e.preventDefault();
        const category = {name , parentId:parent.id}
        if(id!=="add"){
            CategoryService.updateCategoryAdmin(id, category).then((response) => {
               window.setTimeout(function() {
                    window.location.href = '/admin/category/list';
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
        }else{
            CategoryService.createCategoryAdmin(category).then((response) =>{
                window.setTimeout(function() {
                    window.location.href = '/admin/category/list';
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
                    type: 'success'
                })
            })
        }
    }

    const getAllCategories = () => {

        CategoryService.getAllCategoriesAdmin().then((response) => {
            setCategoryies(response.data.data)
        }).catch(error =>{
            console.log(error);
        })
    }
    useEffect(() => {
        CategoryService.getCategoryAdminById(id).then((response) =>{
           setName(response.data.data.name)
           setParent(response.data.data.parent)
        }).catch(error => {
            console.log(error)
        })
    }, [])

    useEffect(() => {
        getAllCategories()
    }, [])
    return (
        <Container>
            <div className="breadcrumb">
                <BreadcrumbCustom id={id} name = 'Danh mục' />
            </div>
            <SimpleCard title={tittle()}>
                <div>
                    <ValidatorForm onSubmit={createOrUpdate} onError={() => null}>
                        <Grid container spacing={6}>
                            <Grid item lg={6} md={6} sm={12} xs={12} sx={{ mt: 2 }}>
                                <Autocomplete
                                    disablePortal
                                    options={categories}
                                    getOptionLabel={(categories)=>categories.name}
                                    onChange={(event, categories)=>{setParent(categories)}}
                                    sx={{width: '100%'}}
                                    value={parent}
                                    renderInput={(params) => <TextField {...params} label="Danh mục cha" />}
                                />
                                <TextField
                                    type="text"
                                    name="name"
                                    id="standard-basic"
                                    onChange={(e) => setName(e.target.value)}
                                    value={name}
                                    label="Tên danh mục"
                                />
                            </Grid>
                        </Grid>
                        <ButtonForm id={id}/>
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

import SaveIcon from '@mui/icons-material/Save';
import CancelIcon from '@mui/icons-material/Cancel';
import {  Button } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { Box } from '@mui/system'
import React, { useState, useEffect } from 'react'
const ButtonForm = ({id}) => {

    const navigate = useNavigate()
    const [confirmDialog, setConfirmDialog] = useState({isOpen: false, title: '', subTitle: '', type: ''})
    const name = (id)=>{
        if(id === 'add'){
            return 'Tạo mới'
        }else{
            return 'Cập nhật'
        }
    }

    return (
        <div>
        <Box width="100%" display="flex" alignItems="center" marginBottom= '10px' justifyContent="space-between">
            <Button 
                color="success"
                variant="contained"
                size="large"
                type = "submit"
            
                // onClick={()=>
                //     setConfirmDialog({
                //         isOpen: true,
                //         title: "Bạn có chắc chắn xóa!",
                //         subTitle: "Bạn sẽ không thể hoàn tác lại thao tác này!",
                //         onSubmit:()=>{}
                //     })
                // }
                startIcon={<SaveIcon />}
            >
                {name(id)}
            </Button>
            <Button 
                color="info"
                variant="contained"
                size="large"
                type="button"
                endIcon={<CancelIcon />}
                onClick={() => navigate(-1)}
            >
                Thoát
            </Button>
        </Box>
                </div>
    )
}

export default ButtonForm
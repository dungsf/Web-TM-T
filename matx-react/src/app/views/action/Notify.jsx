import React from 'react';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';
import Stack from '@mui/material/Stack';
import { makeStyles } from '@mui/material';

export default function Notify(props) {
    const { notify, setNotify } = props;
    const HandleClose = (event, reason) => {
        setNotify({
            ...notify,
            isOpen: false
        })
    }
    return (
        <Stack sx={{ width: '100%'}} spacing={2}>
            <Snackbar
                style={{ height: '20%'}}
                open={notify.isOpen}
                autoHideDuration={2000}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right'
                }}
                onClose = {HandleClose}
                >
                <Alert variant="filled"
                 style={{ padding:'20px 20px', width:'300px'}}
                    severity={notify.type}
                    onClose = {HandleClose}
                >
                    {notify.message}
                </Alert>
            </Snackbar>
        </Stack>           
    )

}
import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

export default function AlertDialog(props) {
    const { confirmDialog, setConfirmDialog } = props;
    const handleClose = () => {
        setConfirmDialog({
            ...confirmDialog,
            isOpen: false
        })
    };

    return (
        <div>
            <Dialog
                open={confirmDialog.isOpen}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {confirmDialog.title}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {confirmDialog.subTitle}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button variant="contained" color="inherit" onClick={handleClose}>
                        Hủy
                    </Button>
                    <Button variant="contained" color="error" onClick={confirmDialog.onConfirm}>
                        Xóa
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
import {
    TableCell, 
    tableCellClasses ,
    styled
} from '@mui/material'

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: theme.palette.common.white,
      color: theme.palette.common.black,
      paddingLeft: 5,
      fontSize: 16,
    },
    [`&.${tableCellClasses.body}`]: {
      fontSize: 14,
      paddingLeft: 5,
    },
}));

export default StyledTableCell

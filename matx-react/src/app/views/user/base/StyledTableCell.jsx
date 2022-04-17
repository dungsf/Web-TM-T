import {
    TableCell, 
    tableCellClasses ,
    styled
} from '@mui/material'

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
      backgroundColor: "#333f48",
      color: theme.palette.common.white,
      paddingLeft: 5,
      fontSize: 16,
    },
    [`&.${tableCellClasses.body}`]: {
      fontSize: 14,
      paddingLeft: 5,
    },
}));

export default StyledTableCell

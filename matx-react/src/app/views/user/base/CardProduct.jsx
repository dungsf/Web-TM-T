import React,{useState} from 'react'
import {
    Card,
    CardContent,
    CardMedia,
    Fab,
    Typography,
    Icon,
    Radio,
    TextField,
    Autocomplete
} from '@mui/material'
import { localStorageService, URL_IMG } from 'app/services'

const CardProduct = (props) => {

    //Sản phẩm
    const [product] = useState(props.data);
    //Màu mặc định
    const [color, setColor] = useState(product.listColors[0]);
    //Danh sách size liên quan đế màu
    const [listSizes, setListSizes] = useState(color.listSizes);
    //Size mặc định
    const [size, setSize] = useState(listSizes[0]);
    
    const controlProps = (item) => ({
        checked: (color.id === item.id),
        onChange: handleChangeColor,
        value: item.id,
        name: 'color-radio-button',
        inputProps: { 'aria-label': item.id },
    });

    const handleChangeColor = (event) => {
        const id = event.target.value
        const color = product.listColors.find(c=>c.id == id)
        setListSizes(color.listSizes)
        setSize(color.listSizes[0])
        setColor(color)
    };


    const handleAddProduct = (event) => {

        let listCart = localStorageService.getItem('listCart')
        let check = true
        if(!listCart){
            listCart = []
        }
        const cartItem = {
            productId: product.id,
            colorId: color.id,
            sizeId: size.id,
            productName: product.name,
            colorCode: color.code,
            sizeName: size.name,
            avatar: product.avatar,
            seo: product.seo,
            price: product.price,
            quantity: 1
        }

        listCart.forEach(i=>{
            if((i.productId === product.id) && (i.colorId === color.id) && (i.sizeId === size.id)){
                i.quantity++
                check = false
            }
        })

        if(check){
            listCart.push(cartItem)
        }

        localStorageService.setItem('listCart', listCart)

    }

    return (
        <Card sx={{ maxWidth: 345, padding: 1}}>
            <CardMedia
                component="img"
                alt={product.avatar}
                width="100%"
                image={URL_IMG+product.avatar}
            />
            <CardContent sx={{padding: 1, paddingBottom: 0, '&:last-child': { paddingBottom: 0 }}}>
                <Typography gutterBottom variant="body2" component="div" marginTop="5px" marginLeft='-10px' marginBottom="0" display="flex" alignItems="center" justifyContent="space-between">
                    <Typography variant="body2" width="78%" component="div">
                        {product.listColors.map((c)=>(
                            <Radio
                                key={c.id}
                                {...controlProps(c)}
                                sx={{ color: c.code, '&.Mui-checked': { color: c.code, }}}
                            />
                        ))}
                    </Typography>
                    <Autocomplete
                        size="small"
                        id="color"
                        options={listSizes}
                        disableClearable
                        onChange={(event, size)=>{setSize(size)}}
                        getOptionLabel={(s)=>s.name}
                        value={size}
                        sx={{ width: '22%', marginLeft: '10px' }}
                        renderInput={(params) => <TextField {...params}/>}
                    /> 
                </Typography>              
                <Typography gutterBottom variant="h6" component="a" marginTop="5px">
                    {product.name}
                </Typography>
                <Typography gutterBottom variant="body2" component="div" marginTop="5px" marginBottom="0" display="flex" alignItems="center" justifyContent="space-between">
                    <div style={{fontSize: "16px"}}>
                        {product.price.toLocaleString('vi-VN', {style : 'currency', currency : 'VND'})}
                    </div>
                    <Fab
                        size="small"
                        color="primary"
                        aria-label="AddToCart"
                        className="button"
                        onClick={handleAddProduct}
                    >
                        <Icon fontSize="small"> shopping_cart </Icon>
                    </Fab>
                </Typography>
            </CardContent>
        </Card>
    );
}

export default CardProduct